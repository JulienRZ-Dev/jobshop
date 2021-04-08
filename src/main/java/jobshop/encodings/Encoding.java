package jobshop.encodings;

import jobshop.Instance;

/** Common class for all encodings.
 *
 * The only requirement for this class is to provide a conversion from the encoding into a Schedule.
 */
public abstract class Encoding {

    /** Problem instance of which this is the solution. */
    public final Instance instance;

    public Encoding(Instance instance) {
        this.instance = instance;
    }

    /** Convert into a schedule. */
    public abstract Schedule toSchedule();
}
