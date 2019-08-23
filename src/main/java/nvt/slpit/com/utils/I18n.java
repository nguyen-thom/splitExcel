package nvt.slpit.com.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum I18n {

    /**
     * Components module
     */
    COMPONENT("component"),

    /*** Common module */
    COMMON("common"),

    /**
     * Customers module
     */
    SUPPORT("customers");

    private final ResourceBundle resourceBundle;
    private static final String DEFAULT_LOCATION = "i18n.";
    private final static Logger LOGGER = Logger.getLogger(I18n.class.getName());

    I18n(String bundleFile) {
        resourceBundle = ResourceBundle.getBundle(DEFAULT_LOCATION + bundleFile, new ResourceBundleControl("UTF-8"));
    }

    /**
     * Gets a string for the given key from resource bundle.
     *
     * @param key the key for the desired string
     * @return the string for the given key
     */
    public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return "err#";
        }
    }

}
