(ns mia.devcards.dots
  (:require-macros
   [devcards.core :as dc :refer
    [defcard defcard-doc noframe-doc deftest]]
   [cljs.test :refer [is testing]]
   )
  (:require
   [devcards.core]
   ;; js lib
   [drawstim]
   ))

(defcard-doc
  "test patterns of moving dots")

(deftest cljs-time-tests
  (testing "calling test function from JS lib"
    (is (= 4 (drawstim/add 2 2)))
    ))

