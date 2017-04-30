(ns sound-maze-game.loop
  (require [sound-maze-game.maze :as maze]
           [sound-maze-game.osc :as osc]))

(def osc-server (osc/server))
(def osc-cli (osc/client))
(def game-state-history (atom []))

(defn start-handling-osc-events!
  [server]
  (osc/handle! server "/maze-game-events" (fn [msg] (println msg))))

;; (defn notify-as-json!
;;   [cli message]
;;   (osc/send-as-json! cli message))

;; (defn player
;;   "A game world player."
;;   [pos]
;;   (hash-map :pos pos
;;             :movement-intentionmedi nil))

;; (defn start-game-state
;;   "Starting state for a game with a given maze and player."
;;   [maze player]
;;   (hash-map :maze maze
;;             :player player
;;             :turn 0))

;; (defn update-game-state!
;;   [action new-state]
;;   (swap game-state-history #(conj % {:action action
;;                                      :state state})))

;; (defn handle-action
;;   "Return the state after handling a game action."
;;   [game-state action]
;;   game-state)

;; (defn tick!
;;   "Advance one step in the simulation."
;;   [game-state]
;;   game-state)

;; (defn start-game!
;;   [maze player]
;;   (let [start-state (start-game-state maze player)
;;     (osc-handle )))

