/* --------------------------------------------------

----------------------------------------------------- */

public class Bell extends Event {
    private GreenhouseControls greenhouse;

    public Bell(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
        this.greenhouse = greenhouse;
    }

    @Override
    public void action() {
        // no state change
    }

    @Override
    public String toString() {
        return "Bing!";
    }
}