/* --------------------------------------------------
----------------------------------------------------- */

public class Terminate extends Event {

    public Terminate(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
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