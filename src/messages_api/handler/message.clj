(ns messages-api.handler.message 
  (:require [clojure.data.json :as json]
            [messages-api.storage.file :as storage.file]
            [messages-api.storage.mongo :as storage.mongo]))

(def default-headers {"Content-type" "application/json"})

(defn ^:private save-message
  [write-fn {:keys [body params]}]
  (let [saved (write-fn 
               (:id params) 
               (slurp body))]
    (if (= nil saved)
      {:status 201
       :headers default-headers
       :body (json/write-str {:message "saved"})}
      {:status 400
       :headers default-headers
       :body (json/write-str {:message "error on save"})})))

(defn ^:private get-message
  [read-fn {:keys [params]}]
  (let [message-id (:id params)
        message-not-found (str "message not found id: " message-id)
        message (read-fn message-id)]
    (if message
      {:status 200
       :headers default-headers
       :body message}
      {:status 404
       :headers default-headers
       :body (json/write-str {:message message-not-found
                              :requested-id message-id})})))



(def file->message (partial get-message storage.file/get-message))
(def message->file (partial save-message storage.file/save-message))
(def mongo->message (partial get-message storage.mongo/get-message))
(def message->mongo (partial save-message storage.mongo/save-message))