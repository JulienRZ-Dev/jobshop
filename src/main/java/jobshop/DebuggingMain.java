package jobshop;

import jobshop.encodings.JobNumbers;
import jobshop.encodings.Schedule;
import jobshop.solvers.GreedySolver;

import java.io.IOException;
import java.nio.file.Paths;

public class DebuggingMain {

    public static void main(String[] args) {
        try {
            // load the aaa1 instance
            Instance instance = Instance.fromFile(Paths.get("instances/aaa1"));

            // builds a solution in the job-numbers encoding [0 1 1 0 0 1]
            JobNumbers enc = new JobNumbers(instance);
            enc.addTask(0);
            enc.addTask(1);
            enc.addTask(1);
            enc.addTask(0);
            enc.addTask(0);
            enc.addTask(1);

            System.out.println("\nENCODING: " + enc);

            Schedule schedule = enc.toSchedule();
            System.out.println("VALID: " + schedule.isValid());
            System.out.println("MAKESPAN: " + schedule.makespan());
            System.out.println("SCHEDULE: " + schedule.toString());
            System.out.println("GANTT: " + schedule.asciiGantt());

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
