(ns messages-api.storage.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [clojure.data.json :as json]))

(def mongo-uri "mongodb://sadrake:mezake@localhost:27017/admin")
(def mongo-coll "message")

(defn save-message
  [id raw-message]
  (println "calabrezo" raw-message)
  (let [{:keys [db]} (mg/connect-via-uri mongo-uri)
        message (json/read-str raw-message)]
    (mc/insert db mongo-coll (assoc message :id-message id))))

(defn get-message
  [id]
  (println "muzzarelo")
  (let [{:keys [db]} (mg/connect-via-uri mongo-uri)]
    (mc/find-maps db mongo-coll {:id-message id})))
