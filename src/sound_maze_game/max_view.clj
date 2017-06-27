(ns sound-maze-game.max-view
  (:require [sound-maze-game.maze :as maze]))

(defn binaural-staging
  "Coordinates for a listener and an object on the binaural stage
   for a listener and an object based on their positions."
  [listener-pos object-pos]
  (let [[odx ody] (maze/sub-vec listener-pos object-pos)
        listener-bin-pos [0.5 0.5]
        object-bin-pos (maze/sub-vec listener-bin-pos
                                     [(/ ody 5.0) (/ odx 5.0)])]
    (hash-map :listener-pos listener-bin-pos
              :object-pos object-bin-pos)))

(defn view
  "View of data for max/msp UI."
  [game-state]
  (let [{:keys [player exit]} game-state
        mz (get game-state :maze)]
    (hash-map
     :available-dirs (mapv maze/dir-named-vecs
                           (maze/passage-dirs mz (:pos player)))
     :player-pos (:pos player)
     :exit-staging (binaural-staging (:pos player) (:pos exit))
     )))
