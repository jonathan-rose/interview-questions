(ns interviews.core
  (:require [clojure.string :as str])
  (:gen-class))

;; ok, first off, I love this, great to do, super impressed

;; You code works, these functions do what they're emant to and for
;; the most part I would happily let them into any professional
;; codebase I'm working on.

;; you're doing a lot of (if (condition) ... ) at the start of your
;; functions to catch bad inputs, I would say that's a little out of
;; the ordinary for clojure code, generally we assume the things
;; coming in are good without being too strict on what it could
;; be. This lest us leverage the functions that work on a bunt of
;; different data types.

;; what you can do, if you still want these assertions is to add
;; `:pre` asserts to your function definition. But honestly you don';t
;; even see these in the wild that often.

(defn find-max
  [col]
  {:pre [(every? number? col)]}
  (apply max col))

(defn reverse-string
  [s]
  {:pre [(string? s)]}
  (-> s
      reverse
      str/join))

;; for the fizz-buzz example (where they specifically ask for
;; printing) it makes sense to use a `doseq`, but honestly if I for
;; some reasona actually wnated a fizz-buz function I would want it to
;; return a collection of values

(fizz-buzz- 15)
;; => [1
;;     2
;;     "Fizz"
;;     4
;;     "Buzz"
;;     "Fizz"
;;     7
;;     8
;;     "Fizz"
;;     "Buzz"
;;     11
;;     "Fizz"
;;     13
;;     14
;;     "FizzBuzz"]

;; or better yet, return a lazy sequence of all the values which I can
;; take the first n from
(take 15 (fizz-buzz))
;; => [1 2 etc. ]

;; finally I would say that your code is a little imperative (all the
;; `do`s and `doseq`s) and all the printing doesn't lend itself to
;; using these functions elsewhere (I know that's not the point of
;; them here). In general you would see functinos that just return the
;; value or `nil` if that value can't be computed.

(find-sum [1 2 3 4 5 6] 10)
;; => [4 6]

(find-sum [1 2 3] 1000)
;; => nil

;; Strings are already sequences, so you dont need to thread it through `(seq)`.
;; Also, when you thread through an expression that ahas no other arguments you 
;; can omit the parens, improves clarity a little.
(defn reverse-string
  "Reverse a given string."
  [string]
  (if (string? string)
    (-> string
        reverse
        str/join)
    (println "Invalid input. Expected a string.")))

(def test-sequence [1, 2, 5, 66, 190, 45])

(defn largest-element
  "Finds the largest element in a given sequence of integers."
  [sequence]
  ;; I would have used `(seq? sequence)`, but that's because I've never seen `sequential?` before 😅.
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

;; This expression returns all the unique pairs of numbers.
;; We're only caring when x is less than y (which also gets rid of the x = y).
(for [e1 [1 2 3]
      e2 [1 2 3]
      :when (< e1 e2)]
  [e1 e2])

;; we can use that list of pairs as the input to your `doseq`
(defn alternate-find-sum
  "Given a sequence of integers and a target sum, finds the index of two elements that sum to the target."
  [sequence target]
  (if (and (sequential? sequence) (every? integer? sequence))
    (let [pairs (for [e1 sequence
                      e2 sequence
                      :when (< e1 e2)]
                  [e1 e2])]
      (doseq [[e1 e2] pairs]
        (do (println (str "The sum of " e1 " and " e2 " is: " (+ e1 e2)))
            (if (= (+ e1 e2) target)
              (do (println (str "WINNER: " e1 " and " e2 " add up to " target))
                  (println (str "Index of " e1 " is " (.indexOf sequence e1)))
                  (println (str "Index of " e2 " is " (.indexOf sequence e2))))))))
    (println "Input is not sequential or contains non-integer elements. Aborting")))

#_(defn find-sum
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
    ;; doesn't need to be a vector, a list is fine
    (let [nums (range 1 (inc num))]
      (doseq [e1 nums]
        ;; honestly this is great, perfect use of `cond`, and well done for using the `zero?` predicate instaed of a #(= 0 %) lambda or something 👍️
        (cond
          (and (zero? (mod e1 3)) (zero? (mod e1 5))) (println "FizzBuzz")
          (zero? (mod e1 3)) (println "Fizz")
          (zero? (mod e1 5)) (println "Buzz")
          :else (println e1))))
    (println "Input is not an integer. Aborting.")))




