(ns sound-maze-game.launchpad
  (require
    [clojure.math.combinatorics :as combo]
    [overtone.osc :as tone]
    [sound-maze-game.osc :as osc]))

(def nrows
  "Number of rows on the launchpad."
  9)

(def ncols
  "Number of coluns on the launchpad."
  9)

(defn positions
  "Possible launchpad positions."
  []
  (into #{} (map vec (combo/cartesian-product (range nrows) (range ncols)))))

(defprotocol LaunchpadOutput
  (clear-all [o] "Set all launchpad LED's to blank (buffered).")
  (update-led [o pos color] "Set a launchpad LED (buffered not shown until call to show).")
  (show [o] "Show all buffered LED updates.")
  (brightness [o value] "Set launchpad brightness (0-127).")
  (draw [o spr] "Draw a sprite on the launchpad."))

(defrecord OscLaunchpadOutput
  [cli]
  LaunchpadOutput

  (update-led
    [_ [row col] [red green]]
    (tone/osc-send cli "lpad/led"
                   (Integer. row) (Integer. col)
                   (Integer. red) (Integer. green)))

  (show
    [_]
    (tone/osc-send cli "lpad/show" (Integer. 1)))

  (brightness
    [_ value]
    (tone/osc-send cli "lpad/brightness" (Integer. value)))

  (clear-all
    [this]
    (doseq [led-pos (positions)]
      (update-led this led-pos [0 0])))

  (draw
    [this spr]
    (doseq [led-pos (keys spr)]
      (update-led this led-pos (get spr led-pos)))))

(defn client
  []
  (OscLaunchpadOutput. (osc/client)))
