(ns messages-api.storage.mongo
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [clojure.data.json :as json]
            [messages-api.adapter.mongo :as adapter.mongo]
            [clojure.pprint :refer [pprint]])
  (:import org.bson.types.ObjectId))

(def mongo-uri "mongodb://sadrake:mezake@localhost:27017/admin")
(def mongo-coll "message")

(defn save-message
  [raw-message]
  (let [{:keys [db]} (mg/connect-via-uri mongo-uri)
        message (json/read-str raw-message)
        insert-return (mc/insert-and-return db mongo-coll message)]
    (if insert-return 
      (adapter.mongo/_id->id insert-return)
      nil)))

(defn get-message
  [id]
  (let [{:keys [db]} (mg/connect-via-uri mongo-uri)
        return (mc/find-one-as-map db mongo-coll {:_id (ObjectId. id)})]
    (pprint return)
    (adapter.mongo/_id->id return)))
