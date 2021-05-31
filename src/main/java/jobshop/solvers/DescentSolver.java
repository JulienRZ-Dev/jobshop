package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;
import jobshop.encodings.ResourceOrder;
import jobshop.solvers.neighborhood.Neighbor;
import jobshop.solvers.neighborhood.Neighborhood;

import java.util.List;

/** An empty shell to implement a descent solver. */
public class DescentSolver implements Solver {

    final Neighborhood<ResourceOrder> neighborhood;
    final Solver baseSolver;

    /** Creates a new descent solver with a given neighborhood and a solver for the initial solution.
     *
     * @param neighborhood Neighborhood object that should be used to generates neighbor solutions to the current candidate.
     * @param baseSolver A solver to provide the initial solution.
     */
    public DescentSolver(Neighborhood<ResourceOrder> neighborhood, Solver baseSolver) {
        this.neighborhood = neighborhood;
        this.baseSolver = baseSolver;
    }

    @Override
    public Result solve(Instance instance, long deadline) {

        long start = System.currentTimeMillis(); // start time for the deadline
        Result result = baseSolver.solve(instance, deadline); // result of the solver
        ResourceOrder ro = new ResourceOrder(result.schedule.get()); // ro of this solver
        Neighbor<ResourceOrder> best = null; // current best (smallest makespan)
        List<Neighbor<ResourceOrder>> neighborhood; // List of neighbors
        int currentMakespan = ro.toSchedule().get().makespan();
        boolean foundABest = false;
        int nbNeighborExplored = 0;

        // Let's loop while the deadline haven't been reached
        while(System.currentTimeMillis() - start < deadline) {

            neighborhood = this.neighborhood.generateNeighbors(ro); // get the neighbors from ro

            // Let's loop while neighbors are found
            if(!neighborhood.isEmpty()) {

                // Loop over the neighbors
                for(Neighbor<ResourceOrder> neighbor : neighborhood) {

                    neighbor.applyOn(ro); // transform ro into its neighbor ro

                    if (ro.toSchedule().isPresent() && (currentMakespan > ro.toSchedule().get().makespan())) {
                        foundABest = true;
                        best = neighbor;
                        currentMakespan = ro.toSchedule().get().makespan();
                    }

                    neighbor.undoApplyOn(ro); // must reset ro to its original state
                    nbNeighborExplored++;
                }

                if(!foundABest) { // if no best founds among neighbors, stop the execution
                    return new Result(instance, ro.toSchedule(), Result.ExitCause.Blocked);
                } else {
                    foundABest = false;
                    best.applyOn(ro); // transform ro into the best neighbor's ro
                }

            } else {
                return new Result(instance, ro.toSchedule(), Result.ExitCause.Blocked);
            }
        }
        return new Result(instance, ro.toSchedule(), Result.ExitCause.Timeout);
    }
}
