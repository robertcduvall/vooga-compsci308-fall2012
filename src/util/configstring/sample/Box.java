package util.configstring.sample;

import java.util.Map;
import util.configstring.ConfigStringParser;

/**
 * Config string sample class. An abstract box. All boxes have a width and a height,
 * but they can hold different types of contents depending on the subclass. As a result,
 * the subclasses will have different types of member variables that must be initialized in the
 * constructor. Because they take config strings in their constructor, all Boxes have identical
 * constructors, making their instantiation clean and extensible.
 * @author Niel
 *
 */

public abstract class Box {
    private static final String WIDTH_TAG = "width";
    private static final String HEIGHT_TAG = "height";

    
    private int myBoxWidth;
    private int myBoxHeight;
    
    /**
     * Constructor taking a config string. The config string must contain parameter tags
     * "width" and "height" with integer values.
     * @param configStr
     */
    public Box(String configStr) {
        Map<String, String> configMap = ConfigStringParser.parseConfigString(configStr);
        myBoxWidth = Integer.parseInt(configMap.get(WIDTH_TAG));
        myBoxHeight = Integer.parseInt(configMap.get(HEIGHT_TAG));
    }
    
    /**
     * Print the dimensions of the box.
     */
    public void printDimensions() {
        System.out.println("width: " + myBoxWidth + " height: " + myBoxHeight);
    }
    
    /**
     * Print the contents of the box. The contents may be of different types, depending on
     * the box subclass.
     */
    public abstract void printContents();
}
