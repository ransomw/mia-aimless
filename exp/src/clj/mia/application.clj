(ns mia.application
  (:gen-class)
  (:require
   [com.stuartsierra.component :as component]
   [system.components.endpoint :refer [new-endpoint]]
   [system.components.handler :refer [new-handler]]
   [system.components.middleware :refer [new-middleware]]
   [system.components.http-kit :refer [new-web-server]]
   [system.components.sente
    :refer [new-channel-socket-server sente-routes]]
   [taoensso.sente.server-adapters.http-kit
    :refer [sente-web-server-adapter]]
   [mia.config :refer [config]]
   [mia.routes :refer [home-routes sente-handler]]
   ))

(defn app-system [config]
  (component/system-map
   :sente
   (new-channel-socket-server
    sente-handler sente-web-server-adapter
    {:wrap-component? true ;; handler gets component
     })
   :sente-endpoint
   (-> (new-endpoint sente-routes)
       (component/using [:sente]))
   :routes
   (new-endpoint home-routes)
   :middleware
   (new-middleware {:middleware (:middleware config)})
   :handler
   (-> (new-handler)
       (component/using [:sente-endpoint :routes :middleware]))
   :http
   (-> (new-web-server (:http-port config))
       (component/using [:handler]))
   ))

(defn -main [& _]
  (let [config (config)]
    (-> config
        app-system
        component/start)
    (println "Started mia on" (str "http://localhost:" (:http-port config)))))
