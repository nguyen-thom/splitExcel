package nvt.slpit.com.ui.view;

import nvt.slpit.com.ui.listerner.ChangeContentPanelListerner;
import nvt.slpit.com.utils.I18n;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LeftPanel extends JPanel {

    private JButton function1;
    private JButton function2;
    private JButton function3;
    private ChangeContentPanelListerner changeContentPanelListerner;

    public LeftPanel(ChangeContentPanelListerner changeContentPanelListerner) {
        super();
        initGUI();
        this.changeContentPanelListerner = changeContentPanelListerner;
    }

    public void initGUI() {
        function1 = new JButton();
        function1.setText(I18n.SUPPORT.getString("leftMenu.xmhd.button"));
        function2 = new JButton(I18n.SUPPORT.getString("leftMenu.mail.button"));
        function3 = new JButton(I18n.SUPPORT.getString("leftMenu.setting.button"));
        function1.setMinimumSize(new java.awt.Dimension(90, 50));
        function1.setPreferredSize(new java.awt.Dimension(200, 50));
        function2.setMinimumSize(new java.awt.Dimension(90, 50));
        function2.setPreferredSize(new java.awt.Dimension(200, 50));
        function3.setMinimumSize(new java.awt.Dimension(90, 50));
        function3.setPreferredSize(new java.awt.Dimension(200, 50));

        function1.addActionListener((e) -> onChangeXMHDPanel());
        function2.addActionListener(e -> onChangeMailPanel());
        function3.addActionListener(e -> onChangeSettingPanel());


        Border line = new LineBorder(Color.BLACK);
        //Border margin = new EmptyBorder(1, 5, 2, 5);
        //Border compound = new CompoundBorder(line, margin);

        function1.setForeground(Color.BLACK);
        function1.setBackground(Color.WHITE);
        function1.setOpaque(false);
        //function1.setBorder(compound);

        function2.setForeground(Color.BLACK);
        function2.setBackground(Color.WHITE);
        //function2.setBorder(compound);
        //function2.setOpaque(false);

        function3.setForeground(Color.BLACK);
        function3.setBackground(Color.WHITE);
        //function3.setBorder(compound);
        //function3.setOpaque(false);

        JPanel jpanel = new JPanel();
        this.setLayout(new BorderLayout());

        jpanel.setLayout(new GridLayout(3, 1, 0, 0));
        jpanel.add(function1);
        jpanel.add(function2);
        jpanel.add(function3);

        this.add(jpanel, BorderLayout.NORTH);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));
    }

    protected ImageIcon createImageIcon(String path,
                                        String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void onChangeXMHDPanel() {
        changeContentPanelListerner.changePanel("xmhd");
    }

    public void onChangeSettingPanel() {
        changeContentPanelListerner.changePanel("about");
    }

    public void onChangeMailPanel() {
        changeContentPanelListerner.changePanel("about");
    }

}
