(ns mia.db.core
  (:require
   [hugsql.core :as hugsql]
   [hugsql.adapter.clojure-jdbc :as cj-adapter]

   [mia.db.exp :as exp]
   ))

(defn app-init []
  (hugsql/set-adapter! (cj-adapter/hugsql-adapter-clojure-jdbc)))

(defn reset-db! [db]
  (let [db-conn (:connection db)]
    (exp/drop-table-trial-res db-conn)
    (exp/drop-table-trial db-conn)
    (exp/drop-table-exp db-conn)
    (exp/create-table-exp db-conn)
    (exp/create-table-trial db-conn)
    (exp/create-table-trial-res db-conn)
    ))
