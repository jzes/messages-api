(ns messages-api.storage.file 
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json]))

(defn save-message
  [message]
  (println "message" message)
  (let [id (str (random-uuid))]
    (try
      (with-open [wrt (io/writer (str "base/" id))]
        (.write wrt message)
        (-> message
            json/read-str
            (assoc :id id)))
      (catch Exception e
        (do
          (println "erro ao salvar:" e)
          false)))))

(defn get-message
  [id]
  (try (->> id
            (str "base/")
            slurp
            json/read-str)
       (catch java.io.FileNotFoundException _ 
         false)))

