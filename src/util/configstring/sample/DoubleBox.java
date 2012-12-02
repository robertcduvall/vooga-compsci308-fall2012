package util.configstring.sample;

import java.util.Map;
import util.configstring.ConfigStringParser;

/**
 * A box holding a Double as a member variable.
 * @author Niel
 *
 */
public class DoubleBox extends Box {
    private static String DOUBLE_TAG = "double";
    
    private double myDouble;
    
    /**
     * Constructor taking a config string. The config string must contain parameter tags
     * "width" and "height" with integer values, and "double" with a double value.
     * @param configStr
     */
    public DoubleBox (String configStr) {
        super(configStr);
        Map<String, String> configMap = ConfigStringParser.parseConfigString(configStr);
        myDouble = Double.parseDouble(configMap.get(DOUBLE_TAG));
    }

    @Override
    public void printContents () {
        System.out.println("contents: " + myDouble);
    }

}
