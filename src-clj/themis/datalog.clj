(ns themis.datalog
  (:use [fogus.datalog.bacwn :only (build-work-plan run-work-plan)]
        [fogus.datalog.bacwn.macros :only (<- ?- make-database)]
        [fogus.datalog.bacwn.impl.rules :only (rules-set)]
        [fogus.datalog.bacwn.impl.database :only (add-tuples add-tuple database-counts get-relation)]
        [com.ashafa.clutch :exclude (:assoc!)])
  (:require [fogus.datalog.bacwn.impl.literals :as literals]))


(defn now [] (new java.util.Date))



                                        ; ---- COUCHDB stuff ----

(defn get-all-ids [database]
  (map #(:id %) (all-documents database)))


(defn get-all-documents [database]
  (map #(get-document database %) (get-all-ids database)))


(defn init-datalog-dbs []
  "Create datalog databases if not available"
  (map get-database ["datalog-user" "datalog-project" "datalog-task"]))


(defn write-to-local-db []
  "write all relations to db"
  (let [users (map #(put-document "datalog-user" %) (:data (get-relation db :user)))
        projects (map #(put-document "datalog-project" %) (:data (get-relation db :project)))
        tasks (map #(put-document "datalog-task" %) (:data (get-relation db :task)))]
    (map count [users projects tasks])))


(defn convert-to-datalog-entry [entry db]
  (let [raw-entry (dissoc entry :_rev :_id)
        raw-keys (keys raw-entry)]
    (apply vector (keyword db) (flatten (map #(vector % (% raw-entry)) raw-keys)))))


(defn get-couchdb-entries []
  (let [users (get-all-documents "datalog-user")
        tasks (get-all-documents "datalog-task")
        projects (get-all-documents "datalog-project")]
    {:user users
     :task tasks
     :project projects}))


(defn convert-entries []
  (let [users (map #(convert-to-datalog-entry % "user") (:user (get-couchdb-entries)))
        tasks (map #(convert-to-datalog-entry % "task") (:task (get-couchdb-entries)))
        projects (map #(convert-to-datalog-entry % "project") (:project (get-couchdb-entries)))]
    (apply conj users (apply conj projects tasks))))



                                        ; ----- datalog magic -----

(def db-base
  (make-database
   (relation :task [:id :description :project-id :user-id :creation-date])
   (index :task :description)

   (relation :project [:id :name :creator :creation-date])
   (index :project :name)

   (relation :user [:id :name :creation-date])
   (index :user :name)))


(defn db [tuples]
  (apply add-tuples db-base tuples))


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



                                        ; ---- run queries ----

(defn get-project-tasks [name]
  (run-work-plan wp-1 (db converted-entries) {'??name name}))


(defn get-user-tasks [name]
  (run-work-plan wp-2 (db converted-entries) {'??name name}))



                                        ; ---- TESTING ----

#_(get-project-tasks "war")
#_(database-counts (db converted-entries))
;eval only once to initialize couch db entries
#_(init-datalog-dbs)
#_(write-to-local-db)
#_(get-user-tasks "john")
#_ (convert-entries)
#_ (get-couchdb-entries)
