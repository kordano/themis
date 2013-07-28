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
      [:div#member-container
       [:a "Members"] [:button.add-button {:type "button"} "Add"]
       [:div#memberlistcontainer
        [:ul#memberlist]]
       [:div#membercreation]]]]
    (run-clojurescript "main.js" "themis.repl.connect()")]))
;
