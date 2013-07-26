(ns themis.coreduo
  (:require [themis.database :as db]
            [clojure.java.io :as io]
            [themis.views.index :refer [index-page]]
            [ring.util.mime-type :refer [ext-mime-type]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.adapter.jetty :refer [run-jetty]]
            [liberator.core :refer [defresource]]
            [compojure.core :refer [defroutes GET routes]]
            [compojure.handler :refer [api]]))


(defresource home
  :available-media-types ["text/html"]
  :handle-ok (fn [_] (index-page)))


(defresource all-projects
  :available-media-types ["application/edn"]
  :handle-ok (fn [_] {:projects (apply vector (map #(:_id %) (db/get-all-documents "projects")))}))


(defresource specific-project [id]
  :available-media-types ["application/json"]
  :handle-ok (fn [_] (db/find-project id)))


(let [static-dir (io/file "resources/public/")]
  (defresource static-dirty-hack [file]
    :available-media-types ["text/javascript" "text/html"]
    :handle-ok (fn [_] (io/file static-dir file))))


(defn assemble-routes []
  (routes
   (GET "/" [] home)
   (GET "/projects" [] all-projects)
   (GET "/projects/:id" [id] (specific-project id))
   (GET "/resources/:resource" [resource] (static-dirty-hack resource))))



(defn create-handler []
  (fn [request]
    ((->
      (assemble-routes)
      api
      wrap-multipart-params)
     request)))


(defn handler [request]
  ((create-handler) request))


(defn start [options]
  (run-jetty
   handler
  (assoc options :join? false)))


#_(def server (start {:port 3000}))


#_(.stop server)
#_(.start server)
