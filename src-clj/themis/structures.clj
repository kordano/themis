(ns themis.structures
  (:refer-clojure :exclude [assoc! conj! dissoc!])
  (:require [clojure.core :as core])
  (:use [com.ashafa.clutch]))


(defn now [] (new java.util.Date))

(defprotocol database-interaction
  (inject [entry]))


(defrecord Relation [record1 record2]
  database-interaction
  (inject [entry] (put-document "relations" entry)))


(defrecord Project [name created-at]
  database-interaction
  (inject [entry] (put-document "projects" entry)))


(defrecord Task [description created-at]
  database-interaction
  (inject [entry] (put-document "tasks" entry)))


(defrecord Member [_id person]
  database-interaction
  (inject [entry] (put-document "members" entry)))


(defrecord Person [first-name surname birthday contact]
  database-interaction
  (inject [entry] (put-document "people" entry)))


(defrecord Contact [email address phone])


(defrecord Address [zipcode city street])


(defn create-relation
  [record1 record2]
  (Relation. record1 record2))


(defn create-project [name]
  (Project. name (now)))


(defn create-task [description]
  (Task. description (now)))


(defn create-member [name & person]
  (Member. name person))


(defn create-person [& {:keys [first-name surname birthday contact]}]
  (Person. first-name surname birthday contact))


(defn create-contact [& {:keys [email address phone]}]
  (Contact. email address phone))


(defn create-address [& {:keys [zipcode city street]}]
  (Address. zipcode city street))
