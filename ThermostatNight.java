/**
 * represents an event that sets the thermostat to night mode
 */
public class ThermostatNight extends Event {

    /**
     * creates a thermostat night event with a delay time
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public ThermostatNight(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    /**
     * sets the thermostat state to night
     */
    @Override
    public void action() {
        greenhouse.setVariable("thermostat", "Night");
    }

    /**
     * returns the thermostat night event description
     *
     * @return the thermostat night event description
     */
    @Override
    public String toString() {
        return "Thermostat on night setting";
    }
}