(ns themis.server.routes
  (:use compojure.core
        themis.server.views
        [hiccup.middleware :only (wrap-base-url)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]))

(defroutes main-routes
  (GET "/" [] (index-page))
  (route/resources "/")
  (route/not-found "These are not the droids you are looking for."))

(def app
  (-> (handler/site main-routes)
      (wrap-base-url)))


; run these three to start a server on the repl
#_(use 'ring.adapter.jetty)
#_(def server (run-jetty #'app {:port 9000 :join? false}))

; control the server
#_(.stop server)
#_(.start server)
