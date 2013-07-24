(ns themis.client
  (:require-macros [hiccups.core :as h])
  (:require [domina :as dom]
        [hiccups.runtime :as hiccupsrt]
        [domina.events :as ev]
        [cljs.reader :refer [read-string]]
        [ajax.core :refer [GET]]
        [jayq.core :as jq]
        [clojure.browser.repl :as repl]))

; fire up a repl for the browser and eval namespace on top once connected
#_(do (ns themis.clojure.start)
      (require 'cljs.repl.browser)
      (cemerick.piggieback/cljs-repl
       :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
                   cljs.repl/-setup)))

(defn ajax-call [target] (jq/ajax target))

(def projects-state (jq/ajax "projects"))

(defn get-all-projects [raw-data]
  (let [projects-list (js->clj (JSON/parse (.-responseText raw-data)))]
    (map #(% "_id") projects-list)))


(defn show-projects [p-list]
  (let [html-list (h/html (map #(vector :li [:a {:id %} %]) p-list))]
    (set! (.-innerHTML (dom/by-id "projectnav")) html-list)))


(defn init-all []
  (do
    (set!  (.-onclick (dom/by-id "projects")) (fn [] (show-projects (get-all-projects projects-state))))))


(set! (.-onload js/window) init-all)


#_(js/alert "red alert!")
