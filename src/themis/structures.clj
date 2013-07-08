(ns themis.structures
  (:use com.ashafa.clutch))


(defn generate-project [name id & [people code tasks]]
  {:_id id
   :codename name
   :people people
   :codebase code
   :tasks tasks})

(defn generate-person [first-name surname & [call-sign git-acc mail]]
  {:first-name first-name
   :surname surname
   :call-sign call-sign
   :email mail
   :git git-acc})

(defn generate-task [name description project & [people files]]
  {:name name
   :project project
   :people people
   :description description
   :files files})

(defn generate-codebase [main-folder & [git]]
  {:main-folder main-folder
   :git git})

#_(def ceres (generate-project "ceres" "0005" (generate-person "Konrad" "Kühne" "Konny" "kordano" "konrad.kuehne@rocketmail.com") (generate-codebase "~/projects/ceres" "https://github.com/kordano/ceres") (generate-task "apply place-record-linkage" "this and that" "ceres" "konny" "~/projects/ceres/index2.py")))

#_(put-document "projects" ceres)
