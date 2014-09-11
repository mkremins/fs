(ns fs.core)

(def fs (js/require "fs"))
(def mkdirp (js/require "mkdirp"))
(def path (js/require "path"))
(def path-sep (.-sep path))

(defn cwd
  "Returns the absolute path to the current working directory."
  []
  (.cwd js/process))

(defn cd
  "Changes the current working directory to the directory at `dirpath`. Returns
   the absolute path to the new working directory. Throws an error if the file
   at `dirpath` does not exist or is not a directory."
  [dirpath]
  (.chdir js/process dirpath)
  (cwd))

(defn slurp
  "Returns a string of the contents of the file at `fpath`. `opts` are those of
  node.js's `fs.readFileSync`."
  [fpath & opts]
  (let [opts (merge {:encoding "utf8"} (apply hash-map opts))]
    (.readFileSync fs fpath (clj->js opts))))

(defn spit
  "Writes a string `contents` to the file at `fpath`, overwriting whatever the
  file previously contained. `opts` are those of node.js's `fs.writeFileSync`."
  [fpath contents & opts]
  (let [opts (apply hash-map opts)]
    (.writeFileSync fs fpath contents (clj->js opts))))

(defn dir?
  "Returns true if the file at `fpath` is a directory, otherwise false."
  [fpath]
  (let [stats (.statSync fs fpath)]
    (.isDirectory stats)))

(defn files-seq
  "Returns a lazy sequence of filepaths under root `fpath`."
  [fpath]
  (letfn [(ls [dirpath]
            (map (partial str dirpath path-sep)
                 (.readdirSync fs dirpath)))]
    (tree-seq dir? ls fpath)))

(defn dirname
  "Returns the directory portion of `fpath`."
  [fpath]
  (.dirname path fpath))

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
  (.sync mkdirp (dirname dirpath)))
