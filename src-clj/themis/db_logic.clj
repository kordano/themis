(ns themis.db-logic
  (:refer-clojure :exclude [assoc! conj! dissoc! ==])
  (:require [clojure.core :as core]
            [clojure.string :refer [blank?]]
            [clojure.core.logic :refer [run* run membero fresh conde facts defrel]])
  (:use themis.structures
        [com.ashafa.clutch])
  (:import [themis.structures Relation Task Project Member Person Contact Address]))


(defrel project Id Name)
(defrel relation Record1 Record2)

(defn get-all-ids [database]
  (map #(:id %) (all-documents database)))


(defn get-all-documents [database]
  (map #(get-document database %) (get-all-ids database)))


(defn relate [type1 id1 type2 id2]
  "Insert relation between two records into database"
  (inject (create-relation [type1 id1] [type2 id2])))


(defn find-id [name database]
  (let [project-records (apply vector
                               (map #(vector (:_id %) (:name %)) (get-all-documents database)))]
    (do
      (facts project project-records)
      (run* [q]
        (project q name)))))


(defn insert-task [task & {:keys [project members]}]
  "Insert a task and add possible relations"
  (if (nil? project)
    (inject (create-task task))
    (let [task-id (:_id (inject (create-task task)))
          project-id (if-let [project-doc (get-document "projects" (first (find-id project "projects")))]
                       (:_id project-doc)
                       (:_id (inject (create-project project))))]
      (relate "task" task-id "project" project-id))))

(insert-task "urghs" :project "ceres")


(defn insert-project [project & {:keys [tasks members]}]
  "Insert project into database with multiple tasks and members"
  (if (nil? tasks)
    (inject (create-project project))
    (let [project-id (:_id (inject (create-project project)))
          task-ids (map #(if-let [task-doc (get-document "tasks" %)]
                          (:_id task-doc)
                          (:_id (inject (create-task %)))) tasks)]
      (map #(relate "task" % "project" project-id) task-ids))))


(defn get-all-relations []
  "Retrieve all relations between records"
  (apply vector (map #(apply vector (map second (rest (rest (vals %))))) (get-all-documents "relations"))))


(defn find-relations [id]
  "Retrieve all ids related to the give id"
  (do
    (facts relation (get-all-relations))
    (run* [q]
      (conde
        ((relation id q))
        ((relation q id))))))


; ----------- SOME TESTING -----------------------------
#_(map #(inject (create-relation (first %) (second %))) fathers)

#_(def fathers [['Vito 'Michael] ['Vito 'Sonny] ['Vito 'Fredo] ['Michael 'Anthony] ['Michael 'Mary] ['Sonny 'Vicent] ['Sonny 'Francesca] ['Sonny 'Kathryn] ['Sonny 'Frank] ['Sonny 'Santino]])


#_(map #(inject (create-project %)) battles)


#_(def battles ["Uedahara" "Nagashino""Kawanakajima"])
