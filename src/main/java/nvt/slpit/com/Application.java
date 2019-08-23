package nvt.slpit.com;

import nvt.slpit.com.ui.view.MainPanel;
import nvt.slpit.com.utils.I18n;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    private static JDialog loadingDialog;
    private static JLabel loadingProgress;

    public Application(String title) {
        super("Dummy application");

        dummyProgressUpdate("Loading content...", 2000);

        MainPanel mainPanel = new MainPanel();
        getContentPane().add(mainPanel);
        this.setTitle(title);
        // setJMenuBar(new MenuApp());

        dummyProgressUpdate("Loading settings...", 2000);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        dummyProgressUpdate("Displaying application...", 1000);
    }

    private static void dummyProgressUpdate(final String status, final int time) {
        // Use SecondaryLoop to block execution and force loading screen update
        final SecondaryLoop loop = Toolkit.getDefaultToolkit().getSystemEventQueue().createSecondaryLoop();
        SwingUtilities.invokeLater(() -> {
            loadingProgress.setText(status);
            loop.exit();
        });
        loop.enter();

        // Perform dummy heavy operation
        dummyLoadTime(time);
    }

    private static void dummyLoadTime(final long time) {
        try {
            Thread.sleep(time);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        // Displaying loading screen from EDT first
        SwingUtilities.invokeAndWait(() -> {
            loadingDialog = new JDialog((Window) null, "Loading screen");
            loadingProgress = new JLabel("Initializing application...", JLabel.CENTER);
            loadingProgress.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            loadingDialog.getContentPane().setLayout(new BorderLayout());
            loadingDialog.getContentPane().add(loadingProgress);
            loadingDialog.setUndecorated(true);
            loadingDialog.setAlwaysOnTop(true);
            loadingDialog.setModal(false);
            loadingDialog.setSize(400, 300);
            loadingDialog.setLocationRelativeTo(null);
            loadingDialog.setVisible(true);
        });

        // Initializing and displaying application from the EDT
        SwingUtilities.invokeLater(() -> {
            final Application applicationFrame = new Application(I18n.SUPPORT.getString("app.title"));
            loadingDialog.setVisible(false);
            applicationFrame.setVisible(true);
        });
    }
}
