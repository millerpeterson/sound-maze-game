(ns sound-maze-game.max-view
  (:require [sound-maze-game.maze :as maze]))

(defn view
  "View of data for max/msp UI."
  [game-state]
  (let [mz (:maze game-state)
        player (:player game-state)]
    (hash-map
     :available-dirs (mapv maze/dir-named-vecs (maze/passage-dirs mz (:pos player)))
     :player-pos (:pos player))))
