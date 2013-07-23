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
    (get-database "users")
    (get-database "people")))


(defprotocol database-interaction
  (inject [entry]))


(defn get-all-projects []
  )



(inject
  (create-person
   :first-name "Konrad"
   :surname "Kühne"
   :birthday "15.04.1985"
   :contact (create-contact
             :email "konnykuehne@googlemail.com"
             :address (create-address
                       :zipcode "69151"
                       :city "Neckargemünd"
                       :street "Kurt-Lindemannstrasse 5")
             :phone "0622372661")))

(inject (create-user "kordano"))
