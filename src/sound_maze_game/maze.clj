(ns sound-maze-game.maze
  (require [clojure.math.combinatorics :as combo]))

(def named-dir-vecs
  "Named direction vectors of neighbors in a maze."
  (hash-map :left [0 -1]
            :right [0 1]
            :up [-1 0]
            :down [1 0]))

(def dir-vecs
  "Possible direction vectors of neighbors in a maze."
  (into #{} (vals named-dir-vecs)))

(defn named-dirs
  "Retrn a list of dirs corresponding to a list of names."
  [dir-names]
  (into #{} (vals (select-keys named-dir-vecs dir-names))))

(defn add-vec
  "Sum two vectors."
  [a b]
  (mapv #(reduce + %) (map vector a b)))

(defn scale-vec
  "Multiply a vector by a scalar."
  [v scalar]
  (mapv #(* scalar %) v))

(defn sub-vec
  "Subtract a vector from another (v1 - v2)."
  [v1 v2]
  (add-vec (scale-vec v2 -1) v1))

(defn positions
  "Possible maze room positions."
  [width height]
  (map #(into [] %)
       (combo/cartesian-product (range width) (range height))))

(defn neighbor-poss
  "Neighboring positions of a reference position."
  [pos]
  (into #{} (map #(add-vec %1 pos) dir-vecs)))

(defn room
  "A maze room at a given position with a given number of wall directions."
  [pos walls]
  (hash-map :pos pos :walls walls))

(defn room-at
  "Return the room in a maze a given position, or nil if no such room exists."
  [maze pos]
  (get maze pos))

(defn wall-facing?
  "Whether a room in a maze has a wall facing another."
  [mz pos1 pos2]
  (let [[r1 r2] (mapv #(room-at mz %) [pos1 pos2])
        vec-diff (sub-vec pos2 pos1)]
    (and (not (= [0 0] vec-diff))
         (contains? (:walls r1) vec-diff))))

(defn passage-to?
  "Whether a room is open to another (i.e. neither have walls facing the other,
  and they are adjacent)."
  [mz pos1 pos2]
  (let [vec-diff (sub-vec pos2 pos1)]
        (or (= vec-diff [0 0])
            (and (contains? dir-vecs vec-diff)
                 (not (wall-facing? mz pos1 pos2))
                 (not (wall-facing? mz pos2 pos1))))))

(defn closed-room
  "A room at a given position with walls on each side."
  [pos]
  (room pos dir-vecs))

(defn maze
  "A collection of rooms."
  [rooms]
  (reduce (fn [pos-map room]
            (assoc pos-map (:pos room) room))
          {} rooms))

(defn rooms
  "Maze rooms."
  [maze]
  (vals maze))

(defn closed-maze
  "A two-dimensional grid of rooms; all walls are present so no room is
  accessible from any other."
  [rows cols]
  (maze (mapv closed-room (positions cols rows))))

(defn wall-opened
  "A room with a given wall opened towards another."
  [r1 r2]
  (assoc r1 :walls
         (disj (:walls r1) (sub-vec (:pos r2) (:pos r1)))))

(defn passage-opened
  "The given maze with passage opened between the room at pos1 and the room at pos2."
  [mz pos1 pos2]
  (let [[r1 r2] (mapv #(room-at mz %) [pos1 pos2])]
    (-> mz
        (assoc pos1 (wall-opened r1 r2))
        (assoc pos2 (wall-opened r2 r1)))))

(defn neighbors
  "Neighboring rooms in a maze relative to a reference position."
  [mz pos]
  (into #{} (remove nil? (map #(room-at mz %) (neighbor-poss pos)))))

(defn passage-dests
  [mz pos]
  "Rooms in a maze that a reference position has passage to."
  (into #{} (filter #(passage-to? mz pos (:pos %)))
                    (neighbors mz pos)))

(defn passage-dirs
  [mz pos]
  "Directions in a maze that a reference position has passage towards."
  (let [ref-room (room-at mz pos)]
    (into #{} (map #(sub-vec (:pos %) (:pos ref-room))
                   (passage-dests mz pos)))))
