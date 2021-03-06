(ns metasolfeggio.shakers_leipzig
  (:use
    leipzig.melody
    leipzig.scale
    leipzig.canon
    leipzig.live)
  (:require [overtone.live :as overtone]
            [overtone.synth.stringed :as strings]))

(strings/gen-stringed-synth ektara 1 true)

(defn pick [distort amp {midi :pitch, start :time, length :duration}]
    (let [synth-id (overtone/at start
                     (ektara midi :distort distort :amp amp :gate 1))]
      (overtone/at (+ start length) (overtone/ctl synth-id :gate 0))))

(defmethod play-note :leader [note]
  (pick 0.7 1.0 note))

(def melody
       ;;;  it is a gift to be simple
  (->> (phrase [ 1   1   1 1/2 1/2 1/2 1/2]
               [-3  -3   0   0   1   2   0])
       ;;; it's a  gift to be free
       (then (phrase [1/2 1/2   1 1/2 1/2   1]
                     [  2   3   4   4   4   2]))
       ;;; it's a gift to come round where we ought to be
       (then (phrase [1/2 1/2   1   1   1   1 1/2 1/2 1/2 1/2   1]
                     [  1   0   1   1   1   1   1   2   1  -1  -3]))
       ;;; and when we find ourselves in the place just right
       (then (phrase [  1 1/2 1/2 1/2 1/2   1 1/2 1/2   1   1]
                     [ -3   0  -1   0   1   2   1   1   2   3]))
       ;;; it will be in the valley of love and delight
       (then (phrase [3/2 1/2   1 1/2 1/2   1 1/2 1/2   1 1/2 1/2  2]
                     [  4   4   1   1   2   1   0   0   1   0  -1  0]))
       ;;; when true simplicity is gained
       (then (phrase [  2 3/2 1/2 1/2 1/2 1/2 1/2 3/2]
                     [  4   2   1   2   3   2   1   0]))
       ;;; to bow and to bend we shan't be ashamed
       (then (phrase [1/2   1 1/2 1/2   1   1   1 1/2 1/2 3/2]
                     [  1   2   2   3   4   2   1   1   2   1]))
       ;;; to turn, turn will be our delight
       (then (phrase [1/2   2 3/2 1/2   1 1/2 1/2   1]
                     [ -3   0   0   1   2   2   3   4]))
       ;;; till by turning, turning we come round right
       (then (phrase [1/2 1/2   1   1   1 1/2 1/2   1   1   1]
                     [  4   4   1   1   2   2   1   0   0   0]))
    (where :part (is :leader))))

(defn shakers [speed key]
  (->> melody
    (where :time speed)
    (where :duration speed)
    (where :pitch key)
    play))

(comment
  (shakers (bpm 120) (comp C major))
  (stop))
