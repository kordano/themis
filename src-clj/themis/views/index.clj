(ns themis.views.index
  (:use themis.database
        themis.views.css
        [hiccup.core :only [html]]
        [hiccup.page :only [html5 include-js]]
        [hiccup.element :only [javascript-tag]]))


(defn projects-list []
  (let [projects (filter string? (get-all-projects))]
    [:ul#projectnav
     (map #(vector :li [:a {:href "#"} %]) projects)]))


(defn index-page []
  (html5
   [:head
    [:title "Themis"]
    [:style {:type "text/css"} (overall-css)]]
   [:body
    [:div#wrap
     [:div#header
      [:div#header-description
       [:a "Projects"]]
      [:div#header-projects-list
       (projects-list)]]
     [:div#container
      [:div#task-container
       [:a "Tasks"]
       [:ul
        [:li.task [:a "test"]]]]]]]))


#_(run-clojurescript "js/cljs.js" "themis.repl.connect()")
