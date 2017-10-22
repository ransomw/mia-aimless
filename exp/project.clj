(defproject mia "0.0.0-SNAPSHOT"
  :description "motion-induced aftereffect"
  :dependencies
  [
   ;; chestnut
   [org.clojure/clojure "1.8.0"]
   [com.cognitect/transit-clj "0.8.300"]
   [ring "1.6.2"]
   [ring/ring-defaults "0.3.1"]
   [bk/ring-gzip "0.2.1"]
   [radicalzephyr/ring.middleware.logger "0.6.0"]
   [compojure "1.6.0"]
   [environ "1.1.0"]
   [com.stuartsierra/component "0.3.2"]
   [org.danielsz/system "0.4.0"]
   [org.clojure/tools.namespace "0.2.11"]
   [http-kit "2.2.0"]
   ;; added
   [org.clojure/clojurescript "1.9.946" :scope "provided"]
   [reagent "0.8.0-alpha2"]
   [com.taoensso/sente "1.11.0"] ;; websockets
   [com.taoensso/carmine "2.16.0"] ;; redis
   [devcards "0.2.3"]
   [postgresql "9.3-1102.jdbc41"]
   [com.layerware/hugsql "0.4.7"]
   [com.layerware/hugsql-adapter-clojure-jdbc "0.4.7"]
   [cljs-http "0.1.43"]
   ]

  :plugins
  [
   [lein-cljsbuild "1.1.6"]
   [lein-environ "1.1.0"]
   ]

  :min-lein-version "2.7.1"

  :source-paths
  ["src/clj" "src/cljs" "src/cljc"
   "env/prod/cljs"
   ]

  :test-paths
  ["test/clj" "test/cljc"
   ]

  :clean-targets ^{:protect false}
  [:target-path :compile-path
   "resources/public/js"
   "resources/devcards/js"
   ]

  :uberjar-name "mia.jar"

  :main mia.application

  :repl-options
  {:init-ns user
   :timeout 120000 ;; ms ;; default: 30000
   }

  :cljsbuild
  {:builds
   [{:id "app"
     :source-paths ["src/cljs" "src/cljc" "dev"
                    "env/prod/cljs"
                    ]

     :figwheel {:on-jsload "mia.system/reset"}

     :compiler
     {:main cljs.user
      :asset-path "js/compiled/out"
      :output-to "resources/public/js/compiled/mia.js"
      :output-dir "resources/public/js/compiled/out"
      :source-map-timestamp true}}

    {:id "devcards"
     :source-paths ["src/cljs" "src/cljc"
                    "test/cljs" "test/cljc"
                    "env/test/cljs"
                    ]

     :figwheel {:devcards true}

     :compiler
     {:main mia.devcards.core
      :asset-path "js/compiled/devcards_out"
      ;; matches http-server-root for figwheel
      :output-to "resources/devcards/js/compiled/mia_devcards.js"
      :output-dir "resources/devcards/js/compiled/devcards_out"
      :source-map-timestamp true

      :foreign-libs [{:file "target/mia-stim-bundle.js"
                      :provides ["drawstim"]
                      :module-type :commonjs}]
      :language-in :ecmascript6
      :install-deps true

      }}

    {:id "test"
     :source-paths ["src/cljs" "test/cljs" "src/cljc" "test/cljc"
                    "env/test/cljs"
                    ]
     :compiler
     {:output-to "resources/public/js/compiled/testable.js"
      :main mia.test-runner
      :optimizations :none}}

    {:id "min"
     :source-paths ["src/cljs" "src/cljc"]
     :jar true
     :compiler
     {:main mia.system
      :output-to "resources/public/js/compiled/mia.js"
      :output-dir "target"
      :source-map-timestamp true
      :optimizations :advanced
      :pretty-print false}}]}


  :figwheel
  {
   :css-dirs ["resources/public/css"]
   :server-logfile "log/figwheel.log"
   }

  :doo {:build "test"}

  :profiles
  {:dev
   {:dependencies
    [[figwheel "0.5.13"]
     [figwheel-sidecar "0.5.13"]
     [com.cemerick/piggieback "0.2.2"]
     [org.clojure/tools.nrepl "0.2.13"]
     [lein-doo "0.1.8"]
     [reloaded.repl "0.2.3"]
     ]

    :plugins
    [[lein-figwheel "0.5.13"]
     [lein-doo "0.1.8"]
     ]

    :source-paths ["dev"]
    :repl-options {:nrepl-middleware
                   [cemerick.piggieback/wrap-cljs-repl
                    ]}}

   :uberjar
   {:source-paths ^:replace ["src/clj" "src/cljc"]
    :prep-tasks ["compile"
                 ["cljsbuild" "once" "min"]]
    :hooks []
    :omit-source true
    :aot :all}
   })
