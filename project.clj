(defproject anglican-user "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [nstools "0.2.4"]
                 [anglican "1.1.0"]]
  :plugins [[org.clojars.benfb/lein-gorilla "0.7.0"]]
  :resource-paths ["programs"]
  :main ^:skip-aot anglican.core)
