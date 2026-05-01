

// convert events to there own file
public class LightOn extends Event {

    // constructor takes delay time and greenhouse
    public LightOn(long delayTime, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
        // event can update greenhouse state
    }

    @Override
    public void action() {
        // uses set variable now isntead of just = true
        greenhouse.setVariable("light", true); // THIS DOES NOT EXIST YET
    }

    @Override
    public String toString() {
        return "Light is on";
    }
}