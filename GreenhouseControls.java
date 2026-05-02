import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.io.Serializable;

/**
 * controls greenhouse event creation thread control state variables and gui output
 */
public class GreenhouseControls extends Controller implements Serializable {

    private String eventsFile = "examples1.txt";

    // PART 1 STEP 3 -> thread collection and suspend state
    // part 1 step 3 thread collection and suspend state
    private transient List<Thread> eventThreads = new ArrayList<Thread>();
    private boolean suspended = false;

    // PART 1 STEP 4
    private List<TwoTuple<String, Object>> stateVariables =
            new ArrayList<TwoTuple<String, Object>>();
    // PART 2 - gui output target
    private transient GreenhouseOutput output;
    // part 2 terminate state
    // this is so terminate stops the events, not ends the program
    private boolean terminated = false;

/* -----------------------------------------------
   PART 2 METHODS
 ----------------------------------------------*/
    // set gui output target

    /**
     * sets the output target used by the graphical interface
     *
     * @param output the gui output target
     */
    public void setOutput(GreenhouseOutput output) {
        this.output = output;
    }

    // output event description

    /**
     * formats an event message and sends it to the gui or console
     *
     * @param time the event delay time in milliseconds
     * @param description the event description
     */
    public synchronized void outputEvent(long time, String description) {
        // format output for console and gui
        String message =
                "[" + Thread.currentThread().getName() + "]" +
                        "[" + time + "] " +
                        description;

        // send output to gui if available
        if (output != null) {
            output.output(message);
        } else {
            System.out.println(message);
        }
    }

    // check if any event thread is running

    /**
     * checks whether any event thread is currently running
     *
     * @return true if at least one event thread is alive otherwise false
     */
    public synchronized boolean isRunning() {
        // call helper method
        setupTransientFields();
        // check event threads
        for (Thread thread : eventThreads) {
            if (thread.isAlive()) {
                return true;
            }
        }

        return false;
    }


    // check if events are suspended

    /**
     * checks whether event threads are currently suspended
     *
     * @return true if events are suspended otherwise false
     */
    public synchronized boolean isSuspended() {
        return suspended;
    }

    // setup transient fields after restore

    /**
     * restores transient fields after deserialization
     */
    private void setupTransientFields() {
        if (eventThreads == null) {
            eventThreads = new ArrayList<Thread>();
        }
    }
    // Below are helper methods for keeping the program running
    // but modifying terminate to stop the current events not close the window

    // terminate event threads

    /**
     * terminates the current running greenhouse events
     */
    public synchronized void terminateEvents() {
        // set terminated state
        terminated = true;

        // interrupt all event threads
        for (Thread thread : eventThreads) {
            if (thread != Thread.currentThread()) {
                thread.interrupt();
            }
        }
    }

    // check if greenhouse is terminated

    /**
     * checks whether the greenhouse has been terminated
     *
     * @return true if the greenhouse has been terminated otherwise false
     */
    public synchronized boolean isTerminated() {
        return terminated;
    }

    // clear terminated state

    /**
     * clears the terminated state before starting or restarting events
     */
    public synchronized void clearTerminated() {
        terminated = false;
    }

/*-----------------------------------------------
   PART 2 METHODS END
----------------------------------------------*/


    /**
     * prints the command line usage instructions
     */
    public static void printUsage() {
        System.out.println("Correct format: ");
        System.out.println("  java GreenhouseControls -f <filename>, or");
        System.out.println("  java GreenhouseControls -d dump.out");
    }

/*-----------------------------------------------------
 part 1 step 2 create events
----------------------------------------------------- */

    /**
     * creates an event object from a class name and starts it as a thread
     *
     * @param className the name of the event class
     * @param delayTime the event delay time in milliseconds
     */
    public void createEvent(String className, long delayTime) {
        try {
            // find class by name
            Class<?> eventClass = Class.forName(className);

            // find constructor with delaytime and greenhousecontrols
            Constructor<?> constructor =
                    eventClass.getConstructor(long.class, GreenhouseControls.class);

            // create event object
            Event event =
                    (Event) constructor.newInstance(delayTime, this);

            // start event thread
            addEvent(event);

        } catch (Exception e) {
            System.out.println("could not create event " + className);
            e.printStackTrace();
        }
    }
// --------------------------------------------------------------

/*-----------------------------------------------------
 part 1 step 2 create restart
----------------------------------------------------- */

    /**
     * creates a restart event object from a class name filename and delay time
     *
     * @param className the name of the event class
     * @param delayTime the event delay time in milliseconds
     * @param filename the events file name
     */
    public void createEvent(String className, long delayTime, String filename) {
        try {
            // find class by name
            Class<?> eventClass = Class.forName(className);

            // find constructor with delaytime filename and greenhousecontrols
            Constructor<?> constructor =
                    eventClass.getConstructor(long.class, String.class, GreenhouseControls.class);

            // create event object
            Event event =
                    (Event) constructor.newInstance(delayTime, filename, this);

            // start event thread
            addEvent(event);

        } catch (Exception e) {
            System.out.println("could not create event " + className);
            e.printStackTrace();
        }
    }
// --------------------------------------------------------------

/*-----------------------------------------------------
PART 1 STEP 3 METHODS
----------------------------------------------------- */
    // start and store event threads

    /**
     * starts an event as a thread and stores the thread
     *
     * @param event the event to start
     */
    @Override
    public void addEvent(Event event) {
        // call helper method
        setupTransientFields();

        // create thread using event
        Thread thread = new Thread(event);

        // store thread
        eventThreads.add(thread);

        // start thread and call run
        thread.start();
    }

    // suspend event threads

    /**
     * suspends all event threads
     */
    public synchronized void suspendEvents() {
        // set suspended state
        suspended = true;
    }

    // part 1 step 3 resume event threads

    /**
     * resumes all suspended event threads
     */
    public synchronized void resumeEvents() {
        // clear suspended state
        suspended = false;

        // wake waiting threads
        notifyAll();
    }

    // event wait check

    /**
     * pauses an event thread while the greenhouse is suspended
     *
     * @throws InterruptedException if the waiting thread is interrupted
     */
    public synchronized void waitIfSuspended() throws InterruptedException {
        // wait while suspended
        while (suspended) {
            wait();
        }
    }

    // access event threads

    /**
     * returns the collection of event threads
     *
     * @return the list of event threads
     */
    public List<Thread> getEventThreads() {
        // return event threads
        return eventThreads;
    }
/*-----------------------------------------------------
PART 1 STEP 3 METHODS END
----------------------------------------------------- */

/*-----------------------------------------------------
PART 1 STEP 4 METHODS
----------------------------------------------------- */

    // synchronized state update

    /**
     * updates a greenhouse state variable in the state collection
     *
     * @param key the name of the state variable
     * @param value the value to store
     */
    public synchronized void setVariable(String key, Object value) {
        // search for existing variable
        for (int i = 0; i < stateVariables.size(); i++) {
            TwoTuple<String, Object> variable = stateVariables.get(i);

            if (variable.first.equals(key)) {
                // replace existing variable
                stateVariables.set(i, new TwoTuple<String, Object>(key, value));
                return;
            }
        }

        // add new variable
        stateVariables.add(new TwoTuple<String, Object>(key, value));
    }

    // get one state variable

    /**
     * returns one greenhouse state variable
     *
     * @param key the name of the state variable
     * @return the stored value or null if the variable is not found
     */
    public synchronized Object getVariable(String key) {
        // search for variable
        for (TwoTuple<String, Object> variable : stateVariables) {
            if (variable.first.equals(key)) {
                return variable.second;
            }
        }

        // variable not found
        return null;
    }

    // get all state variables

    /**
     * returns a copy of all greenhouse state variables
     *
     * @return a copy of the state variable collection
     */
    public synchronized List<TwoTuple<String, Object>> getStateVariables() {
        // return copy of variables
        return new ArrayList<TwoTuple<String, Object>>(stateVariables);
    }

/*-----------------------------------------------------
  PART 1 STEP 4 METHODS END
----------------------------------------------------- */

    //---------------------------------------------------------

    /**
     * starts the greenhouse controls program from the command line
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String option = args[0];
            String filename = args[1];

            if ( !(option.equals("-f")) && !(option.equals("-d")) ) {
                System.out.println("Invalid option");
                printUsage();
            }

            GreenhouseControls gc = new GreenhouseControls();

            if (option.equals("-f"))  {
                // replace add event with create event
                gc.createEvent("Restart", 0, filename);
            }
            // remove gc.run()
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid number of parameters");
            printUsage();
        }
    }

} ///:~