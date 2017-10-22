(ns mia.db.exp
  (:require
   [hugsql.core :as hugsql]
   ))

(hugsql/def-db-fns "mia/db/exp_schema.sql")
(hugsql/def-sqlvec-fns "mia/db/exp_schema.sql")

