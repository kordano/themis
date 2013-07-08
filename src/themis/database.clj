(ns themis.database
  (:use com.ashafa.clutch))

(defn create-project-db []
  (do
    (get-database "projects")))


(bulk-update "projects" [{:name "ceres" :test {:sub "12" :sub2 "13"} :_id "0001"}
                         {:name "acheron" :_id "0002"}])

(get-document "projects" "0001")
