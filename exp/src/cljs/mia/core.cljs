(ns mia.core
  (:require
   [reagent.core :as r]

   [mia.comp.root :refer [root]]
   ))

(enable-console-print!)

(defn render []
  (r/render
   [root]
   (js/document.getElementById "app"))
  )
