(ns mia.comp.start
  (:require
   [reagent.core :as r]

   [mia.actions :as act]
   ))

(defn start []
  (let [clicked-start? (r/atom false)]
    (fn []
      [:div
       [:div>span "click to start"]
       (if (not @clicked-start?)
         [:button
          {:on-click
           #(do (swap! clicked-start? not)
                (act/start-exp)
                )
           } "start"]
         [:span "starting..."])
       ])))
