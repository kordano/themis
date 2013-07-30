(ns themis.core
  (refer-clojure :exlude [read-string])
  (:use ring.adapter.jetty
        ring.middleware.file
        ring.middleware.file-info
        ring.middleware.format-response
        [ring.util.response :exclude (not-found)]
        compojure.core
        compojure.route
        [themis.database :as db]
        themis.views.index)
  (:require [clojure.edn :as edn :refer [read-string]]))


(defn add-task [name project]
  (response (db/insert-task name :project project)))


(defn add-member [name project]
  (response (db/insert-member name :project project)))


(defn add-project [name]
  (response "OK"))


(defroutes handler
  (GET "/" [] (response (index-page)))
  (GET "/projects" [] (response (db/get-all-documents "projects")))
  (GET "/projects/:id" [id] (response (db/find-project id)))
  (GET "/users" [] (response (db/get-all-documents "themis-users")))
  (POST "/insert/member/" request (let [data (read-string (slurp (:body request)))]
                                   (add-member (:name data) (:project data))))
  (POST "/insert/task/" request (let [data (read-string (slurp (:body request)))]
                                  (add-task (:name data) (:project data))))
  (POST "/insert/project/" request (let [data (read-string (slurp (:body request)))]
                                  (add-project (:name data))))
  (files ""  {:root "resources/public"})
  (not-found "<h1>404 Page not found</h1>"))


(def app
  (-> handler
      wrap-clojure-response))


#_(.stop server)
#_(def server
    (run-jetty #'app {:port 3000 :join? false}))

#_(.start server)
