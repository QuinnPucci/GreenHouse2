/* --------------------------------------------------

----------------------------------------------------- */

/**
 * represents a bell event that outputs a bell message
 */
public class Bell extends Event {

    /**
     * creates a bell event with a delay time
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public Bell(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    /**
     * performs the bell event action
     */
    @Override
    public void action() {
        // no state change
    }

    /**
     * returns the bell event description
     *
     * @return the bell event description
     */
    @Override
    public String toString() {
        return "Bing!";
    }
}