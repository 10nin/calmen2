(defproject calmen2 "0.1.0-SNAPSHOT"
  :description "A Tokyo Metropolitan Library annual calender getter."
  :url "https://gitlab.com/10nin/calmen"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-time "0.14.2"]
                 [selmer "1.11.7"]]
  :main calmen2.core)
