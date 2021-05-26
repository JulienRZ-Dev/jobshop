package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;
import jobshop.solvers.neighborhood.Nowicki;

/** Common interface that must implemented by all solvers. */
public interface Solver {

    /** Look for a solution until blocked or a deadline has been met.
     *
     * @param instance Jobshop instance that should be solved.
     * @param deadline Absolute time at which the solver should have returned a solution.
     *                 This time is in milliseconds and can be compared with System.currentTimeMilliseconds()
     * @return A Result containing the solution found and an explanation of why the solver exited.
     */
    Result solve(Instance instance, long deadline);

    /** Static factory method to create a new solver based on its name. */
    static Solver getSolver(String name) {
        switch (name) {
            case "basic": return new BasicSolver();
            case "random": return new RandomSolver();
            case "spt": return new GreedySolver(GreedySolver.Priority.SPT);
            case "lpt": return new GreedySolver(GreedySolver.Priority.LPT);
            case "lrpt": return new GreedySolver(GreedySolver.Priority.LRPT);
            case "est-spt": return new GreedySolver(GreedySolver.Priority.EST_SPT);
            case "est-lrpt": return new GreedySolver(GreedySolver.Priority.EST_LRPT);
            case "descent-spt": return new DescentSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.SPT));
            case "descent-lpt": return new DescentSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.LPT));
            case "descent-lrpt": return new DescentSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.LRPT));
            case "descent-est-spt": return new DescentSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.EST_SPT));
            case "descent-est-lrpt": return new DescentSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.EST_LRPT));
            case "taboo-spt": return new TabooSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.SPT));
            case "taboo-lpt": return new TabooSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.LPT));
            case "taboo-lrpt": return new TabooSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.LRPT));
            case "taboo-est-spt": return new TabooSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.EST_SPT));
            case "taboo-est-lrpt": return new TabooSolver(new Nowicki(), new GreedySolver(GreedySolver.Priority.EST_LRPT));
            default: throw new RuntimeException("Unknown solver: "+ name);
        }
    }

}
