package util.resources;

import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;


/**
 * A class that represents a global ResourceBundle.
 * 
 * Note, currently supports only one active set of resources at a time.
 * 
 * Note, assumes resource files are in a separate folder called "resources".
 * 
 * @author Wutichai Chongchitmate
 * @author Robert C. Duvall
 */
public class ResourceManager {
    private static final String RESOURCES = "resources.";
    private static final String DEFAULT_LANGUAGE = "English";
    private static ResourceBundle ourResources;
    static {
        setResource(DEFAULT_LANGUAGE);
    }

    /**
     * Returns given value of given key as a boolean value.
     */
    public static boolean getBoolean (String key) {
        return getString(key).equalsIgnoreCase("true");
    }

    /**
     * Returns given value of given key as an int value.
     */
    public static int getInteger (String key) {
        return Integer.parseInt(getString(key));
    }

    /**
     * Returns given value of given key as a double value.
     */
    public static double getDouble (String key) {
        return Double.parseDouble(getString(key));
    }

    /**
     * Returns given value of given key as a string value.
     */
    public static String getString (String key) {
        return ourResources.getString(key);
    }

    /**
     * Returns given value of given key as a formatted string,
     * with the given values filling in the formatted values.
     */
    public static String getFormattedString (String key, Object ... values) {
        return String.format(ourResources.getString(key), values);
    }

    /**
     * Iterate through all resources in this bundle.
     */
    public static Iterator<String> iterator () {
        // Introduced in Java 1.6
        // Set<String> results = ourResources.keySet();
        // Java 1.5 code to produce an iterator
        Set<String> results = new TreeSet<String>();
        Enumeration<String> e = ourResources.getKeys();
        while (e.hasMoreElements()) {
            results.add(e.nextElement());
        }
        return results.iterator();
    }

    /**
     * Change the resource file this bundle represents to the one named.
     */
    public static boolean setResource (String language) {
        try {
            ourResources = ResourceBundle.getBundle(RESOURCES + language);
            return true;
        }
        catch (MissingResourceException e) {
            return false;
        }
    }

    /**
     * Change the resource file this bundle represents to one named
     * after the given object.
     */
    public static boolean setResource (Object target) {
        return setResource(getClassName(target));
    }

    /**
     * Get a file name based on the location of the given object.
     */
    public static URL getFile (Object root, String path) {
        return root.getClass().getResource(path);
    }

    /**
     * Returns the name of the class to use in determining which resource
     * file to use.
     */
    protected static String getClassName (Object target) {
        return target.getClass().getSimpleName();
    }
}
