(ns calmen2.core
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io])
  (:import java.net.URL))

(def ^:const ^:dynamic *TAMA-LIBRARY* "https://www.library.metro.tokyo.jp/common/scripts/calendar/tama/data.json")
(def ^:const ^:dynamic *CENTRAL-LIBRARY* "https://www.library.metro.tokyo.jp/common/scripts/calendar/central/data.json")
(def ^:const ^:dynamic *CALENDAR-TEMPLATE* "
BEGIN:VCALENDAR
METHOD:PUBLISH
VERSION:2.0
PRODID:CALMEN-CLJ
X-WR-TIMEZONE:Asia/Tokyo
CALSCALE:GREGORIAN
BEGIN:VTIMEZONE
TZID:Asia/Tokyo
BEGIN:DAYLIGHT
TZOFFSETFROM:+0900
DTSTART:19500507T000000
TZNAME:JDT
TZOFFSETTO:+1000
END:DAYLIGHT
BEGIN:STANDARD
TZOFFSETFROM:+1000
DTSTART:19510909T000000
TZNAME:JST
TZOFFSETTO:+0900
END:STANDARD
END:VTIMEZONE
{% for elm in ClosingList %}
BEGIN:VEVENT
TRANSP:OPAQUE
SUMMARY:図書館休み
DTSTART;VALUE=DATE:{{elm.StartDate}}
DTEND;VALUE=DATE:{{elm.EndDate}}
END:VEVENT
{% endfor %}
")

(defn parse-date [date]
  "parse yyyymmdd style string convert to yyyy/mm/dd"
  (.format
   (java.text.SimpleDateFormat. "yyyy/mm/dd")
   (.parse
    (java.text.SimpleDateFormat. "yyyymmdd") date)))

(defn filter-not-closing-day [closing-days]
  (into (sorted-map) (filter #(= "1" (:closed (fnext %))) closing-days)))

(defn build-days [closing-days]
  (->> (keys closing-days)
       (map #(name %))
       (map #(parse-date %))))
  
(defn get-json [address]
  "get json from address (address is direct link to json)"
  (let [url (URL. address)]
    (with-open [r (io/reader (.openStream url))]
      (json/read r :key-fn keyword))))

(defn -main []
  nil)
