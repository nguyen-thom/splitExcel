package nvt.slpit.com.ui.view;

import nvt.slpit.com.utils.I18n;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    private ImageIcon imageIcon;

    private JLabel jLabel;

    public TopPanel() {
        initGUI();

    }

    public void initGUI() {
        jLabel = new JLabel(I18n.SUPPORT.getString("top.info.label"), JLabel.LEFT);
        jLabel.setFont(new Font("Serif", Font.BOLD, 18));
        jLabel.setIcon(new ImageIcon(getClass().getResource("/images/thue_icon.png")));
        //jLabel.setMinimumSize(new java.awt.Dimension(50, 50));
        jLabel.setBackground(Color.GREEN);
        setLayout(new BorderLayout());
        add(jLabel, BorderLayout.CENTER);
        setMinimumSize(new java.awt.Dimension(500, 80));
        setPreferredSize(new java.awt.Dimension(673, 80));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    private void setupFont(JLabel label) {
        Font labelFont = label.getFont();
        String labelText = label.getText();

        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = label.getWidth();

// Find out how much the font can grow in width.
        double widthRatio = (double) componentWidth / (double) stringWidth;

        int newFontSize = (int) (labelFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();

// Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);

// Set the label's font size to the newly determined size.
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
    }
}
