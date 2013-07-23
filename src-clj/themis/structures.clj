(ns themis.structures
  (:refer-clojure :exclude [assoc! conj! dissoc!])
  (:require [clojure.core :as core])
  (:use [com.ashafa.clutch]))


(defn now [] (new java.util.Date))

(defprotocol database-interaction
  (inject [entry]))

(defrecord Project [_id members tasks created-at]
  database-interaction
  (inject [entry] (put-document "projects" entry)))


(defrecord Task [description assigned-to deadline working-time created-at]
  database-interaction
  (inject [entry] (put-document "tasks" entry)))


(defrecord User [_id person]
  database-interaction
  (inject [entry] (put-document "themis-users" entry)))


(defrecord Person [first-name surname birthday contact]
  database-interaction
  (inject [entry] (put-document "people" entry)))


(defrecord Contact [email address phone])


(defrecord Address [zipcode city street])


(defn create-project
  [name & {:keys [members tasks created-at] :or {created-at (now)}}]
 (Project. name members tasks created-at))


(defn create-task
  [description & {:keys [assigned-to deadline working-time created-at] :or {created-at (now)}}]
  (Task. description assigned-to deadline working-time created-at))


(defn create-user
  [name & person]
  (User. name person))


(defn create-person
  [& {:keys [first-name surname birthday contact]}]
  (Person. first-name surname birthday contact))


(defn create-contact
  [& {:keys [email address phone]}]
  (Contact. email address phone))


(defn create-address
  [& {:keys [zipcode city street]}]
  (Address. zipcode city street))
