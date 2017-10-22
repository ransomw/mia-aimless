(ns mia.comp.trial
  (:require
   [com.stuartsierra.component :as component]
   [system.components.sente :refer [new-channel-socket-client]]
   [reagent.core :as r]

   [mia.actions :as act]
   [mia.comp.stim :refer [stim]]
   [mia.comp.resp :refer [resp]]
   ))

(defn init-trial-state []
  (let [sente-client (component/start (new-channel-socket-client))
        *trial-state (r/atom {:stim-done? false
                              :chsk-has-connected? false
                              })
        ]
    (add-watch (:chsk-state sente-client) ::client-comp-update
               (fn [_ _ _ new-state]
                 (if (:ever-opened? new-state)
                   (swap! *trial-state assoc
                          :chsk-has-connected? true))))
    {:close-chsk! #(component/stop sente-client)
     :chsk-send! (:chsk-send! sente-client)
     :*trial-state *trial-state
     }))

;; todo: doesn't display stim on next-trial call
(defn trial []
  (let [{:keys [close-chsk! chsk-send! *trial-state]
         } (init-trial-state)
        *stim-done? (r/cursor *trial-state [:stim-done?])]
    (fn []
      [:div
       (if @*stim-done?
         [resp
          #(chsk-send! [::res-val {:data %}])
          #(do (close-chsk!)
               ;; wait for close?
               (act/next-trial))
          ]
         [stim #(reset! *stim-done? true)])
       ])))
