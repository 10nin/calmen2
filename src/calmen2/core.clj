(ns calmen2.core
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io])
  (:import java.net.URL))

(def ^:const ^:dynamic *TAMA-LIBRARY* "https://www.library.metro.tokyo.jp/common/scripts/calendar/tama/data.json")
(def ^:const ^:dynamic *CENTRAL-LIBRARY* "https://www.library.metro.tokyo.jp/common/scripts/calendar/central/data.json")

(defn parse-date [date]
  "parse yyyymmdd style string convert to yyyy/mm/dd"
  (if (= (count date) 8)
    (let [y (apply str (seq (take 4 date)))
          m (apply str (seq (take 2 (drop 4 date))))
          d (apply str (seq (take 2 (drop 6 date))))]
      (str y "/" m "/" d))
    nil))

(defn filter-not-closing-day [closing-days]
  (into (sorted-map) (filter #(= "1" (:closed (fnext %))) closing-days)))

(defn get-json [address]
  "get json from address (address is direct link to json)"
  (let [url (URL. address)]
    (with-open [r (io/reader (.openStream url))]
      (json/read r :key-fn keyword))))
