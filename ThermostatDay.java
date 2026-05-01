/* --------------------------------------------------

----------------------------------------------------- */

public class ThermostatDay extends Event {
    private GreenhouseControls greenhouse;

    public ThermostatDay(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime);
    }

    @Override
    public void action() {
        greenhouse.setVariable("thermostat", "Day");
    }

    @Override
    public String toString() {
        return "Thermostat on day setting";
    }
}