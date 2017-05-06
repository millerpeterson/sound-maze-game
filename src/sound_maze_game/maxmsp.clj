(ns sound-maze-game.maxmsp
  (:require [clojure.core.async :as async :refer [go >!! <!! chan]]
            [sound-maze-game.udp :as udp]
            [clojure.data.json :as json]))

(defn make-maxmsp-receive-channel
  "Channel for receiving messages from max/msp."
  [port]
  (udp/make-receive-channel port))

(defn make-maxmsp-send-channel
  "Channel for sending messages to max/msp."
  [port]
  (udp/make-send-channel "localhost" port))

(defn send-to-max
  "Send a message on a max/msp channel."
  [channel msg]
  (>!! channel (json/write-str msg)))

(defn next-max-msg
  "Receive a message from a max/msp channel."
  [channel]
  (json/read-str (<!! channel)))
