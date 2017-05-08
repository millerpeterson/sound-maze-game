(ns sound-maze-game.maxmsp
  (:require [clojure.core.async :as async :refer [go >!! <!! chan]]
            [sound-maze-game.udp :as udp]
            [clojure.data.json :as json]))

(defn make-receive-channel
  "Channel for receiving messages from max/msp."
  [port]
  (udp/make-receive-channel port))

(defn make-send-channel
  "Channel for sending messages to max/msp."
  [port]
  (udp/make-send-channel "localhost" port))

(defn max-serialized
  "Send a message on a max/msp channel."
  [msg]
  (json/write-str msg))

(defn max-deserialized
  "Receive a message from a max/msp channel."
  [msg]
  (json/read-str msg))
