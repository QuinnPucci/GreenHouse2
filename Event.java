/* --------------------------------------------------
 PART 1: STEP 1 MODIFY EVENT TO WORK WITH THREADS.
 step 3: supports suyspend and resume
 step 5: final output through ghc
----------------------------------------------------- */

public abstract class Event implements Runnable {
    // delaytime is used inside run now
    protected final long delayTime;

    // reference to greenhousecontrols
    protected final GreenhouseControls greenhouse;

    public Event(long delayTime, GreenhouseControls greenhouse) {
        this.delayTime = delayTime;
        this.greenhouse = greenhouse;
    }

    // overridden run method
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

    public abstract void action();

    // returns delaytime
    public long getDelayTime() {
        return delayTime;
    }
}