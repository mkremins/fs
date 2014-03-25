(defproject fs "0.1.0-SNAPSHOT"
  :description "Filesystem utilities for ClojureScript via node.js"
  :url "http://github.com/mkremins/fs"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2173"]]

  :plugins [[lein-cljsbuild "1.0.2"]]

  :source-paths ["src"]

  :cljsbuild { 
    :builds [{:source-paths ["src"]
              :compiler {
                :output-to "target/fs.js"
                :output-dir "target/out"
                :optimizations :simple
                :source-map true}}]})
