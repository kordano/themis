(ns themis.windows
  (:require [dommy.core :as dom]
            [hiccups.runtime :as hiccupsrt])
  (:require-macros [hiccups.core :as h]
                   [dommy.macros :refer [sel sel1]]))


(defn create-list-window [type title]
  [:div {:id (str type "-window") :class "type-window"}
       [:a title] [:button.add-button {:type "button" :id (str type "-add-button")} "Add"]
   [:div {:id (str type "-list-window")}
        [:ul {:id (str type "-list") :class "type-list"}]]])


(defn create-addition-window [type title type-keys]
  [:div {:id (str type "-creation-window") :class "creation-window"}
   [:a title] [:button#task-close-button.close-button {:type "button"} "X"]
   [:ul (map #(vector :li [:a %] [:input {:id (str type "-" % "-input-field") :type "text" :name % :class "input-field"}]) (type-keys type))]
   [:button.submit-button {:id (str type "-submit-button")} "Submit"]])
