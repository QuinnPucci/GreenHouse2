
public class LightOff extends Event {
    private GreenhouseControls greenhouse;

    public LightOff(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime);
    }

    @Override
    public void action() {
        greenhouse.setVariable("light", false);
    }

    @Override
    public String toString() {
        return "Light is off";
    }
}