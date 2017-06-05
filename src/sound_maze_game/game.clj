(ns sound-maze-game.game
  (:require [sound-maze-game.maze :as maze]))

(defn player
  "A game world player."
  [pos]
  (hash-map :pos pos
            :movement-intention nil))

(defn exit
  "The maze's exit."
  [pos]
  (hash-map :pos pos))

(defn start-game-state
  "Starting state for a game with a given maze and player."
  [maze player exit]
  (hash-map :maze maze
            :player player
            :exit exit
            :turn 0))

(defn move-intention-realized
  "The player after any intention to move is changed to their
   actual position."
  [player]
  (println player)
  (if-let [intention (:movement-intention player)]
    (-> player
        (update :pos #(maze/add-vec % (maze/named-dir-vecs intention)))
        (assoc :movement-intention nil))
    player))

(defn next-turn
  "Advance the game simulation by one turn."
  [game-state]
  (-> game-state
      (update :player move-intention-realized)
      (update :turn inc)))

(defmulti action-reduced
  "Return the state after having applied an action."
  (fn [game-state action]
    (:type action)))

(defmethod action-reduced "movement-intention"
  [game-state action]
  (assoc-in game-state
            [:player :movement-intention]
            (keyword (get action :payload))))

(defmethod action-reduced "set-player-pos"
  [game-state action]
  (assoc-in game-state
            [:player :pos]
            (get action :payload)))

(defmethod action-reduced "next-turn"
  [game-state _]
  (next-turn game-state))
