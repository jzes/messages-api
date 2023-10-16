(ns messages-api.handler.message 
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]))

(def default-headers {"Content-type" "application/json"})

(defn save-message
  [{:keys [body params] :as req}]
  (println "req" req)
  (let [saved (try
                (with-open [wrt (io/writer (str "base/" (:id params)))]
                  (.write wrt (slurp body)))
                (catch Exception e
                  (do
                    (println "erro ao salvar:" e)
                    false)))]
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
        message (try (->> message-id
                          (str "base/")
                          slurp)
                     (catch java.io.FileNotFoundException _
                       (do
                         (println message-not-found)
                         false)))]
    (if message
      {:status 200
       :headers default-headers
       :body message}
      {:status 404
       :headers default-headers
       :body (json/write-str {:message message-not-found
                              :requested-id message-id})})))