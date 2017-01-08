(ns sound-maze-game.osc)

(use 'overtone.osc)

(def ports {:send 1337
            :recv 7331})

(defn client
  ([host port]
    (osc-client host port))
  ([]
    (osc-client "localhost" (:send ports))))

(defn server
  ([port]
    (osc-server port))
  ([]
    (osc-server (:recv ports))))
