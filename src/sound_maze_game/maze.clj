(ns sound-maze-game.maze
  (require [clojure.math.combinatorics :as combo]
           [clojure.test :as test]))

(def dir-vecs
  "Possible direction vectors of neighbors in a maze."
  #{[-1 0]
    [1 0]
    [0, -1]
    [0, 1]})

(defn add-vec
  "Sum two vectors."
  [a b]
  (mapv #(reduce + %) (map vector a b)))

(defn scale-vec
  "Multiply a vector by a scalar."
  [v scalar]
  (mapv #(* scalar %) v))

(defn sub-vec
  "Subtract a vector from another (v1 - v2)"
  [v1 v2]
  (add-vec (scale-vec v2 -1) v1))

(defn positions
  "Possible maze room positions."
  [width height]
  (map #(into [] %)
       (combo/cartesian-product (range width) (range height))))

(defn neighbors
  "Neighbouring positions of a reference position."
  [pos]
  (into #{} (map #(add-vec %1 pos) dir-vecs)))

(defn room
  "A maze room at a given position with a given number of wall directions."
  [pos walls]
  (hash-map :pos pos :walls walls))

(defn wall-facing?
  "Whether a room (r1) has a wall facing the other (r2)."
  [r1 r2]
  (contains? (:walls r1)
             (sub-vec (:pos r2) (:pos r1))))

;; (defn passage-to?
;;   "Whether a room is open to another (i.e. neither have walls facing the other)."
;;   [r1 r2]
;;   (and ()))

(defn closed-maze
  "A two-dimensional grid of rooms; all walls are present so no room is
  accessible from any other."
  [width height]
  (mapv #(room %1 dir-vecs) (positions width height)))
