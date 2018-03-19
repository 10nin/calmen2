(ns calmen2.core-test
  (:require [clojure.test :refer :all]
            [calmen2.core :refer :all]))

(deftest test-get-json
  (testing "get json resrouce from library web site."
    (is (vector? (get-json "http://www.json.org/JSON_checker/test/pass1.json")))))
    
