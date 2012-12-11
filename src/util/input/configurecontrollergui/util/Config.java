package util.input.configurecontrollergui.util;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;


/**
 * The Config file serves as a global
 * set of constants for the whole
 * configure controller GUI.
 * 
 * @author Lance, Amay
 *
 */
public class Config {

    public static final int FRAME_WIDTH = 900;
    public static final int FRAME_HEIGHT = 500;
    public static final int HEADER_WIDTH = FRAME_WIDTH;
    public static final int HEADER_HEIGHT = FRAME_HEIGHT/8;
    public static final int HEADER_X_LOC = 0;
    public static final int HEADER_Y_LOC = 0;
    public static final String GRID_IMAGE = "keyboard.png";
    public static final int GRID_WIDTH = FRAME_WIDTH;
    public static final int GRID_HEIGHT = FRAME_HEIGHT - HEADER_HEIGHT;
    public static final int GRID_X_LOC = FRAME_WIDTH-GRID_WIDTH;
    public static final int GRID_Y_LOC = HEADER_HEIGHT;
    public static final Point GRID_IMAGE_LOCATION = new Point(0,0);
    public static final Dimension GRID_IMAGE_SIZE = new Dimension(GRID_WIDTH,GRID_HEIGHT);
    public static final int GRID_NUM_ROWS = 20;
    public static final int GRID_NUM_COLUMNS = 30;
    
    public static HashMap<Integer, String> gridMappings = new HashMap<Integer, String>();
    
    public static void initMap() {
        gridMappings.put(471, "Up Pressed");
        gridMappings.put(472, "Up Pressed");
        gridMappings.put(501, "Up Pressed");
        gridMappings.put(502, "Up Pressed");
        gridMappings.put(530, "Left Pressed");
        gridMappings.put(560, "Left Pressed");
        gridMappings.put(590, "Left Pressed");
        gridMappings.put(531, "Down Pressed");
        gridMappings.put(532, "Down Pressed");
        gridMappings.put(561, "Down Pressed");
        gridMappings.put(562, "Down Pressed");
        gridMappings.put(591, "Down Pressed");
        gridMappings.put(592, "Down Pressed");
        gridMappings.put(593, "Right Pressed");
        gridMappings.put(563, "Right Pressed");
        gridMappings.put(533, "Right Pressed");
        gridMappings.put(274, "W Pressed");
        gridMappings.put(304, "W Pressed");
        gridMappings.put(303, "W Pressed");
        gridMappings.put(273, "W Pressed");
        gridMappings.put(363, "A Pressed");
        gridMappings.put(393, "A Pressed");
        gridMappings.put(392, "A Pressed");
        gridMappings.put(362, "A Pressed");
        gridMappings.put(364, "S Pressed");
        gridMappings.put(364, "S Pressed");
        gridMappings.put(365, "D Pressed");
        gridMappings.put(395, "D Pressed");
    }
    
    public static String findButton(int num) {
        String res = gridMappings.get(num);
        if (res == null) return "";
        else return res;
    }
}
