/*-----------------------------------------------------
 part 2 gui output interface
----------------------------------------------------- */
/**
 * provides an output target for greenhouse event messages
 */
public interface GreenhouseOutput {

    /**
     * outputs text to the target display
     *
     * @param text the text to output
     */
    void output(String text);
}