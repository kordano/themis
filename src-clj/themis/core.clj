(ns themis.core
  (:use compojure.core
        themis.views.index
        cheshire.core
        ring.util.response
        [hiccup.middleware :only (wrap-base-url)])
  (:require [themis.database :as db]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [ring.middleware.json :as middleware]))



(defn project-context []
    (context "/projects" []
             (defroutes documents-routes
               (GET "/" [] (response (db/get-all-documents "projects")))
               (context "/:id" [id] (defroutes document-routes (GET "/" [] (response (generate-string (db/find-project id)))))))))


(defn user-context []
  (context "/users" []
           (defroutes documents-routes
             (GET "/" [] (response (db/get-all-documents "themis-users")))
             (context "/:id" [id] (defroutes document-routes (GET "/" [] (response (generate-string (db/find-user id)))))))))


(defroutes main-routes
  (GET "/" [] (index-page))
  (project-context)
  (user-context)
  (route/resources "/")
  (route/not-found "These are not the droids you are looking for."))

(def app
  (-> (handler/api main-routes)
      (wrap-base-url)))


; run these three to start a server on the repl
#_(use 'ring.adapter.jetty)
#_(def server (run-jetty #'app {:port 3000 :join? false}))

; control the server
#_(.stop server)
#_(.start server)
