(ns user
  (:require
   [clojure.repl :refer [doc]]
   [clojure.tools.namespace.repl
    :refer [set-refresh-dirs refresh refresh-all]]
   [clojure.test :refer [run-tests test-vars]]
   [com.stuartsierra.component :as component]
   [figwheel-sidecar.config :as fw-config]
   [figwheel-sidecar.system :as fw-sys]
   [reloaded.repl :refer [system init]]
   [figwheel-sidecar.repl-api :as figwheel]

   [mia.application]
   [mia.config :refer [config]]
   ))

(defn dev-system []
  (assoc (mia.application/app-system (config))
    :figwheel-system (fw-sys/figwheel-system (fw-config/fetch-config))
    :css-watcher (fw-sys/css-watcher
                  {:watch-paths ["resources/public/css"]})
    ))

(defn devcards-system []
  (component/system-map
   :figwheel-system
   (fw-sys/figwheel-system
    (-> (fw-config/fetch-config)
        (update-in
         [:data :figwheel-options]
         #(merge % {:http-server-root "devcards"}))
        (update-in
         [:data]
         #(merge % {:build-ids ["devcards"]}))
        )
    )))

(set-refresh-dirs "src" "dev" "test")

(defn cljs-repl []
  (fw-sys/cljs-repl (:figwheel-system system)))

(def start reloaded.repl/start)
(def stop reloaded.repl/stop)
(defn go []
  (reloaded.repl/set-init! #(dev-system))
  (reloaded.repl/go))
(defn go-devcards []
  (reloaded.repl/set-init! #(devcards-system))
  (reloaded.repl/go))
(def reset reloaded.repl/reset)
(def reset-all reloaded.repl/reset-all)

(defn run-all-tests [& opts]
  (do
    (stop)
    ;; refresh-all in case of sql changes
    (refresh-all)
    (map run-tests
         [
          ])
    ))

(defn cljs-clean []
  (figwheel/start-figwheel!)
  (figwheel/clean-builds)
  (figwheel/stop-figwheel!)
  )
