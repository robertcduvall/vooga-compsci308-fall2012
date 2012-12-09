package util.input.configurecontrollergui.util;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;


/**
 * @author Lance, Amay
 *
 */
public class Config {
    /**
     * default canvas width
     */
    public static final int FRAME_WIDTH = 900;
    /**
     * default canvas height
     */
    public static final int FRAME_HEIGHT = 500;
    
    //header stuff
    public static final int HEADER_WIDTH = FRAME_WIDTH;
    public static final int HEADER_HEIGHT = FRAME_HEIGHT/8;
    public static final int HEADER_X_LOC = 0;
    public static final int HEADER_Y_LOC = 0;
    public static final String GRID_IMAGE = "playStation.jpg";
    public static final int GRID_WIDTH = FRAME_WIDTH;
    public static final int GRID_HEIGHT = FRAME_HEIGHT - HEADER_HEIGHT;
    public static final int GRID_X_LOC = FRAME_WIDTH-GRID_WIDTH;
    public static final int GRID_Y_LOC = HEADER_HEIGHT;
    public static final Point GRID_IMAGE_LOCATION = new Point(0,0);
    public static final Dimension GRID_IMAGE_SIZE = new Dimension(GRID_WIDTH,GRID_HEIGHT);
    public static final int GRID_NUM_ROWS = 15;
    public static final int GRID_NUM_COLUMNS = 19;
    
    public static HashMap<Integer, String> gridMappings = new HashMap<Integer, String>();
    
    public static void initMap() {
        gridMappings.put(79, "Up Pressed");
        gridMappings.put(80, "Up Pressed");
        gridMappings.put(98, "Up Pressed");
        gridMappings.put(99, "Up Pressed");
    }
    
    public static String findButton(int num) {
        String res = gridMappings.get(num);
        if (res == null) return "";
        else return res;
    }
}
