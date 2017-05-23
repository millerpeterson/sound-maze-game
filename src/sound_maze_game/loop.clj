(ns sound-maze-game.loop
  (:require [clojure.core.async :as async :refer [go-loop chan <! >!]]
            [clojure.string :as string :refer [trim]]
            [sound-maze-game.maze :as maze]
            [sound-maze-game.demo-mazes :as demo-mazes]
            [sound-maze-game.maxmsp :as maxmsp]
            [sound-maze-game.max-view :as max-view]))

(def game-state-history (atom []))

(defn player
  "A game world player."
  [pos]
  (hash-map :pos pos
            :movement-intention nil))

(defn start-game-state
  "Starting state for a game with a given maze and player."
  [maze player]
  (hash-map :maze maze
            :player player
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

(defn state-after-action
  "Return the state after processing a game action."
  [game-state action]
  (let [action-type (get action "type")
        action-payload (get action "payload")]
    (case action-type
      "movement-intention" (assoc-in game-state
                                     [:player :movement-intention]
                                     (keyword action-payload))
      "set-player-pos" (assoc-in game-state
                              [:player :pos]
                              action-payload)
      "next-turn" (next-turn game-state)
      game-state)))

(defn handle-action!
  "Handles an action, updating the game state history.
   Returns the updated game state."
  [game-state action]
  (let [new-state (state-after-action game-state action)]
    (println action "->" (:player new-state) (:turn new-state))
    (swap! game-state-history #(conj % [action, new-state]))
    new-state))

(defn cant-handle-action-data!
  "Logs that an action cannot be handled."
  [game-state action-data]
  (println "Can't parse data from Max:" action-data)
  game-state)

(defn start-game-loop
  "Start a game loop running."
  [start-state from-max-chan to-max-chan]
  (go-loop [state start-state]
    (let [action-data (trim (<! from-max-chan))]
      (if (not (= action-data "quit!"))
        (if-let [action (maxmsp/max-deserialized action-data)]
          (let [new-state (handle-action! state action)]
            (>! to-max-chan
                (maxmsp/max-serialized (max-view/view new-state)))
            (recur new-state))
          (recur (cant-handle-action-data! state action-data)))))))

(comment
  (def max-send-chan (maxmsp/make-send-channel 7607))
  (def max-recv-chan (maxmsp/make-receive-channel 7608))
  (def start-state (start-game-state demo-mazes/mvi1-maze (player [0 0])))
  (start-game-loop start-state max-recv-chan max-send-chan)
  )
