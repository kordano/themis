(ns themis.datalog
  (:use [fogus.datalog.bacwn :only (build-work-plan run-work-plan)]
        [fogus.datalog.bacwn.macros :only (<- ?- make-database)]
        [fogus.datalog.bacwn.impl.rules :only (rules-set)]
        [fogus.datalog.bacwn.impl.database :only (add-tuples database-counts get-relation)]
        [com.ashafa.clutch])
  (:require [fogus.datalog.bacwn.impl.literals :as literals]))

(defn now [] (new java.util.Date))

(def db-base
  "db scheme"
  (make-database
   (relation :task [:id :description :project-id :user-id :creation-date])
   (index :task :description)

   (relation :project [:id :name :creator :creation-date])
   (index :project :name)

   (relation :user [:id :name :creation-date])
   (index :user :name)))


(def db
  (add-tuples db-base
              [:task :id 1 :description "do something" :project-id 1 :user-id 1 :creation-date (now)]
              [:task :id 2 :description "do nothing" :project-id 2 :user-id 2 :creation-date (now)]
              [:task :id 3 :description "fuck you!" :project-id 2 :user-id 1 :creation-date (now)]
              [:task :id 4 :description "attack!" :project-id 1 :user-id 1 :creation-date (now)]
              [:task :id 5 :description "run" :project-id 1 :user-id 2 :creation-date (now)]
              [:project :id 1 :name "war" :creator 1 :creation-date (now)]
              [:project :id 2 :name "peace" :creator 2 :creation-date (now)]
              [:user :id 1 :name "john" :creation-date (now)]
              [:user :id 2 :name "jane" :creation-date (now)]))


(def rules
  (rules-set
   (<- (:project-tasks :name ?p :task ?d)
       (:project :id ?i :name ?p)
       (:task :project-id ?i :description ?d))
   (<- (:user-tasks :name ?n :task ?d :project ?p )
       (:user :id ?u :name ?n)
       (:task :id ?t :description ?d :project-id ?pi :user-id ?u )
       (:project :id ?pi :name ?p))))


(def wp-1 (build-work-plan
           rules
           (?- :project-tasks :name '??name :task ?d)))


(def wp-2 (build-work-plan
           rules
           (?- :user-tasks :name '??name)))


;; COUCHDB stuff

(defn init-datalog-dbs []
  "Create datalog databases if not available"
  (map get-database ["datalog-user" "datalog-project" "datalog-task"]))

(defn write-to-local-db []
  "write all relations to db"
  (let [users (map #(put-document "datalog-user" %) (:data (get-relation db :user)))
        projects (map #(put-document "datalog-project" %) (:data (get-relation db :project)))
        tasks (map #(put-document "datalog-task" %) (:data (get-relation db :task)))]
    (map count [users projects tasks])))


#_(run-work-plan wp-1 db {'??name "war"})
#_(run-work-plan wp-2 db {'??name "john"})
#_(database-counts db)
#_(init-datalog-dbs)
