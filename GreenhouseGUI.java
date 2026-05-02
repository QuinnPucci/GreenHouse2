/*
To earn the 5% bonus for a pleasing GUI I used the external library FlatLaf.
FlatLaf modernizes the look of Swing components.
The GUI still uses normal Swing components such as
JFrame, JButton, JMenuBar, JTextArea, JScrollPane,
JPopupMenu, JFileChooser, and JOptionPane,
but FlatLaf improves the visual rendering adding a "modern" feel.
My project zip also includes a reference doc, but here is the flatlaf refs
JFormDesigner. (n.d.). *FlatLaf: Flat look and feel* [Source code]. GitHub. https://github.com/JFormDesigner/FlatLaf
FormDev Software GmbH. (n.d.). FlatLaf: Flat look and feel. https://www.formdev.com/flatlaf/
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/*-----------------------------------------------------
PART 2 - GUI
----------------------------------------------------- */

/**
 * provides the graphical interface for controlling a GreenhouseControls object
 */
public class GreenhouseGUI extends JFrame implements GreenhouseOutput {
    private static final List<GreenhouseGUI> windows = new ArrayList<GreenhouseGUI>();

    private GreenhouseControls greenhouse;
    private File currentEventsFile;

    private JTextArea outputArea;
    private JLabel fileLabel;
    private JLabel statusLabel;

    private JButton startButton;
    private JButton restartButton;
    private JButton terminateButton;
    private JButton suspendButton;
    private JButton resumeButton;

    private JMenuItem newWindowItem;
    private JMenuItem closeWindowItem;
    private JMenuItem openEventsItem;
    private JMenuItem restoreItem;
    private JMenuItem exitItem;

    private JMenuItem popupStartItem;
    private JMenuItem popupRestartItem;
    private JMenuItem popupTerminateItem;
    private JMenuItem popupSuspendItem;
    private JMenuItem popupResumeItem;

    /**
     * creates a new gui window associated with a GreenhouseControls object
     *
     * @param greenhouse the greenhouse controller object for this window
     */
    public GreenhouseGUI(GreenhouseControls greenhouse) {
        this.greenhouse = greenhouse;
        this.greenhouse.setOutput(this);

        windows.add(this);

        setupWindow();
        setupMenu();
        setupLayout();
        setupPopupMenu();
        setupWindowClosing();
        setupTimer();

        updateControls();

        setVisible(true);
    }

    // setup main window

    /**
     * sets up the main window properties
     */
    private void setupWindow() {
        setTitle("Greenhouse Controls");
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    // setup pulldown menu

    /**
     * creates the pulldown menu and keyboard shortcuts
     */
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("file");

        int shortcut = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        newWindowItem = new JMenuItem("new window");
        newWindowItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, shortcut));
        newWindowItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GreenhouseGUI(new GreenhouseControls());
            }
        });

        closeWindowItem = new JMenuItem("close window");
        closeWindowItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, shortcut));
        closeWindowItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });

        openEventsItem = new JMenuItem("open events");
        openEventsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, shortcut));
        openEventsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEventsFile();
            }
        });

        restoreItem = new JMenuItem("restore");
        restoreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, shortcut));
        restoreItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreGreenhouse();
            }
        });

        exitItem = new JMenuItem("exit");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcut));
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitApplication();
            }
        });

        fileMenu.add(newWindowItem);
        fileMenu.add(closeWindowItem);
        fileMenu.addSeparator();
        fileMenu.add(openEventsItem);
        fileMenu.add(restoreItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // setup window layout

    /**
     * creates the main gui layout and control buttons
     */
    private void setupLayout() {
        JPanel rootPanel = new JPanel(new BorderLayout(12, 12));
        rootPanel.setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel topPanel = new JPanel(new BorderLayout(8, 8));

        JLabel titleLabel = new JLabel("greenhouse controls");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));

        fileLabel = new JLabel("events file none");
        statusLabel = new JLabel("status stopped");

        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(fileLabel, BorderLayout.WEST);
        topPanel.add(statusLabel, BorderLayout.EAST);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 8, 8));

        startButton = new JButton("start");
        restartButton = new JButton("restart");
        terminateButton = new JButton("terminate");
        suspendButton = new JButton("suspend");
        resumeButton = new JButton("resume");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGreenhouse();
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGreenhouse();
            }
        });

        terminateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                terminateGreenhouse();
            }
        });

        suspendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                greenhouse.suspendEvents();
                updateControls();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                greenhouse.resumeEvents();
                updateControls();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(terminateButton);
        buttonPanel.add(suspendButton);
        buttonPanel.add(resumeButton);

        rootPanel.add(topPanel, BorderLayout.NORTH);
        rootPanel.add(scrollPane, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(rootPanel);
    }

    // setup popup menu

    /**
     * creates the popup menu with the same commands as the buttons
     */
    private void setupPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        popupStartItem = new JMenuItem("start");
        popupRestartItem = new JMenuItem("restart");
        popupTerminateItem = new JMenuItem("terminate");
        popupSuspendItem = new JMenuItem("suspend");
        popupResumeItem = new JMenuItem("resume");

        popupStartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGreenhouse();
            }
        });

        popupRestartItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGreenhouse();
            }
        });

        popupTerminateItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                terminateGreenhouse();
            }
        });

        popupSuspendItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                greenhouse.suspendEvents();
                updateControls();
            }
        });

        popupResumeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                greenhouse.resumeEvents();
                updateControls();
            }
        });

        popupMenu.add(popupStartItem);
        popupMenu.add(popupRestartItem);
        popupMenu.add(popupTerminateItem);
        popupMenu.add(popupSuspendItem);
        popupMenu.add(popupResumeItem);

        outputArea.setComponentPopupMenu(popupMenu);
    }

    // setup close behavior

    /**
     * sets up the close window behavior
     */
    private void setupWindowClosing() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
    }

    // setup gui update timer

    /**
     * creates a timer that refreshes button and status states
     */
    private void setupTimer() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateControls();
            }
        });

        timer.start();
    }

    // start greenhouse

    /**
     * starts the greenhouse using the currently opened events file
     */
    private void startGreenhouse() {
        if (currentEventsFile == null) {
            showError("open an events file first");
            return;
        }
        outputArea.setText("");
        greenhouse.clearTerminated();
        greenhouse.createEvent("Restart", 0, currentEventsFile.getPath());
        updateControls();
    }

    // restart greenhouse

    /**
     * restarts the greenhouse using the current events file
     */
    private void restartGreenhouse() {
        if (currentEventsFile == null) {
            showError("open an events file first");
            return;
        }

        outputArea.setText("");
        greenhouse.clearTerminated();
        greenhouse.createEvent("Restart", 0, currentEventsFile.getPath());
        updateControls();
    }

    // terminate greenhouse

    /**
     * prompts for a delay time and adds a terminate event
     */
    private void terminateGreenhouse() {
        String input = JOptionPane.showInputDialog(
                this,
                "enter delay time in milliseconds",
                "terminate",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) {
            return;
        }

        try {
            long delayTime = Long.parseLong(input.trim());
            greenhouse.createEvent("Terminate", delayTime);
            updateControls();
        } catch (NumberFormatException e) {
            showError("delay time must be a number");
        }
    }

    // open events file

    /**
     * opens and validates an events file chosen by the user
     */
    private void openEventsFile() {
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            if (isValidEventsFile(selectedFile)) {
                currentEventsFile = selectedFile;
                fileLabel.setText("events file " + selectedFile.getName());
                output("opened events file " + selectedFile.getPath());
            } else {
                showError("not a valid events file");
            }
        }

        updateControls();
    }

    // restore greenhouse object

    /**
     * restores a GreenhouseControls object from a dump file
     */
    private void restoreGreenhouse() {
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                ObjectInputStream input =
                        new ObjectInputStream(new FileInputStream(selectedFile));

                Object object = input.readObject();
                input.close();

                if (object instanceof GreenhouseControls) {
                    greenhouse = (GreenhouseControls) object;
                    greenhouse.setOutput(this);
                    output("restored greenhouse from " + selectedFile.getPath());
                } else {
                    showError("not a valid dumpout file");
                }

            } catch (Exception e) {
                showError("not a valid dumpout file");
            }
        }

        updateControls();
    }

    // close current window

    /**
     * closes the current window after checking for running events
     */
    private void closeWindow() {
        if (greenhouse.isRunning()) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "greenhouse is running close window",
                    "confirm close",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
        }

        windows.remove(this);
        dispose();

        if (windows.size() == 0) {
            System.exit(0);
        }
    }

    // exit application

    /**
     * exits the application after checking all open windows for running events
     */
    private void exitApplication() {
        for (GreenhouseGUI window : windows) {
            if (window.greenhouse.isRunning()) {
                int choice = JOptionPane.showConfirmDialog(
                        this,
                        "one or more greenhouses are running exit application",
                        "confirm exit",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }

                break;
            }
        }

        System.exit(0);
    }

    // check events file format

    /**
     * checks whether a file uses the expected events file format
     *
     * @param file the events file to check
     * @return true if the events file is valid otherwise false
     */
    private boolean isValidEventsFile(File file) {
        Pattern pattern = Pattern.compile("Event=\\w+,time=\\d+");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.length() > 0 && !pattern.matcher(line).matches()) {
                    reader.close();
                    return false;
                }
            }

            reader.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    // update enabled controls

    /**
     * updates button menu and status states based on the greenhouse state
     */
    private void updateControls() {
        boolean running = greenhouse.isRunning();
        boolean suspended = greenhouse.isSuspended();
        boolean hasFile = currentEventsFile != null;

        startButton.setEnabled(!running);
        restartButton.setEnabled(hasFile && !running);
        terminateButton.setEnabled(running);
        suspendButton.setEnabled(running && !suspended);
        resumeButton.setEnabled(running && suspended);

        popupStartItem.setEnabled(!running);
        popupRestartItem.setEnabled(hasFile && !running);
        popupTerminateItem.setEnabled(running);
        popupSuspendItem.setEnabled(running && !suspended);
        popupResumeItem.setEnabled(running && suspended);

        restoreItem.setEnabled(!running);

        if (running && suspended) {
            statusLabel.setText("status suspended");
        } else if (running) {
            statusLabel.setText("status running");
        } else {
            statusLabel.setText("status stopped");
        }
    }

    // show error dialog

    /**
     * displays an error message dialog
     *
     * @param message the error message to display
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // output to text area

    /**
     * appends output text to the scrollable text area
     *
     * @param text the text to display
     */
    @Override
    public void output(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                outputArea.append(text + "\n");
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            }
        });
    }
}