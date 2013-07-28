(ns themis.client
  (:require [dommy.core :as dom]
            [hiccups.runtime :as hiccupsrt]
            [goog.net.XhrIo :as xhr]
            [cljs.reader :refer [read-string]]
            [cljs.core.async :as async :refer [chan close! put!]])
  (:require-macros [cljs.core.async.macros :refer [go alt!]]
                   [hiccups.core :as hiccups]
                   [dommy.macros :refer [sel sel1]]))


; fire up a repl for the browser and eval namespace on top once connected
#_(do (ns metis.clojure.start)
      (require 'cljs.repl.browser)
      (cemerick.piggieback/cljs-repl
       :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
                   cljs.repl/-setup)))


(defn log [s]
  (.log js/console (str s)))


(defn GET [url]
  (let [ch (chan 1)]
    (xhr/send url
              (fn [event]
                (put! ch (-> event .-target .getResponseText))
                (close! ch)))
    ch))


(defn get-edn [url]
  (go
   (-> (GET url) <! read-string)))


(defn show-project-members [id]
  (go
   (let [data (<! (get-edn (str "projects/" id)))
         html-member-list (hiccups/html (map #(vector :li [:a {:id %} %]) (:members data)))]
     (-> (sel1 :#memberlist)
         (dom/set-html! html-member-list)))))


(defn make-project-active [id]
  (do
    (log (str id " active"))
    (doseq [project (sel :.project)]
      (dom/remove-class! project :active))
    (-> (sel1 (keyword (str "#" id)))
        (dom/add-class! :active))))


(defn set-onclick-project [id]
  (do
    (set! (.-onclick
           (sel1 (keyword (str "#" id))))
          (fn [] (do (show-project-members id)
                    (make-project-active id))))))


(defn show-all-projects []
  (go
   (let [data (<! (get-edn "projects"))
         names (map #(:_id %) data)
         html-list (hiccups/html (map #(vector :li [:a.project {:id %} %]) names))]
     (-> (sel1 :#projectnav)
         (dom/set-html! html-list))
     (log (map #(set-onclick-project %) names)))))


(defn init []
  (do
    (set! (.-onclick (sel1 :#header-description)) (fn [] (show-all-projects)))))


#_(init)

(set! (.-onload js/window) init)
