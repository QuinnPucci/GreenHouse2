/**
 * represents an event that turns the greenhouse water off
 */
public class WaterOff extends Event {

    /**
     * creates a water off event with a delay time
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public WaterOff(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    /**
     * sets the water state to false
     */
    @Override
    public void action() {
        greenhouse.setVariable("water", false);
    }

    /**
     * returns the water off event description
     *
     * @return the water off event description
     */
    @Override
    public String toString() {
        return "Greenhouse water is off";
    }
}