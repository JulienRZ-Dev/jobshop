package jobshop.solvers.neighborhood;

import jobshop.encodings.ResourceOrder;

import java.util.ArrayList;
import java.util.List;

/** Implementation of the Nowicki and Smutnicki neighborhood.
 *
 * It works on the ResourceOrder encoding by generating two neighbors for each block
 * of the critical path.
 * For each block, two neighbors should be generated that respectivly swap the first two and
 * last two tasks of the block.
 */
public class Nowicki extends Neighborhood<ResourceOrder> {

    /** A block represents a subsequence of the critical path such that all tasks in it execute on the same machine.
     * This class identifies a block in a ResourceOrder representation.
     *
     * Consider the solution in ResourceOrder representation
     * machine 0 : (0,1) (1,2) (2,2)
     * machine 1 : (0,2) (2,1) (1,1)
     * machine 2 : ...
     *
     * The block with : machine = 1, firstTask= 0 and lastTask = 1
     * Represent the task sequence : [(0,2) (2,1)]
     *
     * */
    public static class Block {
        /** machine on which the block is identified */
        public final int machine;
        /** index of the first task of the block */
        public final int firstTask;
        /** index of the last task of the block */
        public final int lastTask;

        Block(int machine, int firstTask, int lastTask) {
            this.machine = machine;
            this.firstTask = firstTask;
            this.lastTask = lastTask;
        }
    }

    /**
     * Represents a swap of two tasks on the same machine in a ResourceOrder encoding.
     *
     * Consider the solution in ResourceOrder representation
     * machine 0 : (0,1) (1,2) (2,2)
     * machine 1 : (0,2) (2,1) (1,1)
     * machine 2 : ...
     *
     * The swap with : machine = 1, t1= 0 and t2 = 1
     * Represent inversion of the two tasks : (0,2) and (2,1)
     * Applying this swap on the above resource order should result in the following one :
     * machine 0 : (0,1) (1,2) (2,2)
     * machine 1 : (2,1) (0,2) (1,1)
     * machine 2 : ...
     */
    public static class Swap extends Neighbor<ResourceOrder> {
        // machine on which to perform the swap
        public final int machine;
        // index of one task to be swapped
        public final int t1;
        // index of the other task to be swapped
        public final int t2;

        Swap(int machine, int t1, int t2) {
            this.machine = machine;
            this.t1 = t1;
            this.t2 = t2;
        }


        /** Apply this swap on the given resource order, transforming it into a new solution. */
        @Override
        public void applyOn(ResourceOrder current) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void undoApplyOn(ResourceOrder current) {
            throw new UnsupportedOperationException();
        }
    }


    @Override
    public List<Neighbor<ResourceOrder>> generateNeighbors(ResourceOrder current) {
        List<Neighbor<ResourceOrder>> neighbors = new ArrayList<>();
        // iterate over all blocks of the critical
        for(var block : blocksOfCriticalPath(current)) {
            // for this block, compute all neighbors and add them to the list of neighbors
            neighbors.addAll(neighbors(block));
        }
        return neighbors;
    }

    /** Returns a list of all the blocks of the critical path. */
    List<Block> blocksOfCriticalPath(ResourceOrder order) {
        throw new UnsupportedOperationException();
    }

    /** For a given block, return the possible swaps for the Nowicki and Smutnicki neighborhood */
    List<Swap> neighbors(Block block) {
        throw new UnsupportedOperationException();

    }

}
