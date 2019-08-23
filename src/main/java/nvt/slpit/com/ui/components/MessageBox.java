package nvt.slpit.com.ui.components;

import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import nvt.slpit.com.utils.I18n;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

/**
 * Message Box Dialog.
 *
 * <p>
 * JOptionPane.showMessageDialog: only with OK button.
 *
 * <p>
 * JOptionPane.showConfirmDialog: with <code>YES_OPTION</code>,
 * <code>YES_NO_OPTION</code>, <code>YES_NO_CANCEL_OPTION</code>,
 * <code>OK_OPTION</code>, <code>OK_CANCEL_OPTION</code>.
 *
 * <p>
 * JOptionPane.showInputDialog: for input dialog with JTextField, JComboBox etc.
 *
 * <p>
 * JOptionPane.ShowOptionDialog: for dialogs with custom buttons, icon etc.
 *
 * <p>
 * MessageBox uses the JXErrorPane component for error dialogs from SwingX.
 *
 * <p>
 * <pre><code>
 *      if (MessageBox.showInfoYesNoCancel("Test Information") == JOptionPane.YES_OPTION) {
 *          MessageBox.showInfo("Yes clicked.");
 *      }
 * </code></pre>
 *
 * <p>
 * Messages can be used with html tags.
 *
 * @see JOptionPane
 * @see JXErrorPane
 *
 * @author Cem Ikta
 */
public class MessageBox {

    /**
     * MessageBox Ok button option
     */
    public static final int OK_OPTION = JOptionPane.OK_OPTION;

    /**
     * MessageBox Yes button option
     */
    public static final int YES_OPTION = JOptionPane.YES_OPTION;

    /**
     * MessageBox No button option
     */
    public static final int NO_OPTION = JOptionPane.NO_OPTION;

    /**
     * MessageBox Cancel button option
     */
    public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;

    /**
     * MessageBox Closed button option
     */
    public static final int CLOSED_OPTION = JOptionPane.CLOSED_OPTION;

    /**
     * Creates a new instance of MessageBox
     */
    public MessageBox() {
    }

    /**
     * Info MessageBox default with OK button.
     *
     * @param message string for MessageBox
     */
    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Info"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Information ConfirmDialog with OK Cancel buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.OK_OPTION or JOptionPane.CANCEL_OPTION or
     * JOptionPane.CLOSED_OPTION
     */
    public static int showInfoOkCancel(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Info"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Information ConfirmDialog with Yes No buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION or
     * JOptionPane.CLOSED_OPTION
     */
    public static int showInfoYesNo(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Info"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Information ConfirmDialog with Yes No Cancel buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION or
     * JOptionPane.CANCELD_OPTION or JOptionPane.CLOSED_OPTION
     */
    public static int showInfoYesNoCancel(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Info"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Warning MessageBox default with OK button.
     *
     * @param message string for MessageBox
     */
    public static void showWarning(String message) {
        JOptionPane.showMessageDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Warning"),
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Warning ConfirmDialog with Ok Cancel buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.OK_OPTION or JOptionPane.CANCEL_OPTION or
     * JOptionPane.CLOSED_OPTION
     */
    public static int showWarningOkCancel(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Warning"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Warning ConfirmDialog with Yes No buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION or
     * JOptionPane.CLOSED_OPTION
     */
    public static int showWarningYesNo(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Warning"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Warning ConfirmDialog with Yes No Cancel buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION or
     * JOptionPane.CANCEL_OPTION or JOptionPane.CLOSED_OPTION
     */
    public static int showWarningYesNoCancel(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Warning"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Question ConfirmDialog with Ok Cancel buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.OK_OPTION or JOptionPane.CANCEL_OPTION or
     * JOptionPane.CLOSED_OPTION
     */
    public static int showAskOkCancel(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Ask"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Question ConfirmDialog with Yes No buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION or
     * JOptionPane.CLOSED_OPTION
     */
    public static int showAskYesNo(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Ask"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Question ConfirmDialog with Yes No Cancel buttons.
     *
     * @param message string for MessageBox
     * @return JOptionPane.YES_OPTION or JOptionPane.NO_OPTION or
     * JOptionPane.CANCEL_OPTION or JOptionPane.CLOSED_OPTION
     */
    public static int showAskYesNoCancel(String message) {
        return JOptionPane.showConfirmDialog(new JFrame(),
                "<html><body>" + message + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Ask"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Error MessageDialog default with OK button without details of the
     * exception.
     *
     * @param errorMessage string for Error MessageBox
     */
    public static void showError(String errorMessage) {
        JOptionPane.showMessageDialog(new JFrame(),
                "<html><body>" + errorMessage + "</body></html>",
                I18n.COMPONENT.getString("MessageBox.Title.Error"),
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Error Message Dialog with details of the exception in JXErrorPane with Ok
     * and Details buttons.
     *
     * @param errorMessage error string for MessageBox
     * @param ex exception of error
     */
    public static void showError(String errorMessage, Exception ex) {
        ErrorInfo errorInfo = new ErrorInfo(
                I18n.COMPONENT.getString("MessageBox.Title.Error"),
                "<html><body>" + errorMessage + "</body></html>",
                null, null, ex, Level.SEVERE, null);
        JXErrorPane.showDialog(new JFrame(), errorInfo);
    }

}