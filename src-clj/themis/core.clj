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
               (GET "/" [] (response (generate-string (db/get-all-documents "projects") {:escape-non-ascii true})))
               (context "/:id" [id] (defroutes document-routes (GET "/" [] (response (generate-string (db/find-project id) {:escape-non-ascii true}))))))))



(defn user-context []
  (context "/users" []
           (defroutes documents-routes
             (GET "/" [] (response (db/get-all-documents "themis-users")))
             (context "/:id" [id] (defroutes document-routes (GET "/" [] (response (generate-string (db/find-user id) {:escape-non-ascii true}))))))))


(defroutes main-routes
  (GET "/" [] (index-page))
  (GET "/test" [] (generate-string (db/get-all-documents "projects")))
  (project-context)
  (user-context)
  (route/resources "/")
  (route/not-found "These are not the droids you are looking for."))

(def app
  (-> (handler/api main-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))


; run these three to start a server on the repl
#_(use 'ring.adapter.jetty)
#_(def server (run-jetty #'app {:port 3000 :join? false}))

; control the server
#_(.stop server)
#_(.start server)
