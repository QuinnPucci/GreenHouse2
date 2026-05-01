import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.io.Serializable;

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

/* -----------------------------------------------
   PART 2 METHODS
 ----------------------------------------------*/
    // set gui output target
    public void setOutput(GreenhouseOutput output) {
        this.output = output;
    }

    // output event description
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
    public synchronized boolean isSuspended() {
        return suspended;
    }

    // setup transient fields after restore
    private void setupTransientFields() {
        if (eventThreads == null) {
            eventThreads = new ArrayList<Thread>();
        }
    }

/*-----------------------------------------------
   PART 2 METHODS END
----------------------------------------------*/


    public static void printUsage() {
        System.out.println("Correct format: ");
        System.out.println("  java GreenhouseControls -f <filename>, or");
        System.out.println("  java GreenhouseControls -d dump.out");
    }

/*-----------------------------------------------------
 part 1 step 2 create events
----------------------------------------------------- */

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
    public synchronized void suspendEvents() {
        // set suspended state
        suspended = true;
    }

 // part 1 step 3 resume event threads
    public synchronized void resumeEvents() {
        // clear suspended state
        suspended = false;

        // wake waiting threads
        notifyAll();
    }

 // event wait check
    public synchronized void waitIfSuspended() throws InterruptedException {
        // wait while suspended
        while (suspended) {
            wait();
        }
    }

 // access event threads
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
    public synchronized List<TwoTuple<String, Object>> getStateVariables() {
        // return copy of variables
        return new ArrayList<TwoTuple<String, Object>>(stateVariables);
    }

/*-----------------------------------------------------
  PART 1 STEP 4 METHODS END
----------------------------------------------------- */

    //---------------------------------------------------------
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