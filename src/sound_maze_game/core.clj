(ns sound-maze-game.core
  (:require [sound-maze-game.loop :as loop]
            [sound-maze-game.maxmsp :as maxmsp]
            [sound-maze-game.demo-mazes :as demo-mazes]
            [sound-maze-game.game :as game]))

(comment
  (def max-send-chan (maxmsp/make-send-channel 7818))
  (def max-recv-chan (maxmsp/make-receive-channel 7814))
  (def start-state (game/start-game-state
                    demo-mazes/mvi1-maze
                    (game/player [7 5])
                    (game/exit [0 0])))

  (loop/start-game-loop start-state
                        game/action-reduced
                        max-recv-chan max-send-chan)

  (>!! max-recv-chan "quit!")


  )
