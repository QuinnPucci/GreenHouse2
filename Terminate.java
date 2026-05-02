/* --------------------------------------------------
----------------------------------------------------- */

public class Terminate extends Event {

    public Terminate(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    @Override
    public void action() {
        // terminate current greenhouse only, not the whole program
        greenhouse.terminateEvents();
    }

    @Override
    public String toString() {
        return "Terminating";
    }
}