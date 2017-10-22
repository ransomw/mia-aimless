(ns mia.state.updaters
  (:require
   [mia.state.core :refer [app-state]]
   [mia.exp-config :as exp-config]
   ))

(defn start-exp []
  (swap! app-state assoc :started? true))

(defn next-trial []
  (let [cfg (exp-config/config)]
    (swap!
     app-state
     (fn [curr-app-state]
       (if (= (:num-trials cfg)
              (-> curr-app-state :trial-idx inc))
         (assoc curr-app-state :ended? true)
         (update curr-app-state :trial-idx inc))
       ))
    ))
