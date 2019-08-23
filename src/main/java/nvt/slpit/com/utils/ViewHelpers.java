package nvt.slpit.com.utils;

import nvt.slpit.com.ui.components.MessageBox;
import org.jvnet.flamingo.common.icon.ResizableIcon;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewHelpers {

    private final static Logger LOGGER = Logger.getLogger(ViewHelpers.class.getName());

    /**
     * Default icon path for 12px.
     */
    public static final String ICONS12 = "icons12/";

    /**
     * Default icon path for 16px.
     */
    public static final String ICONS16 = "icons16/";

    /**
     * Default icon path for 22px.
     */
    public static final String ICONS22 = "icons22/";

    /**
     * Default images path.
     */
    public static final String IMAGES = "images/";

    public ViewHelpers() {
    }

    /**
     * Creates tool button
     *
     * @param action button's action
     * @return abstract button
     */
    public static AbstractButton createToolButton(Action action) {
        return createToolButton(action, false, false);
    }

    /**
     * Creates tool button
     *
     * @param action button's action
     * @param showTitle show title if true.
     * @return abstract button
     */
    public static AbstractButton createToolButton(Action action, boolean showTitle) {
        return createToolButton(action, showTitle, false);
    }

    /**
     * Creates tool button
     *
     * @param action button's action
     * @param showTitle show title if true.
     * @param titlePositionBottom title pos bottom
     * @return abstract button
     */
    public static AbstractButton createToolButton(Action action, boolean showTitle,
                                                  boolean titlePositionBottom) {

        AbstractButton btn = new JButton(action);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));

        if (!showTitle) {
            btn.setText("");
        }

        if (titlePositionBottom) {
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        if (btn.getPreferredSize().width < 50) {
            btn.setMaximumSize(new Dimension(50, 41));
            btn.setMaximumSize(new Dimension(50, 41));
        }

        return btn;
    }

    /**
     * Create resizable icon for JRibbon Command Button
     *
     * @param icon icon path
     * @return resizable icon
     */
    public static ResizableIcon createResizableIcon(final Icon icon) {
        ResizableIcon resizableIcon = new ResizableIcon() {
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();

            @Override
            public int getIconHeight() {
                return this.height;
            }

            @Override
            public int getIconWidth() {
                return this.width;
            }

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                icon.paintIcon(c, g, x, y);
            }

            @Override
            public void setDimension(Dimension newDimension) {
                this.width = newDimension.width;
                this.height = newDimension.height;
            }
        };

        return resizableIcon;
    }

    /**
     * Create Formatted Text Field with custom format.
     *
     * @param mask specified mask
     * @return FormattedTextField with mask
     */
    public static JFormattedTextField createFormatterField(String mask) {
        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter(mask);
            maskFormatter.setAllowsInvalid(true);
        } catch (ParseException ex) {
            MessageBox.showError("Mask Formatter is bad", ex);
        }

        JFormattedTextField ftfFormatterField = new JFormattedTextField(maskFormatter);
        ftfFormatterField.setValue("");

        return ftfFormatterField;
    }

    /**
     * Open default internet browser and browse the url.
     *
     * @param url url string for URI
     */
    public static void browseUrl(String url) {
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Desktop Url browse action error", ex);
        }
    }
}
