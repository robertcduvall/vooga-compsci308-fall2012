package vooga.platformer.level.levelplugin;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.camera.Camera;
import util.configstring.ConfigStringException;
import util.configstring.ConfigStringParser;
import util.reflection.Reflection;
import vooga.platformer.gameobject.GameObject;

/**
 * A plugin that paints a message, consisting of a variable number of lines, to the screen
 * starting at the given x and y position and with the given vertical spacing. Paints the message
 * on top of everything else.
 * @author Niel Lebeck
 *
 */
public class FixedMessagePainter extends LevelPlugin {
    protected static final String MESSAGES_TAG = "messages";
    protected static final String X_TAG = "x";
    protected static final String Y_TAG = "y";
    protected static final String SPACE_TAG = "space";

    private Color myColor;
    private List<String> myMessages;
    private int myX;
    private int myY;
    private int mySpace;
    
    /**
     * 
     */
    public FixedMessagePainter() {
        
    }
     /**
      * 
      * @param configString config string
      */
    public FixedMessagePainter(String configString) {
        Map<String, String> configMap = ConfigStringParser.parseConfigString(configString);
        try {
            myMessages = ConfigStringParser.extractMultipleEntries(configMap.get(MESSAGES_TAG));
        }
        catch(ConfigStringException e) {
            System.out.println("FixedMessagePainter: config string formatted improperly: "
                    + e.getMessage());
            System.exit(0);
        }
        myX = Integer.parseInt(configMap.get(X_TAG));
        myY = Integer.parseInt(configMap.get(Y_TAG));
        mySpace = Integer.parseInt(configMap.get(SPACE_TAG));
        
        myColor = Color.BLACK;
    }
    
    @Override
    public void update (List<GameObject> objectList) {
        
    }

    @Override
    public void paint (Graphics pen, List<GameObject> objectList, Camera cam) {
        pen.setColor(myColor);
        for (int i = 0; i < myMessages.size(); i++) {
            String str = myMessages.get(i);
            int yOffset = mySpace * i;
            pen.drawString(str, myX, myY + yOffset);
        }
    }

    @Override
    public Map<String, String> getConfigStringParams () {
        Map<String, String> configMap = new HashMap<String, String>();
        configMap.put(MESSAGES_TAG, "{line1,line2,line3}" );
        configMap.put(X_TAG, "0");
        configMap.put(Y_TAG, "0");
        configMap.put(SPACE_TAG, "15");
        return null;
    }

    @Override
    public int getPriority () {
        return MAX_PRIORITY;
    }

}
