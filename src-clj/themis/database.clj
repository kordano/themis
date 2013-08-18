(ns themis.database
  (:refer-clojure :exclude [assoc! conj! dissoc! ==])
  (:require [clojure.core :as core]
            [clojure.string :refer [blank?]]
            [clojure.core.logic :refer [run run* fresh membero conde]])
  (:use themis.structures
        [com.ashafa.clutch])
  (:import [themis.structures Task Project Member Person Contact Address]))


(defn init-db []
  "Initializes databases if they don't exist yet"
  (do
    (get-database "projects")
    (get-database "tasks")
    (get-database "relations")
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


(defn get-init-information []
  {:current-projects (get-all-ids "projects")
   :available-types {"task" (Task/getBasis)
                     "project" (Project/getBasis)
                     "member" (Member/getBasis)}})


;; ------- Testing Stuff -------------------------------------------------------


#_(map #(inject (create-member %)) generals)

#_(map #(inject (create-project (first %) :members (last %))) battles)


#_(def generals [ "Akiyama Nobutomo"
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

#_(def battles ['("Uedahara" ["Itagaki Nobukata" "Amari Torayasu"])
              '("Nagashino" ["Takeda Katsuyori" "Anayama Nobukimi" "Takeda Nobukado"])
              '("Kawanakajima" ["Yamamoto Kansuke" "Kōsaka Masanobu"]) ])


#_(map #(find-member %) (:members (find-project "Uedahara")))
