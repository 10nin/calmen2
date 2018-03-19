(ns calmen2.core
  (:import (java.net URL HttpURLConnection)))

(defn get-json [address]
  (let [conn ^HttpURLConnection (.openConnection (URL. address))
        stat (.getResponseCode conn)]
    (if (= stat 200)
      "OK"
      nil)))
