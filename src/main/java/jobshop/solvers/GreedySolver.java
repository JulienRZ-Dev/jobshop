package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Task;

import java.util.ArrayList;

/** An empty shell to implement a greedy solver. */
public class GreedySolver implements Solver {

    /** All possible priorities for the greedy solver. */
    public enum Priority {
        SPT, LPT, SRPT, LRPT, EST_SPT, EST_LPT, EST_SRPT, EST_LRPT
    }

    /** Priority that the solver should use. */
    final Priority priority;

    /** Creates a new greedy solver that will use the given priority. */
    public GreedySolver(Priority p) {
        this.priority = p;
    }

    // Handle start times memory for EST
    private ArrayList<Integer> jobsDispoTimes = new ArrayList<Integer>();
    private ArrayList<Integer> machinesDispoTimes =  new ArrayList<Integer>();


    @Override
    public Result solve(Instance instance, long deadline) {

        ResourceOrder ro = new ResourceOrder(instance);
        ArrayList<Task> possibleTasks = new ArrayList<Task>();

        // init jobsDispoTime
        for(int i = 0; i < instance.numJobs; i++) {
            jobsDispoTimes.add(0);
        }

        // init machinesDispoTimes
        for(int i = 0; i < instance.numMachines; i++) {
            machinesDispoTimes.add(0);
        }

        // first possible tasks
        for(int i = 0; i < instance.numJobs; i++) {
            Task task = new Task(i, 0);
            possibleTasks.add(task);
        }

        // Loop while there is tasks to do
        while(!possibleTasks.isEmpty()) {

            Task task = null; // the task that will be added to the ro

            // what kind of solver do we want to use ?
            switch(this.priority) {
                case SPT:
                    // Return the shortest possible task
                    task = getTaskSTP(instance, possibleTasks);
                    break;
                case LPT:
                    // Return the longest possible task
                    task = getTaskLPT(instance, possibleTasks);
                    break;
                case LRPT:
                    // Return the longest possible task
                    task = getTaskLRPT(instance, possibleTasks);
                    break;
                case EST_SPT:
                    // Return the shortest possible task that can start earlier
                    task = getTaskEST_SPT(instance, possibleTasks);
                    jobsDispoTimes.set(task.job, instance.duration(task));
                    machinesDispoTimes.set(instance.machine(task), instance.duration(task));
                    break;
                case EST_LRPT:
                    // Return the longest remaining process possible task that can start earlier
                    task = getTaskEST_LRPT(instance, possibleTasks);
                    jobsDispoTimes.set(task.job, instance.duration(task));
                    machinesDispoTimes.set(instance.machine(task), instance.duration(task));

                    System.out.println("possibles tasks : ");
                    for(Task t : possibleTasks) {
                        System.out.println(t.toString());
                    }

                    System.out.println("chosen task : ");
                    System.out.println(task);
                    break;
                default:
                    // Return the shortest possible task
                    task = getTaskSTP(instance, possibleTasks);
                    break;
            }

            // Add the task to the ro, remove the task from the possibles tasks
            ro.addTaskToMachine(instance.machine(task), task);
            possibleTasks.remove(task);

            if(task.task < instance.numTasks - 1) {
                possibleTasks.add(new Task(task.job, task.task + 1));
            }
        }

        // return the result
        return new Result(instance, ro.toSchedule(), Result.ExitCause.ProvedOptimal);
    }


    // STP : shortest processing task
    private Task getTaskSTP(Instance instance, ArrayList<Task> tasks) {
        Task minTask = tasks.get(0);
        int minDuration = Integer.MAX_VALUE;

        for(Task task : tasks) {
            if(instance.duration(task) < minDuration) {
                minTask = task;
                minDuration = instance.duration(task);
            }
        }

        return minTask;
    }

    // LPT : longest processing task
    private Task getTaskLPT(Instance instance, ArrayList<Task> tasks) {
        Task maxTask = tasks.get(0);
        int maxDuration = Integer.MIN_VALUE;

        for(Task task : tasks) {
            if(instance.duration(task) > maxDuration) {
                maxTask = task;
                maxDuration = instance.duration(task);
            }
        }

        return maxTask;
    }

    // LRPT : Longest remaining processing task
    private Task getTaskLRPT(Instance instance, ArrayList<Task> tasks) {
        Task LRPTTask = tasks.get(0);
        int maxJob = Integer.MIN_VALUE;

        for(Task task : tasks) {
            int remainingTime = getRemainingTimeInJob(instance, task.job, task.task);
            if(remainingTime > maxJob) {
                LRPTTask = task;
                maxJob = remainingTime;
            }
        }

        return LRPTTask;
    }

    private int getRemainingTimeInJob(Instance instance, int job, int task) {
        int t = 0;
        for(int i = task; i < instance.numTasks; i++) {
            t += instance.duration(new Task(job, i));
        }
        return t;
    }


    // EST_SPT : shortest task that can start earlier
    private Task getTaskEST_SPT(Instance instance, ArrayList<Task> tasks) {
        ArrayList<Task> EST_SPTTasks = new ArrayList<Task>();
        Task EST_SPTTask = null;
        int earlierTask = Integer.MAX_VALUE;

        for(Task task : tasks) {
            int start_time = Math.max(jobsDispoTimes.get(task.job), machinesDispoTimes.get(instance.machine(task)));
            if(start_time < earlierTask) {
                EST_SPTTasks = new ArrayList<Task>();
                EST_SPTTasks.add(task);
                earlierTask = start_time;
            } else if(start_time == earlierTask) {
                EST_SPTTasks.add(task);
            }
        }

        EST_SPTTask = getTaskSTP(instance, EST_SPTTasks);

        return EST_SPTTask;
    }

    // EST_LRPT : longest remaining process task that can start earlier
    private Task getTaskEST_LRPT(Instance instance, ArrayList<Task> tasks) {

        ArrayList<Task> EST_LRPTTasks = new ArrayList<Task>();
        Task EST_LRPTTask = null;
        int earlierTask = Integer.MAX_VALUE;

        for(Task task : tasks) {
            int start_time = Math.max(jobsDispoTimes.get(task.job), machinesDispoTimes.get(instance.machine(task)));
            if(start_time < earlierTask) {
                EST_LRPTTasks = new ArrayList<Task>();
                EST_LRPTTasks.add(task);
                earlierTask = start_time;
            } else if(start_time == earlierTask) {
                EST_LRPTTasks.add(task);
            }
        }

        EST_LRPTTask = getTaskLRPT(instance, EST_LRPTTasks);

        return EST_LRPTTask;
    }
}
