(ns mia.state.core
  (:require
   [reagent.core :as r]
   ))

(defonce app-state
  (r/atom
   {:started? false
    :ended? false
    :trial-idx 0
    }))
