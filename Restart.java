import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*-----------------------------------------------------
 part 1 step 2 restart event
----------------------------------------------------- */

/**
 * represents a restart event that reads an events file and creates event objects
 */
public class Restart extends Event {
    private String filename;

    /**
     * creates a restart event with a delay time and events filename
     *
     * @param delayTime the event delay time in milliseconds
     * @param filename the events file to read
     * @param greenhouse the greenhouse controller used by the event
     */
    public Restart(long delayTime, String filename, GreenhouseControls greenhouse) {
        super(delayTime, greenhouse);
        this.filename = filename;
    }

    /**
     * reads the events file and creates each listed event by class name
     */
    @Override
    public void action() {
        try {
            // read events from file
            Scanner scanner = new Scanner(new File(filename));

            while (scanner.hasNextLine()) {
                // get next line
                String line = scanner.nextLine().trim();

                if (line.length() > 0) {
                    // split event and time
                    String[] parts = line.split(",");

                    // get event name
                    String eventName = parts[0].split("=")[1].trim();

                    // get event time
                    long eventTime = Long.parseLong(parts[1].split("=")[1].trim());

                    // create event by name
                    greenhouse.createEvent(eventName, eventTime);
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("could not find events file " + filename);
        }
    }

    /**
     * returns the restart event description
     *
     * @return the restart event description
     */
    @Override
    public String toString() {
        return "Restarting system";
    }
}