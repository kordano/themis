(defproject themis "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src-clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [com.cemerick/piggieback "0.0.4"]
                 [hiccup "1.0.2"]
                 [compojure "1.1.5"]
                 [cssgen "0.3.0-alpha1"]
                 [garden "0.1.0-beta6"]
                 [com.ashafa/clutch "0.4.0-RC1"]]
  :ring {:handler themis.routes/app}

  :plugins [[lein-ring "0.8.3"]])
