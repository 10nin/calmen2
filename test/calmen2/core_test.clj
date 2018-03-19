(ns calmen2.core-test
  (:require [clojure.test :refer :all]
            [calmen2.core :refer :all]))

(deftest get-json-test
  (testing "get json resrouce from library web site."
    (is (string? (get-json "http://www.example.com/")))))
