package jobshop.solvers;

import jobshop.Instance;
import jobshop.Result;

public class GreedySolver implements Solver {

    public enum Priority {
        SPT, LPT, SRPT, LRPT, EST_SPT, EST_LPT, EST_SRPT, EST_LRPT
    }

    final Priority priority;
    public GreedySolver(Priority p) {
        this.priority = p;
    }

    @Override
    public Result solve(Instance instance, long deadline) {
        throw new UnsupportedOperationException();
    }
}
