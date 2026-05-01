
public class LightOff extends Event {

    public LightOff(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
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