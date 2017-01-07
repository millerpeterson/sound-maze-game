(ns sound-maze-game.colors
  (require [clojure.math.combinatorics :as combo]))

(def r-depth
  "Red color depth."
  4)

(def g-depth
  "Green color depth."
  4)

(def named
  "Named colors."
  {:black [0 0]
   :red   [3 0]
   :green [0 3]})

(defn red
  "Return the red component of a color."
  [color]
  (first color))

(defn green
  "Return the green component of a color."
  [color]
  (second color))

(defn palette
  "Possible led colors."
  []
  (into #{} (combo/cartesian-product (range r-depth)
                                     (range g-depth))))
(defn random
  "Choose a random color. Pass r-hold or g-hold to fix r/g."
  ([& {:keys [r-hold g-hold]
       :or {r-hold :off g-hold :off}}]
    (vec [(if (= r-hold :off) (rand-int r-depth) r-hold)
          (if (= g-hold :off) (rand-int g-depth) g-hold)])))
