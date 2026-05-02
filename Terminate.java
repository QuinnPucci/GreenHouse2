/* --------------------------------------------------
----------------------------------------------------- */

/**
 * represents an event that terminates the current greenhouse run
 */
public class Terminate extends Event {

    /**
     * creates a terminate event with a delay time
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public Terminate(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    /**
     * terminates the current greenhouse event threads
     */
    @Override
    public void action() {
        // terminate current greenhouse only, not the whole program
        greenhouse.terminateEvents();
    }

    /**
     * returns the terminate event description
     *
     * @return the terminate event description
     */
    @Override
    public String toString() {
        return "Terminating";
    }
}