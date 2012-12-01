package util.configstring.sample;

import java.util.Map;
import util.configstring.ConfigStringParser;

/**
 * A box holding a String as a member variable.
 * @author Niel
 *
 */
public class StringBox extends Box {
    private static String STRING_TAG = "string";
    
    private String myString;
    
    /**
     * Constructor taking a config string. The config string must contain parameter tags
     * "width" and "height" with integer values, and "string" with a string value.
     * @param configStr
     */
    public StringBox (String configStr) {
        super(configStr);
        Map<String, String> configMap = ConfigStringParser.parseConfigString(configStr);
        myString = configMap.get(STRING_TAG);
    }

    @Override
    public void printContents () {
        System.out.println("contents: " + myString);
    }

}