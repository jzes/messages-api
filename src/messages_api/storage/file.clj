(ns messages-api.storage.file 
  (:require [clojure.java.io :as io]))

(defn save-message
  [id message]
  (try
    (with-open [wrt (io/writer (str "base/" id))]
      (.write wrt message))
    (catch Exception e
      (do
        (println "erro ao salvar:" e)
        false))))

(defn get-message
  [id]
  (try (->> id
            (str "base/")
            slurp)
       (catch java.io.FileNotFoundException _ 
         false)))

