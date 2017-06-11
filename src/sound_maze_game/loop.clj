(ns sound-maze-game.loop
  (:require [clojure.core.async :as async :refer [go-loop chan <! >! >!!]]
            [clojure.string :as string :refer [trim]]
            [sound-maze-game.maze :as maze]
            [sound-maze-game.maxmsp :as maxmsp]
            [sound-maze-game.max-view :as max-view]))

(def game-state-history (atom []))

(defn handle-action!
  "Handles an action, updating the game state history.
   Returns the updated game state."
  [game-state action reducer]
  (let [new-state (reducer game-state action)]
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
  [start-state reducer from-max-chan to-max-chan]
  (>!! to-max-chan (maxmsp/max-serialized (max-view/view start-state)))
  (go-loop [state start-state]
    (let [action-data (trim (<! from-max-chan))]
      (if (not (= action-data "quit!"))
        (if-let [action (maxmsp/max-deserialized action-data)]
          (let [new-state (handle-action! state action reducer)]
            (>! to-max-chan
                (maxmsp/max-serialized (max-view/view new-state)))
            (recur new-state))
          (recur (cant-handle-action-data! state action-data)))))))
