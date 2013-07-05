(ns themis.structures)

(defn generate-id []
  (rand-int 1024))

(defn generate-project [name people code git tasks]
  {:_id (generate-id)
   :codename name
   :people people
   :codebase files
   :git-url git
   :tasks tasks
   })

(defn generate-person [])
