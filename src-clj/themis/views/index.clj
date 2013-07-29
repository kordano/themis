(ns themis.views.index
  (:use themis.database
        themis.views.css
        [hiccup.core :only [html]]
        [hiccup.page :only [html5 include-js]]
        [hiccup.element :only [javascript-tag]]))

(defn- run-clojurescript [path init]
  (list
   (include-js path)
   (javascript-tag init)))

(defn list-window [type title]
  [:div {:id (str type "-window") :class "type-window"}
       [:a title] [:button.add-button {:type "button" :id (str type "-add-button")} "Add"]
   [:div {:id (str type "-list-window")}
        [:ul {:id (str type "-list") :class "type-list"}]]])


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
     [:div#container
      (list-window "task" "Tasks")
      (list-window "member" "Members")]]
    (run-clojurescript "main.js" "themis.repl.connect()")]))
;
