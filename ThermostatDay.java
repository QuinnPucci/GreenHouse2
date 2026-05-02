/**
 * represents an event that sets the thermostat to day mode
 */
public class ThermostatDay extends Event {

    /**
     * creates a thermostat day event with a delay time
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public ThermostatDay(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
    }

    /**
     * sets the thermostat state to day
     */
    @Override
    public void action() {
        greenhouse.setVariable("thermostat", "Day");
    }

    /**
     * returns the thermostat day event description
     *
     * @return the thermostat day event description
     */
    @Override
    public String toString() {
        return "Thermostat on day setting";
    }
}