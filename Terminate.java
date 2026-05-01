/* --------------------------------------------------
----------------------------------------------------- */

public class Terminate extends Event {
    private GreenhouseControls greenhouse;

    public Terminate(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime);
    }

    @Override
    public void action() {
        System.exit(0);
    }

    @Override
    public String toString() {
        return "Terminating";
    }
}