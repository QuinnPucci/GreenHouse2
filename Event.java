/* --------------------------------------------------
 PART 1: STEP 1 MODIFY EVENT TO WORK WITH THREADS
----------------------------------------------------- */

public abstract class Event implements Runnable {
    // delayTime stays but used inside run
    protected final long delayTime;

    public Event(long delayTime) {
        this.delayTime = delayTime;
        // Thread sleep handles the delay
    }

    // overidden run method
    @Override
    public void run() {
        try {
            // each event handles its own timing now
            // pauses for delayTime ms
            Thread.sleep(delayTime);

            // formated output [thread-name][time]
            System.out.println(
                    "[" + Thread.currentThread().getName() + "]" +
                            "[" + delayTime + "] " +
                            this
            );


            // event ready -> action
            action();

        } catch (InterruptedException e) {
            // Thread.sleep() InterruptedException throw
            System.out.println(
                    "[" + Thread.currentThread().getName() + "]" +
                            "[" + delayTime + "] Event interrupted: " + this
            );
        }
    }

    public abstract void action();

    // helper method to check delayTime
    public long getDelayTime() {
        return delayTime;
    }
}