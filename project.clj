(defproject mkremins/fs "0.2.0"
  :description "Filesystem utilities for ClojureScript via node.js"
  :url "http://github.com/mkremins/fs"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"
            :distribution :repo}

  :dependencies
  [[org.clojure/clojure "1.6.0"]
   [org.clojure/clojurescript "0.0-2202"]]

  :node-dependencies
  [[mkdirp "0.4.0"]]

  :plugins
  [[lein-cljsbuild "1.0.3"]
   [lein-npm "0.4.0"]]

  :source-paths ["src"]

  :cljsbuild { 
    :builds [{:source-paths ["src"]
              :compiler {
                :output-to "target/fs.js"
                :output-dir "target/out"
                :optimizations :simple}}]})
