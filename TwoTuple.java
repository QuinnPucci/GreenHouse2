import java.io.Serializable;

/*-----------------------------------------------------
 PART 1 STEP 4 twotuple class
----------------------------------------------------- */

public class TwoTuple<A, B> implements Serializable {
    private static final long serialVersionUID = 1L;

    public final A first;
    public final B second;

    public TwoTuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}