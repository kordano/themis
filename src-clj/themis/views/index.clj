(ns themis.views.index
  (:use themis.database
        themis.views.css
        [hiccup.core :only [html]]
        [hiccup.page :only [html5 include-js]]
        [hiccup.element :only [javascript-tag]])
  (:import [themis.structures Task Project Member Person Contact Address]))


(defn- run-clojurescript [path init]
  (list
   (include-js path)
   (javascript-tag init)))


(defn list-window [type title]
  [:div {:id (str type "-window") :class "type-window"}
       [:a title]
   [:div {:id (str type "-list-window")}
    [:ul {:id (str type "-list") :class "type-list"}]]])


(defn action-bar []
  [:div#action-bar.bar
   [:ul
    [:li#project-add-action [:a "&#9773;"]]
    [:li#type-add-action [:a "&#8853;"]]
    [:li [:a "&#8854;"]]]])


(defn index-page []
  (html5
   [:head
    [:title "Themis"]
    [:style {:type "text/css"} (overall-css)]
    (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js")]
   [:body
    [:div#wrap
     [:div#header
      [:div#header-description
       [:a#projects "Projects"]]
      [:div#header-projects-list
       [:ul#projectnav]]]
     (action-bar)
     [:div#container
      (list-window "task" "Tasks")
      [:div#creation-container]]]
    (run-clojurescript "main.js" "themis.repl.connect()")]))


(defn tabs-header []
  [:div#tabs-header
   [:ul
    [:li [:a {:href "#"} "This"]]
    [:li [:a#selected {:href "#"} "That"]]
    [:li [:a {:href "#"} "Other"]]
    [:li [:a {:href "#"} "Banana"]]]])

(defn tabs-content []
  [:div#tabs-content
   [:p "Alles aber ist geworden, es gibt keine ewigen Tatsachen: so wie es keine absoluten Wahrheiten gibt."]])

(defn tab-page []
  (html5
   [:head
    [:title "Themis"]
    [:style {:type "text/css"} (overall-css)]
    (include-js "http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js")]
   [:body
    [:div#wrap
     [:div#header
      [:div#header-description
       [:a#projects "Projects"]]
      [:div#header-projects-list
       [:ul#projectnav]]]
     [:div#container
      (tabs-header)
      (tabs-content)]]
    (run-clojurescript "main.js" "themis.repl.connect()")]))
