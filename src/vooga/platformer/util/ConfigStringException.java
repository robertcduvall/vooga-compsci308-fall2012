package vooga.platformer.util;

/**
 * An exception that results from errors in config string processing
 * @author Niel Lebeck
 *
 */
public class ConfigStringException extends Exception {
    
    public ConfigStringException(String str) {
        super(str);
    }
}
