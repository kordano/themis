(ns themis.database
  (:use com.ashafa.clutch))

(defn create-project-db []
  (do
    (get-database "projects")))


(bulk-update "projects" [{:name "ceres" :_id "0001"}
                         {:name "acheron" :_id "0002"}])
