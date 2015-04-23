(defproject anglican-user "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [nstools "0.2.4"]
                 [anglican "0.6.6-SNAPSHOT"]]
  :plugins [[lein-gorilla "0.3.4"]]
  :resource-paths ["programs"]
  :main ^:skip-aot anglican.core)
