package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;
import jobshop.encodings.ResourceOrder;
import jobshop.encodings.Task;
import jobshop.solvers.neighborhood.Neighbor;
import jobshop.solvers.neighborhood.Neighborhood;
import jobshop.solvers.neighborhood.Nowicki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** This class handle the Taboo descent method */
public class TabooSolver implements Solver {

    final Neighborhood<ResourceOrder> neighborhood;
    final Solver baseSolver;

    /** Creates a new taboo solver with a given neighborhood and a solver for the initial solution.
     *
     * @param neighborhood Neighborhood object that should be used to generates neighbor solutions to the current candidate.
     * @param baseSolver A solver to provide the initial solution.
     */
    public TabooSolver(Neighborhood<ResourceOrder> neighborhood, Solver baseSolver) {
        this.neighborhood = neighborhood;
        this.baseSolver = baseSolver;
    }


    @Override
    public Result solve(Instance instance, long deadline) {

        long start = System.currentTimeMillis(); // start time for the deadline
        Result result = baseSolver.solve(instance, deadline); // result of the solver
        ResourceOrder ro = new ResourceOrder(result.schedule.get()); // ro of this solver
        List<Neighbor<ResourceOrder>> neighborhood; // List of neighbors
        ResourceOrder bestRo = null; // current best (smallest makespan)
        Neighbor<ResourceOrder> currentNeighbor = null; // current neighbor to explore
        int currentMakespan = Integer.MAX_VALUE;
        int bestMakespan = Integer.MAX_VALUE;
        int timeTaboo = 10; // nb iter for a swap to be Taboo
        int currentIter = 1; // current iteration
        int maxIter = 100; // nb iterations before to block
        int nbTasks = result.instance.numJobs * result.instance.numTasks; // number of tasks to do for all the jobs
        int tabooSwaps[][] = new int[nbTasks][nbTasks]; // nbTasks*nbTasks matrix
        int nbNeighborExplored = 0;


        // Let's loop while the deadline haven't been reached
        while(System.currentTimeMillis() - start < 3000) {

            // Let's loop while the max iterations haven't been reached
            while(currentIter < maxIter) {

                int localMakespan = Integer.MAX_VALUE;
                neighborhood = this.neighborhood.generateNeighbors(ro); // get the neighbors from ro

                int[] taskSwapped = new int[0];
                // Loop over the neighbors
                for(Neighbor<ResourceOrder> neighbor : neighborhood) {

                    neighbor.applyOn(ro); // transform ro into its neighbor ro
                    localMakespan = ro.toSchedule().get().makespan();
                    taskSwapped = neighbor.getTasks();

                    if(tabooSwaps[taskSwapped[0]][taskSwapped[1]] < currentIter) { // if the neighbor is not taboo
                        // System.out.println("Local makespan is : " + localMakespan);
                        // System.out.println("Current makespan is : " + currentMakespan);
                        // System.out.println("Best makespan is : " + bestMakespan);

                        if (currentMakespan > localMakespan) { // keep track of the current neighbor to explore

                            currentNeighbor = neighbor;
                            currentMakespan = localMakespan;
                            // System.out.println("A current have been found");
                        }

                        // System.out.println("--------------------");
                    }

                    neighbor.undoApplyOn(ro); // reset ro to its original state
                    nbNeighborExplored++;
                }

                currentNeighbor.applyOn(ro); // transform ro into the best neighbor's ro

                if(bestMakespan > currentMakespan) { // Keep track of the best
                    bestRo = ro.copy();
                    bestMakespan = localMakespan;
                    // System.out.println("A best have been found");
                }

                taskSwapped = currentNeighbor.getTasks();

                Task task1 = ro.getTaskOfMachine(taskSwapped[2],taskSwapped[0]);
                Task task2 = ro.getTaskOfMachine(taskSwapped[2],taskSwapped[1]);

                int t1_index = task1.job * instance.numTasks + task1.task;
                int t2_index = task2.job * instance.numTasks + task2.task;

                tabooSwaps[t1_index][t2_index] = currentIter + timeTaboo;
                tabooSwaps[t2_index][t1_index] = currentIter + timeTaboo;

                currentMakespan = Integer.MAX_VALUE; // reset the makespan
                currentIter++;
            }
            // System.out.println("nbNeighborExplored : " + nbNeighborExplored);
            return new Result(instance, bestRo.toSchedule(), Result.ExitCause.Blocked);
        }
        // System.out.println("nbNeighborExplored : " + nbNeighborExplored);
        return new Result(instance, bestRo.toSchedule(), Result.ExitCause.Timeout);
    }
}
