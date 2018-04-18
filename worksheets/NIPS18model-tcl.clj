;; gorilla-repl.fileformat = 1

;; **
;;; ## NIPS'18 submission benchmark 2 - Model for Thermosttically Controlled Loads
;;; 
;;; This is an example taken from Soudjani et al.'s QEST'17 paper. We think that it is a well-known toy example. The example is originally specified in terms of a stochastic differential equation, and we obtain our model by descritizing this equation using Euler scheme. We also modify the example slightly so that we can do some interesting posterior inference. Some people might object this last part.
;; **

;; @@
(use 'nstools.ns)
(ns+ nips18model-tcl
  (:like anglican-user.worksheet))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[nil,nil]"}
;; <=

;; **
;;; Here are the parameters used in the model
;; **

;; @@
(def theta-s 20.0)
(def theta-a 32.0)
(def delta-d 0.5)
(def P-rate 14.0) 
(def R 1.5)
(def C 10.0)
(def sigma0 0.2)
(def sigma1 0.22)

(def theta-lower (- theta-s (/ delta-d 2.0)))
(def theta-upper (+ theta-s (/ delta-d 2.0)))
;; @@

;; **
;;; We now generate a data and define a model.
;; **

;; @@
(defm init-state [] [0 20])

(defm next-q [q theta]
  (cond 
    (<= theta theta-lower) 0
    (>= theta theta-upper) 1
    :else q))

(defm next-theta [q theta]
  (let [b       (* (/ 1 (* C R))
                   (+ theta-a (- (* q R P-rate) (- theta))))
        sigma   (if (= q 0) sigma0 sigma1)
        noise   (sample (normal 0 1))
        q-n     (next-q q theta)
        theta-n (+ b (* sigma noise))]
    [q-n theta-n]))

(defm next-state [state]
  (let [[q theta] state
        q-n       (next-q q theta)
        theta-n   (next-theta q theta)]
    [q-n theta-n]))

(defquery model [data]
  (let [f      (fn [states d] 
                 (let [state-n (first states)]
                   (observe (normal (second state-n) 1) d)))
        states (reduce f (init-state) data)]
    (reverse states)))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/alpha</span>","value":"#'message-example/alpha"},{"type":"html","content":"<span class='clj-var'>#&#x27;message-example/model</span>","value":"#'message-example/model"}],"value":"[#'message-example/alpha,#'message-example/model]"}
;; <=
