(ns messages-api.adapter.mongo
  (:require [clojure.set :refer [rename-keys]]))

(defn _id->id [document]
  (when document
    (rename-keys (assoc
                  document
                  :_id (-> document
                           :_id
                           str))
                 {:_id :id})))