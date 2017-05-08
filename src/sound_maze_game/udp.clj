(ns sound-maze-game.udp
  (:require [clojure.core.async :as async :refer [go >! <! chan]])
  (:import (java.net DatagramPacket DatagramSocket InetAddress)))

(defn make-socket
  "Make a datagram socket."
	([] (new DatagramSocket))
	([port] (new DatagramSocket port)))

(defn send-data
  "Send data over a socket to a given ip/port combo."
  [send-socket ip port data]
  (let [ipaddress (InetAddress/getByName ip)
        send-packet (new DatagramPacket (.getBytes data) (.length data) ipaddress port)]
    (.send send-socket send-packet)))

(defn receive-data
  "Receive data over a socket."
  [receive-socket]
  (let [receive-data (byte-array 1024),
       receive-packet (new DatagramPacket receive-data 1024)]
    (.receive receive-socket receive-packet)
    (new String (.getData receive-packet) 0 (.getLength receive-packet))))

(defn make-receive-channel
  "Make a channel that receives data over a given port."
  [receive-port]
	(let [receive-socket (make-socket receive-port)
        receive-channel (chan)]
		(go (while true
          (>! receive-channel (receive-data receive-socket))))
    receive-channel))

(defn make-send-channel
  "Make a function that sends data to a given ip/port."
  [ip port]
	(let [send-socket (make-socket)
        send-channel (chan)]
    (go (while true
          (send-data send-socket ip port (<! send-channel))))
    send-channel))
