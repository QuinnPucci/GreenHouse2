/* --------------------------------------------------

----------------------------------------------------- */

public class ThermostatNight extends Event {
    private GreenhouseControls greenhouse;

    public ThermostatNight(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime);
    }

    @Override
    public void action() {
        greenhouse.setVariable("thermostat", "Night");
    }

    @Override
    public String toString() {
        return "Thermostat on night setting";
    }
}