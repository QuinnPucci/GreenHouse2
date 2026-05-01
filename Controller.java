import java.io.Serializable;

// part 2 -> make controller serializable
public class Controller implements Serializable {
    private static final long serialVersionUID = 1L;

    public void addEvent(Event event) {
        // create thread using event
        Thread thread = new Thread(event);

        // start thread and call run
        thread.start();
    }
}