import java.io.Serializable;
/*-----------------------------------------------------
 PART 1 STEP 4 twotuple class
----------------------------------------------------- */
/**
 * stores two related values as a pair
 *
 * @param <A> the type of the first value
 * @param <B> the type of the second value
 */
public class TwoTuple<A, B> implements Serializable {
    private static final long serialVersionUID = 1L;

    public final A first;
    public final B second;

    /**
     * creates a tuple with two values
     *
     * @param first the first value
     * @param second the second value
     */
    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /**
     * returns a string representation of the tuple
     *
     * @return the tuple as a string
     */
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}