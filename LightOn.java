/**
 * represents an event that turns the greenhouse light on
 */
public class LightOn extends Event {

    // constructor takes delay time and greenhouse

    /**
     * creates a light on event with a delay time
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public LightOn(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
        // event can update greenhouse state
    }

    /**
     * sets the light state to true
     */
    @Override
    public void action() {
        // uses set variable now isntead of just = true
        greenhouse.setVariable("light", true); // THIS DOES NOT EXIST YET
    }

    /**
     * returns the light on event description
     *
     * @return the light on event description
     */
    @Override
    public String toString() {
        return "Light is on";
    }
}