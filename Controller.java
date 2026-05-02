import java.io.Serializable;


/**
 * provides the base controller behavior for starting event threads
 */
// part 2 -> make controller serializable
public class Controller implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * starts an event as a new thread
     *
     * @param event the event to start
     */
    public void addEvent(Event event) {
        // create thread using event
        Thread thread = new Thread(event);

        // start thread and call run
        thread.start();
    }
}