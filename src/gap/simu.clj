(ns gap.simu
    "data simulation for coloring"
    (:use anglican.runtime))

(def ip-prefixes ["192.168.1.", "192.168.3.",
                  "172.16.3.", "172.16.11.",
                  "10.0.0.", "10.0.1.", "10.1.1."])

(defn ips "IP addresses for transactions"
  [n]
  (repeatedly n #(format "%s%d"
                         (rand-nth ip-prefixes)
                         (+ 1 (rand-int 252)))))

(defn ip-groups
  "IP groups"
  [n p ips]
  (filter seq (repeatedly n #(random-sample p ips))))

(defn amount
  "Transaction amount"
  ([] (amount 50.0))
  ([average]
   (sample (exponential (/ 1. average)))))

(defn interval
  "Transaction interval"
  ([] (interval 10000.0))
  ([average]
   (sample (exponential (/ 1. average)))))

(defrecord user [group amount interval])

(defn simulate 
  "simulate sequence"
  [n groups p users]
  (println groups)
  (sort-by
    second
    (loop [i 0
           session []
           last-times [1433478819 1435026406]]
          (if (< i n)
            (let []
              (let [u (sample (bernoulli p))
                    user (users u)
                    ip (rand-nth (nth groups (:group user)))
                    amount (amount (:amount user))
                    interval (interval (:interval user))
                    time (+ (last-times u) interval)]
                (recur (inc i)
                       (conj session [ip
                                      (round time)
                                      (/ (round (* 100.0 amount)) 100.0)])
                       (assoc last-times u time))))
            session))))
