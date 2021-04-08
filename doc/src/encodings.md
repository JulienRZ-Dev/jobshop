# Encodings

Several encoding and associated utilities are provided in the `jobshop.encodings` package.
The abstract class `Encoding` provides a common interface for all encodings.

## Schedule

The `Schedule` has direct encoding of a solution: it associates every task in the jobshop to a start time.

It plays a particular role as it is the standard way of representing a solution. As a consequence, all other encodings must provide a way to produce a schedule.

Convenience methods:

 - `isValid()`: returns true if the schedule is valid (no violated constraints).
 - `makespan()`: computes the makespan of the solution.
 - `criticalPath()`: returns a critical path in the solution.
 - `asciiGantt()`: generates a Gantt chart view of the solution in ASCII art.


 ## NumJobs



 ## ResourceOrder