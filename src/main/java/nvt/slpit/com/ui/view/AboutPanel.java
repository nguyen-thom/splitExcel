package nvt.slpit.com.ui.view;

import nvt.slpit.com.utils.I18n;

import javax.swing.*;
import java.awt.*;

public class AboutPanel extends JPanel {

    private JLabel copyrightLabel;

    public AboutPanel() {
        initGUI();
    }

    public void initGUI() {
        setLayout(new BorderLayout());
        copyrightLabel = new JLabel(I18n.SUPPORT.getString("app.menu.not.implement"));
        this.add(copyrightLabel, BorderLayout.CENTER);
    }
}
