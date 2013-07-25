(ns themis.tricore
  (:use ring.adapter.jetty
        ring.middleware.file
        ring.middleware.file-info
        ring.middleware.format-response
        [ring.util.response :exclude (not-found)]
        compojure.core
        compojure.route
        [themis.database :as db]
        themis.views.index))

(defroutes handler
  (GET "/" [] (response (index-page)))
  (GET "/projects" [] (response (apply vector (map #(:_id %) (db/get-all-documents "projects")))))
  (GET "/resources/:id" [id] (file-response "main.js" {:root "resources/public"}))
  (not-found "<h1>404 Page not found</h1>")
)

(def app (-> handler
             wrap-clojure-response))

#_(def server
    (run-jetty #'app {:port 3000 :join? false}))

#_ (.stop server)
#_ (.start server)
