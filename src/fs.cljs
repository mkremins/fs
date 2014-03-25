(ns fs)

(def fs (js/require "fs"))
(def mkdirp (js/require "mkdirp"))
(def path (js/require "path"))
(def path-sep (.-sep path))

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

(defn basename
  "Returns the last portion of `fpath`. If `extname` is specified, drops the
  extension whose name is `extname` from the returned filename."
  ([fpath] (.basename path fpath))
  ([fpath extname] (.basename path fpath (str "." extname))))

(defn extname
  "Returns the name of `fpath`'s extension – the part of the filename that
  follows the final period – or nil if `fpath` has no extension."
  [fpath]
  (let [ext (.extname path fpath)]
    (when (> (count ext) 1)
      (subs ext 1))))

(defn rel-path
  "Returns path `fpath` made relative to path `basepath`."
  [basepath fpath]
  (.relative path basepath fpath))

(defn mkdirs
  "Creates any missing directories in `dirpath`."
  [dirpath]
  (.sync mkdirp dirpath))
