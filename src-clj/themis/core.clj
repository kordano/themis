(ns themis.core
  (:use ring.adapter.jetty
        ring.middleware.file
        ring.middleware.file-info
        ring.middleware.format-response
        [ring.util.response :exclude (not-found)]
        [clojure.edn :as edn]
        compojure.core
        compojure.route
        [themis.database :as db]
        themis.views.index))


(defroutes handler
  (GET "/" [] (response (index-page)))
  (GET "/projects" [] (response (db/get-all-documents "projects")))
  (GET "/projects/:id" [id] (response (db/find-project id)))
  (GET "/users" [] (response (db/get-all-documents "themis-users")))
  (POST "/insert/users/" request {:status 200 :body (-> request :body)})
  (files ""  {:root "resources/public"})
  (not-found "<h1>404 Page not found</h1>"))


(def app (-> handler
             wrap-clojure-response))

#_ (.stop server)
#_ (.start server)

#_(def server
    (run-jetty #'app {:port 3000 :join? false}))
