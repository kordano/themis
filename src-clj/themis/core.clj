(ns themis.core
  (:use ring.adapter.jetty
        ring.middleware.file
        ring.middleware.file-info
        ring.middleware.format-response
        [clojure.edn :as edn]
        [ring.util.response :exclude (not-found)]
        compojure.core
        compojure.route
        [themis.database :as db]
        themis.views.index))


(defn add-user [name project]
    (response (db/insert-user name :project project)))

(defroutes handler
  (GET "/" [] (response (index-page)))
  (GET "/projects" [] (response (db/get-all-documents "projects")))
  (GET "/projects/:id" [id] (response (db/find-project id)))
  (GET "/users" [] (response (db/get-all-documents "themis-users")))
  (POST "/insert/users/" request (let [data (edn/read-string (slurp (:body request)))]
                                     (add-user (:name data) (:project data))))
  (files ""  {:root "resources/public"})
  (not-found "<h1>404 Page not found</h1>"))


(def app
  (-> handler
      wrap-clojure-response))


#_(.stop server)
#_(def server
    (run-jetty #'app {:port 3000 :join? false}))

#_(.start server)
