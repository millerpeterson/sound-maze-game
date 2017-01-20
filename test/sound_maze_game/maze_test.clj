(ns sound-maze-game.maze-test
  (:use clojure.test
        sound-maze-game.maze))

(deftest test-named-dirs
  (is (= (named-dirs [:left :down]) #{[1 0] [0 -1]})))

(deftest test-add-vec
  (is (= (add-vec [1 4] [0 5]) [1 9]))
  (is (= (add-vec [-4 2] [5 2]) [1 4]))
  (is (= (add-vec [4 -9] [2 -2]) [6 -11])))

(deftest test-scale-vec
  (is (= (scale-vec [3 2] 8) [24, 16]))
  (is (= (scale-vec [-1 2] 4) [-4 8]))
  (is (= (scale-vec [3 -4] -2) [-6 8])))

(deftest test-sub-vec
  (is (= (sub-vec [9 5] [1 1]) [8 4]))
  (is (= (sub-vec [-2 5] [0 2]) [-2 3])))

(deftest test-neighbors
  (let [ref-pos [5 6]
        nbrs (neighbors ref-pos)]
    (is (= nbrs #{[4 6] [6 6] [5 5] [5 7]}))))

; 4.....bc
; 5......d
;  0123456

(def test-rm-b (room [4 5] (named-dirs [:up :down :left]))) ; Right is open
(def test-rm-c (room [4 6] (named-dirs [:up :right]))) ; Left and Down are open
(def test-rm-d (room [5 6] (named-dirs [:up :down :left :right]))) ; None are open

(deftest test-wall-facing?
  (is (= (wall-facing? test-rm-b test-rm-c) false))
  (is (= (wall-facing? test-rm-c test-rm-b) false))
  (is (= (wall-facing? test-rm-d test-rm-c) true))
  (is (= (wall-facing? test-rm-c test-rm-d) false)))

(deftest test-passage-to?
  (is (= (passage-to? test-rm-b test-rm-c) true))
  (is (= (passage-to? test-rm-c test-rm-b) true))
  (is (= (passage-to? test-rm-c test-rm-d) false))
  (is (= (passage-to? test-rm-d test-rm-c) false)))
