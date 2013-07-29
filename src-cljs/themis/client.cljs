(ns themis.client
  (:require [dommy.core :as dom]
            [hiccups.runtime :as hiccupsrt]
            [goog.net.XhrIo :as xhr]
            [cljs.reader :refer [read-string]]
            [themis.communicator :refer [get-edn post-edn]]
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


(def state
  (atom {:active-project nil
         :project-data nil
         :timer nil
         :interval-id nil
         :container-state nil}))


(defn log [s]
  (.log js/console (str s)))


(defn create-task-list [data]
  (hiccups/html
   (map #(vector :li [:a.type {:id %} %]) (:tasks data))
   [:input#add-task-field {:type "text" :name "name" :onsubmit :false}]))


(defn show-project-tasks [data]
  (let [html-task-list (create-task-list data)]
    (-> (sel1 :#task-list)
        (dom/set-html! html-task-list))))


(defn create-member-list [data]
  (hiccups/html
   (map #(vector :li [:a.type {:id %} %]) (:members data))
   [:input#add-member-field {:type "text" :name "name" :onsubmit :false}]))


(defn show-project-members [data]
  (let [html-member-list (create-member-list data)]
    (-> (sel1 :#member-list)
        (dom/set-html! html-member-list))))


(defn activate-project [id]
  (go
   (let [data (<! (get-edn (str "projects/" id)))]
     (show-project-members data)
     (show-project-tasks data)
     (doseq [project (sel :.project)]
       (dom/remove-class! project :active))
     (-> (sel1 (keyword (str "#" id)))
         (dom/add-class! :active))
     (swap! state assoc :active-project id :project-data data))))


(defn set-onclick-project [id]
  (do
    (set! (.-onclick
           (sel1 (keyword (str "#" id))))
          (fn [] (activate-project id)))))


(defn show-all-projects []
  (go
   (let [data (<! (get-edn "projects"))
         names (map #(:_id %) data)
         html-list (hiccups/html (map #(vector :li [:a.project {:id %} %]) names))]
     (-> (sel1 :#projectnav)
         (dom/set-html! html-list))
     (log (map #(set-onclick-project %) names)))))


(defn send-member-data []
  (go
   (let [data (<! (post-edn
                   "/insert/member/"
                   {:name (dom/value (sel1 :#add-member-field))
                    :project (:active-project (deref state))}))
         html-member-list (create-member-list data)]
     (-> (sel1 :#member-list)
         (dom/set-html! html-member-list)))))


(defn send-task-data []
  (go
   (let [data (<! (post-edn
                   "/insert/task/"
                   {:name (dom/value (sel1 :#add-task-field))
                    :project (:active-project (deref state))}))
         html-task-list (create-task-list data)]
     (-> (sel1 :#task-list)
         (dom/set-html! html-task-list)))))




(defn timer-init []
  (let [time 90]
    (do (swap! state assoc :container-state (-> (sel1 :#container) dom/html))
        (-> (sel1 :#container)
            (dom/set-html!
             (hiccups/html
              [:div#centercontainer
               [:div#timer
                [:a#timertext (str time)]]])))
        (swap! state assoc :timer time))))


(defn reinstate []
  (-> (sel1 :#container)
      (dom/set-html! (:container-state (deref state)))))

(defn countdown-timer []
  (let [time (dec (:timer (deref state)))]
    (if (> time 0)
      (go
       (-> (sel1 :#timertext)
           (dom/set-text! (str time)))
       (swap! state assoc :timer (dec time)))
      (go
       (.clearInterval js/window (:interval-id (deref state)))
       (-> (sel1 :#timertext)
           (dom/set-text! "BOOM!"))
       (.setTimeout js/window reinstate 1000)))))

(defn run-timer []
  (do
    (timer-init)
    (swap! state assoc :interval-id (.setInterval js/window countdown-timer 1000))))

(defn init []
  (do
    (set! (.-onclick (sel1 :#header-description)) (fn [] (show-all-projects)))
    (set! (.-onclick (sel1 :#member-add-button)) (fn [] (send-member-data)))
    (set! (.-onclick (sel1 :#task-add-button)) (fn [] (send-task-data)))
    (set! (.-onclick (sel1 :#timer-button)) (fn [] (run-timer)))))

#_(init)

(set! (.-onload js/window) init)
