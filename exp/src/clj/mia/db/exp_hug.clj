(ns mia.db.exp-hug
  (:require
   [hugsql.core :as hugsql]
   ))

(hugsql/def-db-fns "mia/db/exp.sql")
(hugsql/def-sqlvec-fns "mia/db/exp.sql")
