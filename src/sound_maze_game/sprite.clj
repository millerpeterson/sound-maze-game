(ns sound-maze-game.sprite
  (require [sound-maze-game.colors :as led]
           [clojure.math.combinatorics :as combo]))

(def nrows
  "Number of rows."
  8)

(def ncols
  "Number of cols."
  8)

(defn positions
  "Possible sprite led positions."
  []
  (into #{} (combo/cartesian-product (range nrows) (range ncols))))

(defn rect-region
  "Led positions in a rectangular region."
  [row-start col-start width height]
  (into #{} (filter (fn [[r c]] (and (>= r row-start)
                                     (<= r (+ row-start (- width 1)))
                                     (>= c col-start)
                                     (<= c (+ col-start (- height 1)))))
                    (positions))))

(defn gen
  "Create a new sprite according to a function."
  [gen-fn]
  (reduce #(assoc %1 %2 (gen-fn %2)) {} (positions)))

(defn blank
  "Empty sprite."
  []
  (gen (fn [_] (led/named :black))))
