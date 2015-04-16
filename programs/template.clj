(ns template
  (:use [anglican emit runtime]))

(defquery template
  "query template"
  (let [x (sample (bernoulli 0.5))]
    (observe (normal x 1.) 0.75)
    (predict x)))
