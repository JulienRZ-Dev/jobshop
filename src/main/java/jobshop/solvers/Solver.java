package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;

public interface Solver {

    Result solve(Instance instance, long deadline);

    /** Static factory method to create a new solver based on its name. */
    static Solver getSolver(String name) {
        switch (name) {
            case "basic": return new BasicSolver();
            case "random": return new RandomSolver();
            case "spt": return new GreedySolver(GreedySolver.Priority.SPT);
            // TODO: add new solvers
            default: throw new RuntimeException("Unknown solver: "+ name);
        }
    }

}
