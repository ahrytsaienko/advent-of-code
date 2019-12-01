(ns advent-of-code-2019.day1
  (:require [clojure.java.io :as io]))

(def input (->> "day1/input"
                io/resource
                io/reader
                line-seq
                (map #(Long/parseLong %))))

(defn count-fuel [mass]
  (max
   (- (quot mass 3) 2)
   0))

(defn count-fuel-independently [mass]
  (loop [m mass
         total []]
    (if (pos? m)
      (let [x (count-fuel m)]
        (recur x (conj total x)))
      (apply + total))))

(def solution1-1
  (apply + (map count-fuel input)))

(def solution2-1
  (apply + (map count-fuel-independently input)))

;; alternatives

(defn fuel* [mass]
  (->> (iterate count-fuel mass)
       (drop 1)
       (take-while pos?)
       (apply +)))

(def solution2-2
  (apply + (map fuel* input)))

(defn fuel** [mass]
  (let [m (count-fuel mass)]
    (lazy-seq (when (pos? m)
                (cons m (fuel** m))))))

(def solution2-3
  (->> (mapcat fuel** input)
       (apply +)))


