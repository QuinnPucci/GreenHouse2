import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*-----------------------------------------------------
 part 1 step 2 restart event
----------------------------------------------------- */

public class Restart extends Event {
    private GreenhouseControls greenhouse;
    private String filename;

    public Restart(long delayTime, String filename, GreenhouseControls greenhouse) {
        super(delayTime);
        this.filename = filename;
        this.greenhouse = greenhouse;
    }

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

    @Override
    public String toString() {
        return "Restarting system";
    }
}