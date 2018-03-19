(ns calmen2.core-test
  (:require [clojure.test :refer :all]
            [calmen2.core :refer :all]))

(deftest test-get-json
  (testing "get json resrouce from library web site."
    (is (map? (get-json *TAMA-LIBRARY*)))))
    
(deftest test-parse-date
  (testing "date format parser."
    (is (= "2017/12/31" (parse-date "20171231")))
    (is (= nil (parse-date "xxxxxxxxxx")))
    (is (= "yyyy/mm/dd" (parse-date "yyyymmdd")))))
