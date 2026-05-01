import java.util.*;

public class Controller {
    public void addEvent(Event event) {
        // create thread using event
        Thread thread = new Thread(event);

        // start the thread -> calls event.run
        thread.start();
    }
}