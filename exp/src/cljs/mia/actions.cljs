(ns mia.actions
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [cljs.core.async :refer [<! >!]]
   [mia.state.updaters :as u]
   ))

(defn start-exp []
  (u/start-exp)
  )

(defn next-trial []
  (u/next-trial)
  )
