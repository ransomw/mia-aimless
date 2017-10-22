(ns mia.comp.stim
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require
   [cljs.core.async :refer [<! timeout]]
   [reagent.core :as r]
   ))

(def delay-tick-ms 300)
(def tot-delay-ms 3000)

(defn stim [done]
  (let [*rem-delay-ms (r/atom tot-delay-ms)]
    (go-loop []
      (<! (timeout delay-tick-ms))
      (if (>= 0 (swap! *rem-delay-ms #(- % delay-tick-ms)))
        (done)
        (recur)))
    (fn []
      [:div
       [:span
        (str
         "stimulus placeholder delay."
         (apply str (repeat (int (/ (- tot-delay-ms @*rem-delay-ms)
                                    (/ tot-delay-ms 9))) "."))
          )]
       ])))
