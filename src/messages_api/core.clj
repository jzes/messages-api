(ns messages-api.core
  (:require [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn hello-handler
  [request]
  (println request)
  {:status 208
   :headers {"Content-type" "application/json"}
   :body "{\"message\": \"bom dia meus condecorados\"}"})

(defn -main
  [& args]
  (jetty/run-jetty (var hello-handler) {:port 1337 :join? false}))