(ns themis.database
  (:refer-clojure :exclude [assoc! conj! dissoc!])
  (:require [clojure.core :as core])
  (:use
   [com.ashafa.clutch]))

#_(defn init-db []
  (do
    (get-database "projects")
    (get-database "tasks")
    (get-database "people")))


(defn get-all-projects []
  (map #((get-document "projects" (% :key)) :name) (all-documents "projects")))
