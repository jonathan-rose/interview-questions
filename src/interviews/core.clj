(ns interviews.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn reverse-string
  "Reverse a given string."
  [string]
  (if (string? string)
    (-> string
        (seq)
        (reverse)
        (str/join))
    (println "Invalid input. Expected a string.")))

(def test-sequence [1, 2, 5, 66, 190, 45])

(defn largest-element
  "Finds the largest element in a given sequence of integers."
  [sequence]
  (if (and (sequential? sequence) (every? integer? sequence))
    (apply max sequence)
    (println "Input is not sequential or contains non-integer elements. Aborting.")))

(defn check-palindrome
  "Checks if a given string is a palindrome. Converts string to lowercase before evaluation."
  [string]
  (let [string (str/lower-case string)
        string-reversed (reverse-string string)]
    (if (= string string-reversed)
      (println "This string is a lowercase palindrome.")
      (println "This string is not a palindrome."))))

(def target 71)

;; How could find-sum be improved to ignore flipped pairs?
;; I.e, if x + y sums to target, it should not evaluate y + x.
(defn find-sum
  "Given a sequence of integers and a target sum, finds the index of two elements that sum to the target."
  [sequence target]
  (if (and (sequential? sequence) (every? integer? sequence))
    (doseq [e1 sequence e2 (remove #{e1} sequence)]
      (do (println (str "The sum of " e1 " and " e2 " is: " (+ e1 e2)))
          (if (= (+ e1 e2) target)
            (do (println (str "WINNER: " e1 " and " e2 " add up to " target))
                (println (str "Index of " e1 " is " (.indexOf sequence e1)))
                (println (str "Index of " e2 " is " (.indexOf sequence e2)))))))
    (println "Input is not sequential or contains non-integer elements. Aborting")))

;; How can this be improved so the order of precedence is irrelevant?
(defn fizz-buzz
  "Prints numbers in num range. Multiples of 3, prints Fizz. Multiples of 5, Buzz. For both, FizzBuzz."
  [num]
  (if (integer? num)
    (let [nums (vec (range 1 (inc num)))]
      (doseq [e1 nums]
        (cond
          (and (zero? (mod e1 3)) (zero? (mod e1 5))) (println "FizzBuzz")
          (zero? (mod e1 3)) (println "Fizz")
          (zero? (mod e1 5)) (println "Buzz")
          :else (println e1))))
    (println "Input is not an integer. Aborting.")))
