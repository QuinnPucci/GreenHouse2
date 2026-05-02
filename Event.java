/* --------------------------------------------------
 PART 1: STEP 1 MODIFY EVENT TO WORK WITH THREADS.
 step 3: supports suyspend and resume
 step 5: final output through ghc
----------------------------------------------------- */

/**
 * represents a greenhouse event that runs as a separate thread
 */
public abstract class Event implements Runnable {
    // delaytime is used inside run now
    protected final long delayTime;

    // reference to greenhousecontrols
    protected final GreenhouseControls greenhouse;

    /**
     * creates an event with a delay time and greenhouse controller reference
     *
     * @param delayTime the event delay time in milliseconds
     * @param greenhouse the greenhouse controller used by the event
     */
    public Event(long delayTime, GreenhouseControls greenhouse) {
        this.delayTime = delayTime;
        this.greenhouse = greenhouse;
    }

    // overridden run method

    /**
     * runs the event by waiting for its delay time then outputting and performing its action
     */
    @Override
    public void run() {
        try {
            // tracks how much delay has passed
            long slept = 0;

            // each event handles its own timing now
            while (slept < delayTime) {
                // stop if greenhouse was terminated
                if (greenhouse.isTerminated()) {
                    return;
                }
                // waits here if greenhouse is suspended
                greenhouse.waitIfSuspended();

                // sleep in small pieces so suspend can be checked
                long sleepTime = Math.min(100, delayTime - slept);
                Thread.sleep(sleepTime);
                slept += sleepTime;
            }

            // check suspend one more time before action
            greenhouse.waitIfSuspended();
            // stop if greenhouse was terminated
            if (greenhouse.isTerminated()) {
                return;
            }
            // output event description
            greenhouse.outputEvent(delayTime, this.toString());

            // event ready then action
            action();

        } catch (InterruptedException e) {
            // output interrupted event
            greenhouse.outputEvent(delayTime, "event interrupted " + this);
        }
    }

    /**
     * performs the event specific action
     */
    public abstract void action();

    // returns delaytime

    /**
     * returns the event delay time
     *
     * @return the event delay time in milliseconds
     */
    public long getDelayTime() {
        return delayTime;
    }
}