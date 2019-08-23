package nvt.slpit.com.ui.view;

import nvt.slpit.com.utils.I18n;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {

    private JLabel copyrightLabel;

    public BottomPanel() {
        initGUI();
    }

    public void initGUI() {
        copyrightLabel = new JLabel(I18n.SUPPORT.getString("bottom.copyright.label"));
        this.add(copyrightLabel);
        setMinimumSize(new java.awt.Dimension(50, 50));
        setPreferredSize(new java.awt.Dimension(673, 50));
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    }
}
