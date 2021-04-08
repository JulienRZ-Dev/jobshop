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


## Documentation

Documentation for this project is available here : [https://insa-4ir-meta-heuristiques.github.io/doc/](https://insa-4ir-meta-heuristiques.github.io/doc/)

