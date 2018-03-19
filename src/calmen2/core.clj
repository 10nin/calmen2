(ns calmen2.core
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io])
  (:import java.net.URL))

(defn get-json [address]
  (let [url (URL. address)]
    (with-open [r (io/reader (.openStream url))]
      (json/read r))))
