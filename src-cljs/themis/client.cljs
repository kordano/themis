(ns themis.client
  (:use [themis.repl :only [connect]]
        [webfui.dom :only [defdom]])
  (:require [clojure.browser.repl :as repl]
            [clojure.browser.event :as event]
            [hiccups.runtime :as hiccupsrt]
            [domina :as dom])
  (:require-macros [hiccups.core :as hiccups]))

; fire up a repl for the browser and eval namespace on top once connected
#_(do (ns themis.clojure.start)
      (require 'cljs.repl.browser)
      (cemerick.piggieback/cljs-repl
       :repl-env (doto (cljs.repl.browser/repl-env :port 9000)
                   cljs.repl/-setup)))

#_(js/alert "red alert!")
