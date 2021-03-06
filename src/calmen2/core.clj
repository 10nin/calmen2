(ns calmen2.core
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [clj-time.format :as f]
            [clj-time.core :as t]
            [selmer.parser :as tmpl])
  (:import java.net.URL))

(def ^:const ^:dynamic *TAMA-LIBRARY* "https://www.library.metro.tokyo.jp/common/scripts/calendar/tama/data.json")
(def ^:const ^:dynamic *CENTRAL-LIBRARY* "https://www.library.metro.tokyo.jp/common/scripts/calendar/central/data.json")
(def ^:const ^:dynamic *CALENDAR-TEMPLATE* "iCal_template.ics")

(defn filter-not-closing-day [closing-days]
  (into (sorted-map) (filter #(= "1" (:closed (fnext %))) closing-days)))

(defn get-next-day [day]
  (let [cf (f/formatter "yyyyMMdd")]
    (f/unparse cf (t/plus (f/parse cf day) (t/days 1)))))

(defn build-days
  ([g closing-days]
   (->> (keys closing-days)
        (map #(name %))
        (map #(g %))))
  ([closing-days]
   (build-days identity closing-days)))

(defn get-next-days [closing-days]
  (build-days get-next-day closing-days))

(defn build-closing-list [start-days end-days]
  {:ClosingList (map #(assoc {} :StartDate %1 :EndDate %2) start-days end-days)})

(defn render-calendar [closing-list]
  (tmpl/render-file *CALENDAR-TEMPLATE* closing-list))

(defn write-to-file [file content]
  (with-open [f (io/writer file)]
    (.write f content)))

(defn get-json [address]
  "get json from address (address is direct link to json)"
  (let [url (URL. address)]
    (with-open [r (io/reader (.openStream url))]
      (json/read r :key-fn keyword))))

(defn make-iCal [closing-days file]
  (->> (build-closing-list (build-days closing-days) (get-next-days closing-days))
      render-calendar
      (write-to-file file)))

(defn -main []
  (let [tama (filter-not-closing-day (get-json *TAMA-LIBRARY*))
        central (filter-not-closing-day (get-json *CENTRAL-LIBRARY*))]
    (make-iCal tama "/tmp/library_closing_tama.ics")
    (make-iCal central "/tmp/library_closing_central.ics")))
    
