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

 The `NumJobs` encoding consists of a sequence of job numbers. To produce a schedule, one should iterate on the job numbers and tries to schedule *as early as possible* the next task of the job.

 For instance the encoding `[0 0 1 1 0 1]` will produce a schedule by trying to place as early as possible the following tasks (in this order):

 - `(0, 0)`: first task of the first job
 - `(0, 1)`: second task of the first job
 - `(1, 0)`: first task of the second job
 - `(1, 1)`: second task of the second job
 - `(0, 2)`: third task of the first job
 - `(1, 2)`: third task of the second job


 ## ResourceOrder

The resource order encoding specifies the order in which each machine will process its tasks.

Each machine is associated with an array of task that specifies the order on which the task must be scheduled on the machine.

For instance, the encoding:

 ```
 machine 0: [(0, 1), (1, 0)]
 machine 1: [(0, 0), (1, 1)]
 machine 2: [(1, 2), (0, 2)]
 ```

 Specifies that the first machine (machine 0) will first process the second task of the first job `(0, 1)` and only when it is finished can start processing the first task of the second job `(1, 0)`.