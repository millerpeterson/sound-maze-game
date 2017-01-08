(ns sprite-assembly.op
  (require [sprite-assembly.sprite :as sprite]
           [sprite-assembly.colors :as led]))

(defn region-map
  "Map leds in a given region of a sprite."
  ([spr func & {:keys [region]
                :or {region sprite/positions}}]
    (reduce #(assoc %1 %2 (func %2 (get %1 %2)))
      spr region)))

(defn set-led
  "Set an individual led."
  ([spr r c colr]
    (region-map spr (fn [_ _] (vec colr))
                :region [[r c]])))

(defn fill
  "Fill a region with a specific color."
  ([spr color & {:keys [region]
                 :or {region sprite/positions}}]
    (region-map spr (fn [_ _] (vec color))
                :region region)))

