(ns sound-maze-game.maze-test
  (:use clojure.test
        sound-maze-game.maze))

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

(def test-rm-a (room [3 4] #{[0 -1]}))
(def test-rm-b (room [4 5] #{[0 -1] [1 0] [0 1]}))
(def test-rm-c (room [4 6] #{[1 0] [0 1] [-1 0]}))

(deftest test-wall-facing
  (is (= (wall-facing? test-rm-a test-rm-b) false))
  (is (= (wall-facing? test-rm-b test-rm-c) true))
  (is (= (wall-facing? test-rm-c test-rm-b) false)))
