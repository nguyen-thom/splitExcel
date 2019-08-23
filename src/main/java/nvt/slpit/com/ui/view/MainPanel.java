package nvt.slpit.com.ui.view;

import lombok.extern.slf4j.Slf4j;
import nvt.slpit.com.ui.listerner.ChangeContentPanelListerner;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class MainPanel extends JDesktopPane implements ChangeContentPanelListerner {
    private MenuApp menuBar;
    private SupportSplitPanel supportSplitPanel;
    private AboutPanel aboutPanel;
    private LeftPanel leftPanel;
    private BottomPanel bottomPanel;
    private TopPanel topPanel;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainPanel() {
        initGUI();
    }

    private void initGUI() {
        this.setLayout(new BorderLayout());
        leftPanel = new LeftPanel(this);
        bottomPanel = new BottomPanel();
        supportSplitPanel = new SupportSplitPanel();
        aboutPanel = new AboutPanel();
        cardLayout = new CardLayout();
        topPanel = new TopPanel();
        this.add(topPanel, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(bottomPanel, BorderLayout.SOUTH);

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);
        this.add(cardPanel, BorderLayout.CENTER);
        cardPanel.add(aboutPanel, "about");
        cardPanel.add(supportSplitPanel, "xmhd");
    }

    @Override
    public void changePanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }
}
