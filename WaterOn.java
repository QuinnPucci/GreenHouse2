/* --------------------------------------------------

----------------------------------------------------- */

public class WaterOn extends Event {

    public WaterOn(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
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