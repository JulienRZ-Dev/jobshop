package jobshop;

import jobshop.encodings.JobNumbers;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Schedule;
import jobshop.encodings.Task;
import jobshop.solvers.GreedySolver;

import java.io.IOException;
import java.nio.file.Paths;

/** A java main classes for testing purposes. */
public class MainTest {

    public static void main(String[] args) {
        try {
            // load the aaa1 instance
            Instance instance = Instance.fromFile(Paths.get("instances/aaa3"));

            /*
            // builds a solution in the job-numbers encoding [0 0 1 1 0 1]
            JobNumbers enc = new JobNumbers(instance);
            enc.addTaskOfJob(0);
            enc.addTaskOfJob(0);
            enc.addTaskOfJob(1);
            enc.addTaskOfJob(1);
            enc.addTaskOfJob(0);
            enc.addTaskOfJob(1);

            System.out.println("\nENCODING: " + enc);

            // convert to a schedule and display
            Schedule schedule = enc.toSchedule().get();
            System.out.println("VALID: " + schedule.isValid());
            System.out.println("MAKESPAN: " + schedule.makespan());
            System.out.println("SCHEDULE: " + schedule.toString());
            System.out.println("GANTT: " + schedule.asciiGantt());
            */


            GreedySolver solver = new GreedySolver(GreedySolver.Priority.EST_LRPT);

            Result result = solver.solve(instance, 1000);

            System.out.println("Solution found by the greedy solver :");
            System.out.println("MAKESPAN: " + result.schedule.get().makespan());
            System.out.println(result.schedule.get().asciiGantt());

            /*
            // MANUAL SCHEDULE

            Schedule manualSchedule = new Schedule(instance);

            manualSchedule.setStartTime(0, 0, 0);
            manualSchedule.setStartTime(0, 1, 3);
            manualSchedule.setStartTime(0, 2, 6);
            manualSchedule.setStartTime(1, 0, 6);
            manualSchedule.setStartTime(1, 1, 8);
            manualSchedule.setStartTime(1, 2, 10);

            System.out.println("*****MANUAL SCHEDULE*****");
            System.out.println(manualSchedule.asciiGantt());


            // MANUAL RESOURCE ODER

            ResourceOrder manualRO = new ResourceOrder(instance);

            manualRO.addTaskToMachine(0, new Task(0, 0));
            manualRO.addTaskToMachine(0, new Task(1, 1));
            manualRO.addTaskToMachine(1, new Task(0, 1));
            manualRO.addTaskToMachine(1, new Task(1, 0));
            manualRO.addTaskToMachine(2, new Task(0, 2));
            manualRO.addTaskToMachine(2, new Task(1, 2));

            System.out.println("*****MANUAL RO*****");
            System.out.println(manualRO.toSchedule().get().asciiGantt());


            ResourceOrder optimalRO = new ResourceOrder(instance);

            optimalRO.addTaskToMachine(0, new Task(0, 0));
            optimalRO.addTaskToMachine(0, new Task(1, 1));
            optimalRO.addTaskToMachine(1, new Task(1, 0));
            optimalRO.addTaskToMachine(1, new Task(0, 1));
            optimalRO.addTaskToMachine(2, new Task(1, 2));
            optimalRO.addTaskToMachine(2, new Task(0, 2));


            System.out.println("*****OPTIMAL RO*****");
            System.out.println(optimalRO.toSchedule().get().asciiGantt());
            /*
            ResourceOrder errorRO = new ResourceOrder(instance);

            errorRO.addTaskToMachine(0, new Task(1, 1));
            errorRO.addTaskToMachine(0, new Task(0, 0));
            errorRO.addTaskToMachine(1, new Task(0, 1));
            errorRO.addTaskToMachine(1, new Task(1, 0));
            errorRO.addTaskToMachine(2, new Task(1, 2));
            errorRO.addTaskToMachine(2, new Task(0, 2));


            System.out.println("*****ERROR RO*****");
            System.out.println(errorRO.toSchedule().get().asciiGantt());
             */

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
