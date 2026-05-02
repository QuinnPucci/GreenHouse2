/**
 * represents an event that turns the greenhouse water on
 */
public class WaterOn extends Event {

    /**
     * creates a water on event with a delay time
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public WaterOn(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    /**
     * sets the water state to true
     */
    @Override
    public void action() {
        greenhouse.setVariable("water", true);
    }

    /**
     * returns the water on event description
     *
     * @return the water on event description
     */
    @Override
    public String toString() {
        return "Greenhouse water is on";
    }
}