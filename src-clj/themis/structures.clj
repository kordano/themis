(ns themis.structures
  (:refer-clojure :exclude [assoc! conj! dissoc!])
  (:require [clojure.core :as core])
  (:use
   [com.ashafa.clutch :only [put-document bulk-update get-document]]))


(defn now [] (new java.util.Date))


(defn generic-entry [name & description]
  {:name name
   :description description
   :created-at (now)
   :last-updated-at (now)})


(defn create-project [name description & [members git-url]]
  (assoc (generic-entry name description)
    :members members
    :git-url git-url))


(defn create-task [name description & [project people]]
  (assoc (generic-entry name description)
    :assignee people
    :related-project project))


(defn create-person [first-name surname & [date-of-birth address mail phone]]
  (assoc (dissoc (generic-entry nil) :name)
    :first-name first-name
    :surname surname
    :date-of-birh date-of-birth
    :contact {:address address
              :mail mail
              :phone phone}))

(def themis
  (create-project
   "themis"
   "A generic management tool"
   ["konny" "judy" "banana joe"]
   "https://github.com/kordano/themis"))

(def ceres
  (create-project
   "ceres"
   "twitter location extraction"
   ["konny"]
   "https://github.com/kordano/ceres"))

(def task1
  (create-task
   "create generic types"
   nil
   "themis"
   ["konny"]))

(def konny
  (create-person
   "Konrad"
   "Kühne"
   "15.04.1985"))
;;(get-database "tasks")
;;(get-database "people")

;;(put-document "tasks" task1)
;;(put-document "projects" ceres)
;;(put-document "people" konny)
