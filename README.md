# Heuristic methods for JobShop scheduling

This repository contains the starter code for the assignment.

## Working in IntelliJ

For working on this project, we recommend using the IntelliJ-IDEA development environment. It is available in INSA's 
classrooms as well as on `montp.insa-toulouse.fr`.

To import the project in IntelliJ (once IntelliJ is running):

 - Open a new project : `Open project` or `File > Open`
 - Select the `gradle.build` file in the cloned repository. 
 - Select `Open as project`.

To run the program in IntelliJ, you can 

 - Right click on the `src/main/java/Jobshop/Main` class in the project view.
 - Select `Run Main.main()`. It should complain that some arguments are missing.
 - Give it the expected command line arguments : `Run > Edit Configuration`, then fill in the `Program arguments` text box.


## Working on the command line (Gradle)

Compilation instructions are given for Linux. On Windows you can use the `gradlew.bat` script (but you are on your own).

```
❯ ./gradlew build    # Compiles the project
```

The project can be executed directly with `gradle` by specifying the arguments like so :

```
❯ ./gradlew run --args="--solver basic random --instance aaa1 ft"
```

You can also build an executable jar file, and run it with the java command.
This is especially useful if you want to run it on another machine.

```
 # Create a jar file with all dependencies in build/libs/JSP.jar
❯ ./gradlew jar     
# Run the jar file. Only requires a Java Runtime Environment (JRE)
❯ java -jar build/libs/JSP.jar --solver basic --instance ft06
```

The command line above indicates that we want to solve the instance named`ft06` with the `basic` solver. It should give an output like the following :
```
                         basic
instance size  best      runtime makespan ecart
ft06     6x6     55            1       60   9.1
AVG      -        -          1.0        -   9.1
```

Fields in the result view are the following :
- `instance`: name of the instance
- `size`: size of the instance `{num-jobs}x{num-tasks}`
- `best`: best known resultfor this instance
- `runtime`: time taken by the solver in milliseconds (rounded)
- `makespan`: makespan of the solution
- `ecart`: normalized distance to the best result: `100 * (makespan - best) / best` 

One can also specify multiple solvers (below `basic` and `random`) and instances (below `ft06`, `ft10` and `ft20`) for simultaneous testing:

```
❯ java -jar build/libs/JSP.jar --solver basic random --instance ft06 ft10 ft20

                         basic                         random
instance size  best      runtime makespan ecart        runtime makespan ecart
ft06     6x6     55            1       60   9.1            999       55   0.0
ft10     10x10  930            0     1319  41.8            999     1209  30.0
ft20     20x5  1165            0     1672  43.5            999     1529  31.2
AVG      -        -          0.3        -  31.5          999.0        -  20.4
```
Here the last line give the average `runtime` and `ecart` for each solver.

```
sage: jsp-solver [-h] [-t TIMEOUT] --solver SOLVER [SOLVER ...]
                  --instance INSTANCE [INSTANCE ...]

Solves jobshop problems.

named arguments:
  -h, --help             show this help message and exit
  -t TIMEOUT, --timeout TIMEOUT
                         Solver  timeout  in  seconds  for  each  instance. 
                         (default: 1)
  --solver SOLVER [SOLVER ...]
                         Solver(s) to use  (space  separated  if  more than
                         one)
  --instance INSTANCE [INSTANCE ...]
                         Instance(s) to  solve  (space  separated  if  more
                         than one). All instances  starting  with the given
                         String will be  selected.  (e.g.  "ft" will select
                         the instances ft06, ft10 and ft20.
```


## IDE Support

Most IDEs should provide support for importing gradle projects. However, our experience has been best with IntelliJ so far and we would recommend it.

#### IntelliJ

IntelliJ has worked best, with out of the box support for the import of gradle projets:
https://www.jetbrains.com/help/idea/gradle.html#gradle_import_project_start


#### Eclipse

Most technical problems have been related to the use of Eclipse so we don't recommend using it unless you have a good reason to.
We have however configured gradle to allow generating an eclipse configuration like so : 
```
./gradlew eclipseClean eclipse
```