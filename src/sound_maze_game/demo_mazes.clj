(ns sound-maze-game.demo-mazes
  (require [sound-maze-game.maze :as maze]))

(def mvi1-maze
  (-> (maze/closed-maze 8 8)
      (maze/passage-opened [0 0] [0 1])
      (maze/passage-opened [0 1] [1 1])
      (maze/passage-opened [1 1] [2 1])
      (maze/passage-opened [2 1] [3 1])
      (maze/passage-opened [2 1] [2 2])
      (maze/passage-opened [2 2] [2 3])
      (maze/passage-opened [2 3] [3 3])
      (maze/passage-opened [3 1] [3 0])
      (maze/passage-opened [3 1] [4 1])
      (maze/passage-opened [4 1] [4 2])
      (maze/passage-opened [4 1] [5 1])
      (maze/passage-opened [5 1] [6 1])
      (maze/passage-opened [6 1] [6 0])
      (maze/passage-opened [6 1] [6 2])
      (maze/passage-opened [6 2] [6 3])
      (maze/passage-opened [6 3] [5 3])
      (maze/passage-opened [5 3] [5 4])
      (maze/passage-opened [4 2] [4 3])
      (maze/passage-opened [3 3] [4 3])
      (maze/passage-opened [4 3] [4 4])
      (maze/passage-opened [4 4] [4 5])
      (maze/passage-opened [4 5] [3 5])
      (maze/passage-opened [3 5] [2 5])
      (maze/passage-opened [2 5] [1 5])
      (maze/passage-opened [1 5] [1 6])
      (maze/passage-opened [1 6] [1 7])
      (maze/passage-opened [4 5] [4 6])
      (maze/passage-opened [4 6] [4 7])
      (maze/passage-opened [4 6] [5 6])
      (maze/passage-opened [5 6] [6 6])
      (maze/passage-opened [6 6] [6 7])
      (maze/passage-opened [6 6] [7 6])
      (maze/passage-opened [7 6] [7 5])
      ))