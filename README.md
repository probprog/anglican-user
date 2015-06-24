# Anglican user project template

This repository provides template for quickly start hacking away
with [Anglican Probabilistic Programming
System](https://bitbucket.org/probprog/anglican).  The repository is
a [Leiningen](http://leiningen.org/) project. Probabilistic queries
can be run either in the Leiningen REPL, or inside the browser using
[Gorilla REPL](http://gorilla-repl.org/).

Checkout and [modify for your needs](https://bitbucket.org/probprog/anglican-user/src/HEAD/doc/intro.md).
 `programs` is intended for
standalone programs run in REPL or from the command line,
`worksheets` is for Gorilla REPL worksheets. Take a look a the
[Introduction to
Anglican](https://bitbucket.org/probprog/anglican/src/HEAD/code/doc/intro.md)
if you are new to Anglican.  Anglican is built on top of Clojure and
syntactically supports a subset of Clojure. This [Clojure tutorial](http://clojure-doc.org/articles/tutorials/introduction.html)
is a sufficient introduction to the Clojure language for Anglican
programmers.

The easiest way to start a new program is to copy
`programs/template.clj` (for a standalone program) or
`worksheets/template.clj` (for Gorilla worksheets) to a file with a
different name in the same directory and start modifying it.
Advanced programming patterns and techniques can be found in the
collection of [anglican examples](https://bitbucket.org/probprog/anglican-examples).