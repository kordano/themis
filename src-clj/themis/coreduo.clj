(ns themis.coreduo
  (:use ring.adapter.jetty
        ring.middleware.file
        ring.middleware.file-info
        ring.middleware.format-response
        [ring.util.response :exclude (not-found)]
        [themis.database :as db]
        [clojure.java.io :as io]
        themis.views.index
        [liberator.core :only [defresource]]
        compojure.core
        compojure.route))




(defresource home
  :available-media-types ["text/html"]
  :handle-ok (fn [_] (index-page)))


(defresource all-projects
  :available-media-types ["application/edn"]
  :handle-ok (fn [_] (db/get-all-documents "projects")))


(defresource specific-project [id]
  :available-media-types ["application/json"]
  :handle-ok (fn [_] (db/find-project id)))


(let [static-dir (io/file "resources/public/")]
  (defresource static-dirty-hack [file]
    :available-media-types ["text/javascript"]
    :handle-ok (fn [_] (io/file static-dir file))))


(defroutes handler
  (GET "/" [] home)
  (GET "/projects" [] all-projects)
  (GET "/projects/:id" [id] (specific-project id))
  (GET "/:resource" [resource] (static-dirty-hack resource)))




(def app (-> handler
             wrap-clojure-response))

#_(def server (run-jetty #'app {:port 3000 :join? false}))


#_(.stop server)
#_(.start server)
