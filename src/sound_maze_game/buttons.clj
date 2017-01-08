(ns sound-maze-game.buttons)

(use 'overtone.osc)

(defn handle-presses [srv callback]
  "Register a call back to receive all button events.
   Events are of the form (<row> <col> <val>), where
   <val> is 1 for a press, 0 for a release."
  (osc-handle srv "/btn"
              (fn [msg] (callback (:args msg)))))

(defn press?
  "Is a button event a down press?"
  [[_ _ down]]
  (= down 1))

(defn release?
  "Is a button event a release?"
  [[_ _ down]]
  (= down 0))
