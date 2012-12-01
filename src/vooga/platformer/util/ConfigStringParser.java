package vooga.platformer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    public static List<String> parseMultiArgEntry (String argString)
                throws ConfigStringException {
        if (!(argString.startsWith("{") && argString.endsWith("}"))) {
            throw new ConfigStringException("multi-argument string is not enclosed by {}");
        }
        String subStr = argString.substring(1, argString.length() - 1);
        String[] splitStr = subStr.split(",");
        List<String> strList = new ArrayList<String>();
        for (String s : splitStr) {
            strList.add(s);
        }
        return strList;
    }
}
