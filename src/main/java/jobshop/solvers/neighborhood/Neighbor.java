package jobshop.solvers.neighborhood;

public abstract class Neighbor<Encoding> {

    public abstract void applyOn(Encoding current);
    public abstract void undoApplyOn(Encoding current);

}
