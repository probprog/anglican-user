# Hacking with Angllican

An Anglican query can be either run as a standalone program, producing
the inference results on the standard output, or invoked programmatically
from Clojure code.

## Running Standalone Programs

Standalone Anglican programs can be run from the command line
or in the REPL. The command line syntax is

    lein run namespace [program] [option ...]

from the command line, or:

    (m! namespace [program] [option ...])

in the REPL, where 'namespace' is the namespace containing the
Anglican program to run, for example:

    bash$ lein run branching -a pgibbs -n 100 \
               -o ":number-of-particles 50"

    anglican.core=> (m! branching -a pgibbs -n 100
                      -o ":number-of-particles 50")
               
'program' is the first argument of `defquery`. The namespace
may contain multiple programs. If 'program' is omitted, it defaults
to the last component of the namespace (hmm for anglican.hmm,
logi for anglican.logi).

### Options:

    -a, --inference-algorithm NAME   :lmh       Inference algorithm
    -b, --burn N                     0          Skip first N samples
    -d, --debug                                 Print debugging information
    -f, --output-format FORMAT       :anglican  Output format
    -n, --number-of-samples N                   Output predicts for N samples
    -o, --algorithm-options OPTIONS  []         Algorithm options
    -t, --thin N                     1          Retain each Nth sample
    -v, --value V                               Initial value to pass to the program
    -w, --warmup FLAG                true       Pre-evaluate the program
    -h, --help                                  Print usage summary and exit

### Redirecting Input and Output

Inference output is normally redirected to a file for post-processing. On the
command line, '>' can be used:

    bash$ lein run branching -a pgibbs -n 100 \
               -o ":number-of-particles 50" > branching.pgibbs

In the REPL, a macro `redir` is provided. The syntax is:

    (redir [:in "input-file-name" :out "output-file-name"]
      actions ...)

Either `:in` or `:out` (or both) can be omitted.
if the output file name begins with '+', '+' is removed
and the output is appended to the file. In the REPL, the above
command example can be run as

    anglican.core=> (redir [:out "branching.pgibbs"]
                      (m! branching -a pgibbs -n 100
                          -o ":number-of-particles 50"))

Functions `freqs` (frequency table for every integer-valued or
symbolic predict) and `meansd` (mean and standard deviation for
each predict) can be used in the REPL to quickly assess the
results:

     anglican.core=> (redir [:in "branching.pgibbs"] (freqs))
     r, 0, 0.0260000, -3.64966
     r, 1, 0.0930000, -2.37516
     r, 2, 0.0740000, -2.60369
     r, 4, 0.00100000, -6.90776
     r, 5, 0.340000, -1.07881
     r, 6, 0.244000, -1.41059
     r, 7, 0.133000, -2.01741
     r, 8, 0.0590000, -2.83022
     r, 9, 0.0170000, -4.07454
     r, 10, 0.00700000, -4.96185
     r, 11, 0.00500000, -5.29832
     r, 12, 0.00100000, -6.90776

## Calling Anglican from Clojure

An Anglican query can also be invoked programmatically from
Clojure code, using the `doquery` macro, with the following
syntax:

    (doquery algorithm query-name value & options)

where `algorithm` is the keyword specifying the algorithm (`:lmh`,
`:pgibbs`, `:pcascade` etc.), `value` is the initial value passed to
the query (`nil` if the query does not expect arguments), and
`options` are Clojure clojure keyword arguments, specific to each
inference algorithm.  `doquery` returns a lazy sequence of samples —
objects encapsulating a map of predicts and the log probability of
each sample:

    (ns analyze-branching
      (:use [anglican
             core 
             [state :only [get-predicts get-log-weight]]])
      (:use branching))

    ;; Lazily invoke the inference.
    (def samples (doquery :pgibbs branching nil :number-of-particles 100)) 

	;; Retrieve predicts from samples.
    (def rs (map #(get % 'r) (map get-predicts samples)))

	;; Use 1000 samples to estimate the mean of r.
    (def r-mean (let [N 1000]
                  (*  (/ N) (reduce + (take N rs)))))
    (println "mean(r) =" r-mean)

Gorilla REPL provides a convenient environment for processing
and visualization of inference results. 'worksheets/template.clj'
is supplied as a starting point for an Anglican worksheet.
