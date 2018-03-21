(ns calmen2.core-test
  (:require [clojure.test :refer :all]
            [calmen2.core :refer :all]))

(deftest test-get-json
  (testing "get json resrouce from library web site."
    (is (map? (get-json *TAMA-LIBRARY*)))))
    
(deftest test-parse-date
  (testing "date format parser."
    (is (= "2017/12/31" (parse-date "20171231")))
    (is (= "2018/02/02" (parse-date "20180202")))
    (is (= "2102/12/04" (parse-date "21021204")))
    (is (= "2002/03/14" (parse-date "20020314")))))

(deftest test-filter-not-closing-day
  (testing "filter function for filtered not closing days."
    (let [d {:20190315 {:closed "1"}, :20180619 {:closed "1"},:20180620 {:closed "0"}}]
      (is (= 3 (count d)))
      (is (= 2 (count (filter-not-closing-day d)))))))
