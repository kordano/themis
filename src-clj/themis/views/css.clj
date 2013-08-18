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
    :height "100%"
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
     :padding-left (em 0.5)
     :color hover-color
     :text-decoration :none
     :line-height 3
     :font-weight :bold}
    [:&:hover
     {:color (lighten hover-color 10)
      :text-decoration :none}]]])


(defn- close-button-css []
  [:.close-button
    {:border-style :none
     :float :right
     :cursor :pointer
     :color default-font-color
     :background dark-background-color}
    [:&:hover
     {:color hover-color}]])


(defn- submit-button-css []
  [:.submit-button
    {:border-style :none
     :cursor :pointer
     :width "100%"
     :font-size (pt 13)
     :color default-font-color
     :background dark-background-color}
    [:&:hover
     {:color hover-color}]])

(defn- type-window-css []
  [:.type-window
   {:float :left
    :width "50%"
    :margin-top (em 1.5)
    :margin-left (em 1.5)
    :border-style :solid
    :border-width (px 1)
    :border-color dark-background-color
    :list-style :none
    :padding (em 0.3)}
   ["& > a"
    {:text-align :center
     :font-weight :bold}]])

(defn- type-list-window-css []
  [:.type-list-window
   {:border-style :solid
    :border-color dark-background-color
    :border-width (px 1)
    :height "100%"
    :width "100%"}])

(defn- type-list-css []
  [:.type-list
   {:list-style :none
    :padding 0
    :margin-left 0}
   [:li
    {:height "100%"}]])

(defn- type-css []
  [:.type
   {:float :left
    :width "100%"
    :color dark-background-color}])


(defn- type-creation-window-css []
  [:.creation-window
   {:float :left
    :list-style :none
    :width "25%"
    :margin-top (em 1.5)
    :margin-left (em 1.5)
    :padding (em 0.3)
    :border-style :solid
    :border-width (px 1)
    :border-color dark-background-color}
   ["& > a"
    {:text-align :center
     :font-weight :bold}]
   [:ul
    {:margin 0
     :padding 0}
    [:li
     {:list-style :none
      :padding 0
      :margin 0}]]])


(defn- wrap-css []
  [:#wrap {:height "100%"}])


(defn- action-bar-css []
  [:#action-bar
   {:float :left
    :padding 0
    :margin 0
    :background dark-background-color
    :height "100%"}
   [:ul
    {:padding 0
     :margin 0
     :list-style :none}
    [:li {}
     [:a
      {:display :block
       :background dark-background-color
       :padding (em 0.1)
       :margin 0
       :font-size (px 32)
       :cursor :pointer
       :color default-font-color}
      [:&:hover
       {:color hover-color}]]]]])


(defn- input-field-css []
  [:.input-field
   {:float :right
    :padding 0
    :margin 0}])


(defn- tabs-header-css []
  [:#tabs-header
   [:ul
    {:list-style :none
     :padding 0
     :margin 0}]
   [:li
    {:display :inline
     :border-style :solid
     :border-top-width (px 1)
     :border-right-width (px 1)
     :border-bottom-width 0
     :border-left-width (px 1)
     :margin-top 0
     :margin-right (em 0.5)
     :margin-bottom 0
     :margin-left 0}
    [:a
     {:padding-top 0
      :padding-bottom 0
      :padding-right (em 1)
      :padding-left (em 1)}]]])

(defn- tabs-content-css []
  [:#tabs-content
   {:border :none}])

(defn overall-css []
  (css
   (body-css)
   (wrap-css)
   (nav-css)
   (header-logo-css)
   (type-window-css)
   (type-list-css)
   (type-css)
   (tabs-header-css)
   (tabs-content-css)
   (action-bar-css)
   (type-creation-window-css)
   (input-field-css)
   (close-button-css)
   (submit-button-css)))
