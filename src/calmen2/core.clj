(ns calmen2.core
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io])
  (:import java.net.URL))

(def ^:const ^:dynamic *TAMA-LIBRARY* "https://www.library.metro.tokyo.jp/common/scripts/calendar/tama/data.json")
(def ^:const ^:dynamic *CENTRAL-LIBRARY* "https://www.library.metro.tokyo.jp/common/scripts/calendar/central/data.json")

(defn get-json [address]
  (let [url (URL. address)]
    (with-open [r (io/reader (.openStream url))]
      (json/read r))))
