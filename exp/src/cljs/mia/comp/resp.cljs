(ns mia.comp.resp
  (:require
   [reagent.core :as r]
   ))

(defn resp [on-change-val done]
  (let [*curr-val (r/atom 0)]
    (fn []
      [:div
       [:div>input {:type "range" :min -180 :max 180 :step 1
                :value @*curr-val
                :on-change
                (fn [ev]
                  ((comp on-change-val (partial reset! *curr-val))
                   (-> ev .-target .-value int))
                  )}]
       [:div>button {:on-click done} "done"]
       ])))
