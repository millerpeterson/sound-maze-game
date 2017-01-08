(ns sound-maze-game.maze
  (require [clojure.math.combinatorics :as combo]))

(def dir-vecs
  "Possible direction vectors of neighbors in a maze."
  [[-1 0]
   [1 0]
   [0, -1]
   [0, 1]])

(defn add-vec
  "Sum two vectors."
  [a b]
  (mapv #(reduce + %) (map vector a b)))

(defn positions
  "Possible maze room positions."
  [width height]
  (mapv #(into [] %)
       (combo/cartesian-product (range width) (range height))))

(defn neighbors
  "Neighbouring positions of a reference position."
  [pos]
  (map #(add-vec %1 pos) dir-vecs))

(defn room
  "A maze room at a given position with a given number of wall directions."
  [pos walls]
  (hash-map :pos pos :walls walls))

(defn closed-maze
  "A two-dimensional grid of rooms; all walls are present so no room is
  accessible from any other."
  [width height]
  (mapv #(room %1 dir-vecs) (positions width height)))
