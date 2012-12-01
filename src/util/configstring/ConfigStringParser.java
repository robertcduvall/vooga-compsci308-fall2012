package util.configstring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class to parse config strings. Two methods are included: one to parse a config string
 * into a Map mapping parameter tags to values, and another to extract a list of Strings from a value
 * String representing multiple entries.
 * 
 * CONFIG STRING OVERVIEW:
 * 
 * The config string approach involves having all classes in a hierarchy take one parameter in their
 * constructor: a String. This String, the config string, has the following format: key-value pairs are
 * separated by commas, with each pair being in the form key=value. Values consisting of multiple entries
 * are enclosed by brackets, and individual entries are separated by commas: e.g. key={value1,value2,value3}.
 * In general, keys correspond to member variables of the class, and values are strings holding the values
 * for the member variables. The key-value pairs replace parameters in the constructor, so the keys are
 * referred to as parameter tags and values are referred to as parameter values.
 * 
 * Each class in the hierarchy uses this config string in its constructor to initialize all of its member
 * variables. For instance, a class that would ordinarily take integer parameters representing x and y
 * positions, and a double parameter representing its damage, now takes a config string such as
 * "x=10,y=20,damage=5.0". The class is responsible for knowing which key in the config string corresponds
 * to which of its member variables. This utility class provides methods to transform the config
 * string into a String-String map, allowing the class to easily extract the information it needs.
 * 
 * The key benefit of the config string approach is that it allows a factory class to easily instantiate
 * subclasses in a class hierarchy that would otherwise take different parameters in their constructors.
 * For instance, the user could provide a class name and a config string in a level file, and the factory
 * could instantiate the classes in a manner that is both extensible and clean.
 * 
 * See the example Box hierarchy and BoxFactory class for a demonstration. Note that the factory code is
 * very clean and highly extensible, due to the config string approach. Also, note that the Box class
 * and its subclasses have relatively constructors, as this ConfigStringParser utility class handles most
 * of the parsing work.
 * 
 * @author Niel Lebeck
 *
 */
public final class ConfigStringParser {
    private ConfigStringParser() {
        
    }
    
    /**
     * Parse a config string into a map, where keys are the parameter tags and values
     * are the parameter values.
     * @param configString config string
     * @return a Map mapping parameter tags to their values
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
    
    /**
     * Transform a parameter value consisting of multiple entries into a list containing
     * those entries.
     * @param argString a string containing a multi-entry parameter value, with {} brackets enclosing
     * it and entries separated by commas
     * @return a list containing the individual entries
     * @throws ConfigStringException
     */
    public static List<String> extractMultipleEntries (String argString)
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
