(ns themis.database
  (:refer-clojure :exclude [assoc! conj! dissoc!])
  (:require [clojure.core :as core])
  (:use themis.structures
        [com.ashafa.clutch])
  (:import [themis.structures Task Project User Person Contact Address]))

#_(defn init-db []
  (do
    (get-database "projects")
    (get-database "tasks")
    (get-database "members")
    (get-database "people")))


(defn get-all-ids [database]
  (map #(:id %) (all-documents database)))


(defn find-project [name]
  (get-document "projects" name))


(defn find-member [name]
  (get-document "members" name))


(defn get-all-documents [database]
  (map #(get-document database %) (get-all-ids database)))


(defn add-task-to-project [id task]
  (let [document (get-document "projects" id)
        current-tasks (:tasks document)]
    (update-document "projects" document {:tasks (conj current-tasks task)})))


(defn remove-task-from-project [id task]
  (let [document (get-document "projects" id)
        current-tasks (:tasks document)]
    (update-document "projects" document {:tasks (filter #(not= task %) current-tasks)})))


(defn add-member-to-project [id member]
  (let [document (get-document "projects" id)
        current-members (:members document)]
    (if-not (contains? (set current-members) member) ;; find better error handling for existing entries in database
      (update-document "projects" document {:members (conj current-members member)}))))


(defn remove-member-from-project [id member]
  (let [document (get-document "projects" id)
        current-members (:members document)
        current-tasks (:tasks document)]
    (update-document "projects" document {:members (filter #(not= member %) current-members)})))


(defn insert-member [name & {:keys [project]}]
  (do
    (inject (create-member name))
    (add-member-to-project project name)))

(defn insert-task [name & {:keys [project]}]
  (do
    (inject (create-task name))
    (add-task-to-project project name)))



;; ------- Testing Stuff -------------------------------------------------------


#_(map #(inject (create-member %)) generals)

#_(map #(inject (create-project (first %) :members (last %))) battles)


(def generals [ "Akiyama Nobutomo"
                "Amari Torayasu"
                "Anayama Baisetsu"
                "Baba Nobufusa"
                "Hara Masatane"
                "Hara Toratane"
                "Ichijô Nobutatsu"
                "Itagaki Nobukata"
                "Kosaka Masanobu"
                "Naitô Masatoyo"
                "Obata Masamori"
                "Obata Toramori"
                "Obu Toramasa"
                "Oyamada Nobushige"
                "Saigusa Moritomo"
                "Sanada Nobutsuna"
                "Sanada Yukitaka"
                "Tada Mitsuyori"
                "Takeda Nobukado"
                "Takeda Nobushige"
                "Tsuchiya Masatsugu"
                "Yamagata Masakage"
                "Yamamoto Kansuke"
                "Yokota Takamatsu"])

(def battles ['("Uedahara" ["Itagaki Nobukata" "Amari Torayasu"])
              '("Nagashino" ["Takeda Katsuyori" "Anayama Nobukimi" "Takeda Nobukado"])
              '("Kawanakajima" ["Yamamoto Kansuke" "Kōsaka Masanobu"]) ])


(map #(find-user %) (:members (find-project "Uedahara")))
