/*
Flatlaf ref:

JFormDesigner. (n.d.). *FlatLaf: Flat look and feel* [Source code]. GitHub. https://github.com/JFormDesigner/FlatLaf
FormDev Software GmbH. (n.d.). FlatLaf: Flat look and feel. https://www.formdev.com/flatlaf/

*/

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*-----------------------------------------------------
PART 2 GUI APP
----------------------------------------------------- */

public class GreenhouseApp {
    public static void main(String[] args) {
        // setup flatlaf before gui starts
        FlatLightLaf.setup();

        // start gui on swing thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GreenhouseGUI(new GreenhouseControls());
            }
        });
    }
}