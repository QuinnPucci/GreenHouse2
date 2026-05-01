/* --------------------------------------------------

----------------------------------------------------- */

public class WaterOn extends Event {
    private GreenhouseControls greenhouse;

    public WaterOn(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime);
    }

    @Override
    public void action() {
        greenhouse.setVariable("water", true);
    }

    @Override
    public String toString() {
        return "Greenhouse water is on";
    }
}