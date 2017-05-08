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
  [game-state action]
  (let [new-state (state-after-action game-state action)]
    (println "handle action" action)
    (swap! game-state-history #(conj % [action, new-state]))
    new-state))

(defn cant-handle-action-data!
  "Logs that an action cannot be handled"
  [game-state action-data]
  (println "Can't parse data from Max:" action-data)
  game-state)

(defn start-game-loop
  "Start a game loop running. Returns a channel for telling the
   loop to stop."
  [start-state from-max-chan]
  (go-loop [state start-state]
    (let [action-data (trim (<! from-max-chan))]
      (if (not (= action-data "quit!"))
        (if-let [action (maxmsp/max-deserialized action-data)]
          (recur (handle-action! state action))
          (recur (cant-handle-action-data! state action-data)))))))

(comment
  (def max-send-chan (maxmsp/make-send-channel 7605))
  (def max-recv-chan (maxmsp/make-receive-channel 7606))
  (def start-state (start-game-state demo-mazes/mvi1-maze (player [0 0])))
  (start-game-loop start-state max-recv-chan)
)
