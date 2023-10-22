(ns messages-api.handler.message 
  (:require [clojure.data.json :as json]
            [messages-api.storage.file :as storage.file]))

(def default-headers {"Content-type" "application/json"})

(defn save-message
  [{:keys [body params] :as req}]
  (println "req" req)
  (let [saved (storage.file/save-message 
               (:id params) 
               (slurp body))]
    (println "saved" saved)
    (if (= nil saved)
      {:status 201
       :headers default-headers
       :body (json/write-str {:message "saved"})}
      {:status 400
       :headers default-headers
       :body (json/write-str {:message "error on save"})})))

(defn get-message
  [{:keys [params] :as req}]
  (println "req" req)
  (let [message-id (:id params)
        message-not-found (str "message not found id: " message-id)
        message (storage.file/get-message message-id)]
    (if message
      {:status 200
       :headers default-headers
       :body message}
      {:status 404
       :headers default-headers
       :body (json/write-str {:message message-not-found
                              :requested-id message-id})})))