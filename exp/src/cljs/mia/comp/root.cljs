(ns mia.comp.root
  (:require
   [reagent.core :as r]

   [mia.state.core :refer [app-state]]
   [mia.comp.start :refer [start]]
   [mia.comp.end :refer [end]]
   [mia.comp.trial :refer [trial]]
   ))

;; (defn timer-component []
;;   (let [seconds-elapsed (r/atom 0)]
;;     (println "there")
;;     (fn []
;;       (println "here")
;;       (js/setTimeout #(swap! seconds-elapsed inc) 1000)
;;       [:div
;;        "Seconds Elapsed: " @seconds-elapsed])))

(defn root []
  [:div
   [:h1 "mia"]
   (let [{:keys [started? ended?]
          } @app-state]
     [:div
      (cond
        (not started?) [start]
        ended? [end]
        :else [trial]
        )
      ])
   ])
