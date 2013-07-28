(defproject themis "0.0.2"
  :description "A decent management service"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  ;; CLJ source path
  :source-paths ["src-clj"]
  :dependencies [ ;; Server side
                 [org.clojure/clojure "1.5.1"]
                 [ring "1.2.0"]
                 [ring-middleware-format "0.3.0"]
                 [hiccup "1.0.2"]
                 [cheshire "5.2.0"]
                 [garden "0.1.0-beta6"]
                 [com.ashafa/clutch "0.4.0-RC1"]
                 [compojure "1.1.5"]
                 ;; Client side
                 [org.clojure/clojurescript "0.0-1820"]
                 [org.clojure/core.async "0.1.0-SNAPSHOT"]
                 [prismatic/dommy "0.1.1"]
                 [hiccups "0.2.0"]
                 [com.cemerick/piggieback "0.0.4"]]

  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"}

  :ring {:handler themis.core/app}

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
     {:output-to "resources/public/main.js"
      :optimizations :simple}}]})
