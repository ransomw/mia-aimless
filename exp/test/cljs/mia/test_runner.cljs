(ns mia.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [mia.core-test]
   [mia.common-test]))

(enable-console-print!)

(doo-tests 'mia.core-test
           'mia.common-test)
