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



(defn- type-window-css []
  [:.type-window
   {:float :left
    :width (px 300)
    :margin-top (em 1.5)
    :margin-left (em 1.5)
    :border-style :solid
    :border-width (px 2)
    :border-color dark-background-color
    :list-style :none
    :padding (em 0.3)}
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

(defn- type-list-css []
  [:.type-list
   {:list-style :none
    :padding 0
    :margin-left 0}])


(defn- type-css []
  [:.type
   {:float :left
    :width (px 300)
    :color dark-background-color}])

(defn- fun-timer-container-css []
  [:#centercontainer
   {:margin :auto
    :padding-top (em 4.0)
    :width (px 600)}])


(defn- fun-timer-button-css []
  [:#timer-button
   {:float :right
     :border-style :none
     :cursor :pointer
     :color default-font-color
    :background dark-background-color}
   [:&:hover
     {:color hover-color}]])

(defn- fun-timer-css []
  [:#timer
   {:width (px 500)
    :background overall-background-color
    :margin :auto}
   [:& :a
    {:padding 0
     :font-size (pt 64)
     :text-align :center
     :font-family "Inconsolata"
     :background dark-background-color
     :color hover-color
     :vertical-align :middle}]])

(defn- wrap-css []
  [:#wrap {:height "100%"}])

(css (fun-timer-css))

(defn overall-css []
  (css
   (body-css)
   (wrap-css)
   (nav-css)
   (header-logo-css)
   (type-window-css)
   (type-list-css)
   (type-css)
   (fun-timer-container-css)
   (fun-timer-css)
   (fun-timer-button-css)))
