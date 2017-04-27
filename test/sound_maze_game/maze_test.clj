(ns sound-maze-game.maze-test
  (:use clojure.test
        sound-maze-game.maze
        sound-maze-game.demo-mazes))

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

(deftest test-neighbors-poss
  (let [ref-pos [5 6]
        nbrs (neighbor-poss ref-pos)]
    (is (= nbrs #{[4 6] [6 6] [5 5] [5 7]}))))

; 4.....bc
; 5......d
;  0123456
(defonce test-pos-b [4 5])
(defonce test-pos-c [4 6])
(defonce test-pos-d [5 6])
(defonce test-rm-b (room test-pos-b (named-dirs [:up :down :left]))) ; Right is open
(defonce test-rm-c (room test-pos-c (named-dirs [:up :right]))) ; Left and Down are open
(defonce test-rm-d (room test-pos-d (named-dirs [:up :down :left :right]))) ; None are open

(defonce test-maze (maze [test-rm-b test-rm-c test-rm-d]))

(deftest test-room-at
  (is (= (room-at test-maze test-pos-b) test-rm-b))
  (is (= (room-at test-maze test-pos-c) test-rm-c))
  (is (= (room-at test-maze test-pos-d) test-rm-d))
  (is (= (room-at test-maze [5 5]) nil))
  )

(deftest test-wall-facing?
  (is (= (wall-facing? test-maze test-pos-b test-pos-c) false))
  (is (= (wall-facing? test-maze test-pos-c test-pos-b) false))
  (is (= (wall-facing? test-maze test-pos-d test-pos-c) true))
  (is (= (wall-facing? test-maze test-pos-c test-pos-d) false))
  (is (= (wall-facing? test-maze test-pos-b test-pos-d) false))
  (is (= (wall-facing? test-maze test-pos-b test-pos-b) false)
      "No room has a wall facing itself.")
  )

(deftest test-passage-to?
  (is (= (passage-to? test-maze test-pos-b test-pos-c) true))
  (is (= (passage-to? test-maze test-pos-c test-pos-b) true))
  (is (= (passage-to? test-maze test-pos-c test-pos-d) false))
  (is (= (passage-to? test-maze test-pos-d test-pos-c) false))
  (is (= (passage-to? test-maze test-pos-d test-pos-d) true)
      "Rooms have passage to themselves.")
  )

(defonce closed-rm-middle (closed-room [3 4]))
(defonce closed-rm-right (closed-room [3 5]))
(defonce closed-rm-left (closed-room [3 3]))
(defonce closed-rm-up (closed-room [2 4]))
(defonce closed-rm-down (closed-room [4 4]))
(defonce test-closed-room-maze (maze [closed-rm-down
                                      closed-rm-down
                                      closed-rm-left
                                      closed-rm-middle]))

(deftest test-closed-room
  (is (= (passage-to? test-closed-room-maze (:pos closed-rm-middle) (:pos closed-rm-right)) false))
  (is (= (passage-to? test-closed-room-maze (:pos closed-rm-middle) (:pos closed-rm-up)) false))
  (is (= (passage-to? test-closed-room-maze (:pos closed-rm-middle) (:pos closed-rm-down)) false))
  (is (= (passage-to? test-closed-room-maze (:pos closed-rm-middle) (:pos closed-rm-left)) false))
  )

(defonce tcm (closed-maze 3 3))

(deftest test-closed-maze
  (is (= (passage-to? tcm [0 0] [0 1]) false))
  (is (= (passage-to? tcm [0 0] [1 1]) false))
  (is (= (passage-to? tcm [0 0] [1 0]) false))
  (is (= (passage-to? tcm [0 1] [0 0]) false))
  (is (= (passage-to? tcm [0 1] [1 0]) false))
  (is (= (passage-to? tcm [0 1] [1 1]) false))
  (is (= (passage-to? tcm [1 1] [0 0]) false))
  (is (= (passage-to? tcm [1 1] [1 0]) false))
  (is (= (passage-to? tcm [1 1] [0 1]) false))
  )

(deftest test-passage-opened
  (let [opened-wall (passage-opened tcm [0 1] [1 1])]
    (is (= (passage-to? opened-wall [0 1] [1 1]) true))
    (is (= (passage-to? opened-wall [1 1] [0 1]) true))
    (is (= (passage-to? opened-wall [1 0] [1 1]) false))
    (is (= (passage-to? opened-wall [1 1] [1 0]) false))
    ))

(deftest test-neighbors
  (is (= (neighbors tcm [0 0])
         #{(room-at tcm [0 1])
           (room-at tcm [1 0])}))
  (is (= (neighbors tcm [0 1])
         #{(room-at tcm [0 0])
           (room-at tcm [1 1])
           (room-at tcm [0 2])}))
  (is (= (neighbors tcm [1 1])
         #{(room-at tcm [0 1])
           (room-at tcm [2 1])
           (room-at tcm [1 2])
           (room-at tcm [1 0])}))
  )

(deftest test-passage-dirs
  (is (= (passage-dirs mvi1-maze [0 0]) #{(:right named-dir-vecs)}))
  (is (= (passage-dirs mvi1-maze [1 1]) #{(:up named-dir-vecs)
                                          (:down named-dir-vecs)}))
  (is (= (passage-dirs mvi1-maze [4 3]) #{(:up named-dir-vecs)
                                          (:left named-dir-vecs)
                                          (:right named-dir-vecs)}))
  (is (= (passage-dirs mvi1-maze [4 1]) #{(:up named-dir-vecs)
                                          (:down named-dir-vecs)
                                          (:right named-dir-vecs)}))
  )
