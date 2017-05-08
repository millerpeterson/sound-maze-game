(ns sound-maze-game.loop
  (:require [clojure.core.async :as async :refer [go-loop chan <!]]
            [clojure.string :as string :refer [trim]]
            [sound-maze-game.maze :as maze]
            [sound-maze-game.demo-mazes :as demo-mazes]
            [sound-maze-game.maxmsp :as maxmsp]))

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

(defn state-after-action
  "Return the state after processing a game action."
  [game-state action]
  game-state)

(defn handle-action!
  "Handles an action, updating the game state history.
   Returns the updated game state."
  [game-state action-data]
  (let [action (maxmsp/max-deserialized action-data)
        new-state (state-after-action game-state action)]
    (println "handle action" action new-state)
    (swap! game-state-history #(conj % [action, new-state]))
    new-state))

(defn start-game-loop
  "Start a game loop running. Returns a channel for telling the
   loop to stop."
  [start-state from-max-chan]
  (go-loop [state start-state]
    (let [action-data (trim (<! from-max-chan))]
      (println "#" action-data "#")
      (if (not (= action-data "quit!"))
        (let [new-state (handle-action! state action-data)]
          (recur new-state))))))

(comment
  (def max-send-chan (maxmsp/make-send-channel 7605))
  (def max-recv-chan (maxmsp/make-receive-channel 7607))
  (def start-state (start-game-state demo-mazes/mvi1-maze (player [0 0])))
  (start-game-loop start-state max-recv-chan)
)
