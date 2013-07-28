(ns themis.views.css
  (:use [garden.core :only [css]]
        [garden.units :only [pt px em]]
        [garden.color :only [rgb lighten darken]]))


(def header-height (px 150))
(def default-font "Open Sans")
(def header-font-size (pt 12))
(def default-font-color (rgb 255 255 255))
(def hover-color (rgb 242 126 24))
(def dark-background-color (rgb 36 36 36))
(def overall-background-color (rgb 243 243 243))

(defn- body-css []
  [:body :html
   {:font-family default-font
    :font-size header-font-size
    :background overall-background-color
    :margin 0}])


(defn- nav-css []
  [:#projectnav
   {:margin 0
    :padding [0 (em 1)]
    :background dark-background-color
    :height (em 3)
    :text-decoration :none
    :list-style :none}
   ["& > li"
    {:float :left
     :margin-right (em 0.5)
     :padding [0 (em 1)]
     :cursor :pointer}
    [:& :a
     {:float :left
      :color default-font-color
      :text-decoration :none
      :line-height 3}
     [:&:hover
      {:color hover-color
       :text-decoration :none}]
     [:&.active
      {:color hover-color}]]]])


(defn- header-logo-css []
  [:#header-description
   {:float :left
    :margin-right (em 0.5)
    :padding [0 (em 1)]
    :cursor :pointer}
   [:& :a
    {:float :left
     :color hover-color
     :text-decoration :none
     :line-height 3
     :font-weight :bold}
    [:&:hover
     {:color (lighten hover-color 10)
      :text-decoration :none}]]])


(defn- member-container-css []
  [:#member-container
   {:float :left
    :margin 0
    :width (px 300)
    :padding-top (em 1.5)
    :padding-left (em 1.5)
    :list-style :none}
   ["& > a"
    {:text-align :center
     :font-weight :bold}]
   ["& > button"
    {:float :right
     :border-style :none
     :cursor :pointer
     :color default-font-color
     :background dark-background-color}
    [:&:hover
     {:color hover-color}]]])

(defn- memberlist-css []
  [:#memberlist
   {:list-style :none
    :padding 0
    :margin-left 0}])


(defn- member-css []
  [:.member
   {:float :left
    :width (px 300)
    :color dark-background-color}])


(defn overall-css []
  (css
   (body-css)
   (nav-css)
   (header-logo-css)
   (member-container-css)
   (memberlist-css)
   (member-css)))
