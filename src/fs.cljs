(ns fs)

(def fs (js/require "fs"))
(def path-sep (.-sep (js/require "path")))

(defn slurp
  "Returns a string of the contents of the file at `fpath`."
  [fpath]
  (.readFileSync fs fpath #js {:encoding "utf8"}))

(defn spit
  "Writes a string `contents` to the file at `fpath`, overwriting whatever the
  file previously contained."
  [fpath contents]
  (.writeFileSync fs fpath contents))

(defn dir?
  "Returns true if the file at `fpath` is a directory, otherwise false."
  [fpath]
  (let [stats (.statSync fs fpath)]
    (.-isDirectory stats)))

(defn ls
  "Given a path to a directory `dirpath`, returns a shallow seq of subpaths
  contained within. If `relative?` (default true) is false, all subpaths are
  prepended with `dirpath`."
  ([dirpath] (ls dirpath true))
  ([dirpath relative?]
    (let [subpaths (.readdirSync fs dirpath)]
      (if relative?
          (map (partial str dirpath path-sep) subpaths)
          (seq subpaths)))))

(defn files-seq
  "Returns a lazy sequence of filepaths under root `fpath`."
  [fpath]
  (tree-seq dir? #(ls % false) fpath))
