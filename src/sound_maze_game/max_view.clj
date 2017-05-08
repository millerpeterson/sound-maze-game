(ns sound-maze-game.max-view
  (:require [sound-maze-game.maze :as maze]))

(defn view
  "View of data for max/msp UI."
  [game-state]
  (hash-map :available-dirs [:left :down]))

