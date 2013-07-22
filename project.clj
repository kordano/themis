(defproject themis "0.0.1"
  :description "A decent management service"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  ;; CLJ source path
  :source-paths ["src-clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [hiccup "1.0.2"]
                 [hiccups "0.2.0"]
                 [domina "1.0.1"]
                 [webfui "0.2.1"]
                 [jayq "2.3.0"]
                 [com.cemerick/piggieback "0.0.4"]
                 [compojure "1.1.5"]
                 [garden "0.1.0-beta6"]
                 [com.ashafa/clutch "0.4.0-RC1"]]

  :ring {:handler themis.routes/app}

  :plugins [[lein-ring "0.8.3"]
            [lein-cljsbuild "0.3.2"]]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :cljsbuild {
   :repl-listen-port 9000
   :repl-launch-commands
   {"firefox" ["firefox"
               :stdout ".repl-firefox-out"
               :sterr ".repl-firefox-err"]
    }
   :builds
   [{:source-paths ["src-cljs"]
     :compiler
     {:output-to "resources/public/js/cljs.js"
      :optimizations :simple}}]}
  )
