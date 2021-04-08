# Solvers

## The `Solver` interface

`jobshop.solvers.Solver` provides a common interface for all solvers.


## Basic solver

A very simple solver that tries to schedule all first tasks, then all second tasks, then all third tasks, ...

It does so using the `JobNumbers` encoding

## Random solver

Another very simple solver based on the `JobNumbers` encoding.
At each iteration, the solver generates a new random solution keeps it if it the best one found so far.

It repeats this process until the deadline to produce a result is met and finally returns the best solution found.


## Greedy solver

The greedy solver is not implemented yet. 
Its constructor accepts a parameter that specifies the priority that should be used to produce solutions.

## Descent Solver


### Neighborhoods

TODO