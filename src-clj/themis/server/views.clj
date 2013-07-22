(ns themis.server.views
  (:use themis.database
        [garden.core :only [css]]
        [garden.units :only [pt px em]]
        [garden.color :as color]
        [hiccup.core :only [html]]
        [hiccup.page :only [html5 include-js]]
        [hiccup.element :only [javascript-tag]]))


(def header-height (px 150))
(def default-font "Open Sans")
(def header-font-size (pt 16))
(def default-font-color "#FFFFFF")
(def hover-color "#F27E18")
(def default-background-color "#242424")

(defn body-css []
  [:body :html
   {:font-family default-font
    :font-size header-font-size
    :margin 0}])


(defn nav-css []
  [:#projectnav
   {:margin 0
    :padding [0 (em 1)]
    :background default-background-color
    :height (em 3)
    :text-decoration :none
    :list-style :none}
   ["& > li"
    {:float :left
     :margin-right (em 0.5)
     :padding [0 (em 1)]}
    [:& :a
     {:float :left
      :color default-font-color
      :text-decoration :none
      :line-height 3
      :fint-weight :bold
      :text-transform :uppercase
      }
     [:&:hover
      {:color hover-color
       :text-decoration :none}]]]])


(defn overall-css []
  (css
   (body-css)
   (nav-css)))


(defn projects-list []
  (let [projects (filter string? (get-all-projects))]
    [:ul#projectnav
     (map #(vector :li [:a {:href "#"} %]) projects)]))


(defn index-page []
  (html5
   [:head
    [:title "Themis Management Tool"]
    [:style {:type "text/css"} (overall-css)]]
   [:body
    [:div#wrap
     [:div#projects
      (projects-list)]]]
   ))


#_(run-clojurescript "js/cljs.js" "themis.repl.connect()")
