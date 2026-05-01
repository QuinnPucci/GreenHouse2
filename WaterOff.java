/* --------------------------------------------------
 part 1 step 2 wateroff event
----------------------------------------------------- */

public class WaterOff extends Event {

    public WaterOff(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    @Override
    public void action() {
        greenhouse.setVariable("water", false);
    }

    @Override
    public String toString() {
        return "Greenhouse water is off";
    }
}