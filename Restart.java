public class Restart extends Event {
    private GreenhouseControls greenhouse;
    private String filename;

    public Restart(long delayTime, String filename, GreenhouseControls greenhouse) {
        super(delayTime);
        this.filename = filename;
        this.greenhouse = greenhouse;
    }

    @Override
    public void action() {
        // create events by class name
        greenhouse.createEvent("ThermostatNight", 0);
        greenhouse.createEvent("LightOn", 2000);
        greenhouse.createEvent("WaterOff", 8000);
        greenhouse.createEvent("ThermostatDay", 10000);
        greenhouse.createEvent("Bell", 9000);
        greenhouse.createEvent("WaterOn", 6000);
        greenhouse.createEvent("LightOff", 4000);
        greenhouse.createEvent("Terminate", 12000);
    }

    @Override
    public String toString() {
        return "Restarting system";
    }
}