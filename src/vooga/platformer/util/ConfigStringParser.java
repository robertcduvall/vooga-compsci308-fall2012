package vooga.platformer.util;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class to parse config strings. The GameObject, LevelPlugin, and
 * Condition hierarchies take config strings as parameters for their Constructors.
 * @author Niel Lebeck
 *
 */
public final class ConfigStringParser {
    private ConfigStringParser() {
        
    }
    
    /**
     * Parse a config string into a map of keys (parameter tags) and values
     * (parameter values).
     * @param configString config string
     * @return
     */
    public static Map<String, String> parseConfigString (String configString) {
        Map<String, String> configMap = new HashMap<String, String>();
        String[] pairs = configString.split(",");
        for (String entry : pairs) {
            String[] entrySplit = entry.split("=");
            configMap.put(entrySplit[0], entrySplit[1]);
        }
        return configMap;
    }
}
