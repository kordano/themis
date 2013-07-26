(ns themis.client
  (:require [dommy.core :as dom]
            [goog.net.XhrIo :as xhr]
            [hiccups.runtime :as hiccupsrt]
            [clojure.browser.repl :as repl]
            [cljs.reader :refer [read-string]]
            [cljs.core.async :as async :refer [chan close! put!]])
  (:require-macros [hiccups.core :as h]
                   [cljs.core.async.macros :refer [go alt!]]
                   [dommy.macros :refer [sel sel1]]))

; fire up a repl for the browser and eval namespace on top once connected
#_(do (ns themis.clojure.start)
      (require 'cljs.repl.browser)
      (cemerick.piggieback/cljs-repl
       :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
                   cljs.repl/-setup)))


(defn log [s]
  (.log js/console s))


(log "Started")


(defn GET [url]
  (let [ch (chan 1)]
    (xhr/send url
              (fn [event]
                  (put! ch (-> event .-target .getResponseText))
                  (close! ch)))
    ch))


(defn get-edn [url]
  (go
   (-> (.-buf (.-buf (GET url))) <! read-string)))

(def state (GET "projects"))


(.log js/console (first (.-buf (.-buf state))))

(.log js/console (read-string (.-buf (.-buf state))))


(def c (read-string (.-buf (.-buf state))))


(go
 (log "Fetching edn ...")
 (log "Done"))



#_(js/alert "red alert!")
