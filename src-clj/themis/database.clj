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
    (get-database "themis-users")
    (get-database "people")))



(defn get-all-ids [database]
  (map #(:id %) (all-documents database)))

(defn find-project [name]
  (get-document "projects" name))

(defn find-user [name]
  (get-document "themis-users" name))

(defn get-all-documents [database]
  (map #(get-document database %) (get-all-ids database)))

(get-all-documents "projects")


;; ------- Testing Stuff -------------------------------------------------------


#_(map #(inject (create-user %)) generals)

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
