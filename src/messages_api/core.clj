(ns messages-api.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as comp.core]
            [messages-api.handler.message :as handler.msg])
  (:gen-class))

(def my-routes
  (comp.core/routes
   (comp.core/GET "/message/:id" [] handler.msg/file->message)
   (comp.core/POST "/message/:id" [] handler.msg/message->file)))

(defn -main
  [& _]
  (jetty/run-jetty (var my-routes) {:port 1337 :join? false}))
