(ns themis.datalog
  (:require [fogus.datalog.bacwn.impl.literals :as literals])
  (:use [fogus.datalog.bacwn :only (build-work-plan run-work-plan)]
        [fogus.datalog.bacwn.macros :only (<- ?- make-database)]
        [fogus.datalog.bacwn.impl.rules :only (rules-set)]
        [fogus.datalog.bacwn.impl.database :only (add-tuples)]))


(def db-base
  (make-database
   (relation :task [:id :description :project-id])
   (index :task :description)

   (relation :project [:id :name :creator])
   (index :project :name)

   (relation :user [:id :name])
   (index :user :name)

   (relation :assignment [:user-id :task-id])
   (index :assignment :user-id)))


(def db
  (add-tuples db-base
              [:task :id 1 :description "do something" :project-id 1]
              [:task :id 2 :description "do nothing" :project-id 2]
              [:task :id 3 :description "fuck you!" :project-id 1]
              [:project :id 1 :name "war" :creator 1]
              [:project :id 2 :name "peace" :creator 2]
              [:user :id 1 :name "john"]
              [:user :id 2 :name "jane"]
              [:assignment :user-id 1 :task-id 2]
              [:assignment :user-id 1 :task-id 3]
              [:assignment :user-id 2 :task-id 1]))


(def rules
  (rules-set
   (<- (:project-tasks :name ?p :task ?d)
       (:project :id ?i :name ?p)
       (:task :project-id ?i :description ?d))
   (<- (:user-tasks :name ?n :task ?d :project ?p)
       (:user :id ?u :name ?n)
       (:assignment :user-id ?u :task-id ?t)
       (:task :id ?t :description ?d :project-id ?pi)
       (:project :id ?pi :name ?p))))


(def wp-1 (build-work-plan
           rules
           (?- :project-tasks :name '??name :task ?d)))

(def wp-2 (build-work-plan
           rules
           (?- :user-tasks :name '??name :task ?d :project ?p)))

(run-work-plan wp-1 db {'??name "war"})
(run-work-plan wp-2 db {'??name "john"})
