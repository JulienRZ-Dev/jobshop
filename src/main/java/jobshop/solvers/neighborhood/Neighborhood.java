package jobshop.solvers.neighborhood;

import java.util.List;

public abstract class Neighborhood<Encoding> {

    public abstract List<Neighbor<Encoding>> generateNeighbors(Encoding current);

}
