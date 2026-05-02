/**
 * represents an event that turns the greenhouse light off
 */
public class LightOff extends Event {

    /**
     * creates a light off event with a delay time
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public LightOff(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    /**
     * sets the light state to false
     */
    @Override
    public void action() {
        greenhouse.setVariable("light", false);
    }

    /**
     * returns the light off event description
     *
     * @return the light off event description
     */
    @Override
    public String toString() {
        return "Light is off";
    }
}