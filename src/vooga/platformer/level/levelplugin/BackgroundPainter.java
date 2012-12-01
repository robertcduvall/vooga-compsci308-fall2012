package vooga.platformer.level.levelplugin;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.util.ConfigStringParser;

/**
 * A simple LevelPlugin that paints a background image on the screen.
 * @author Niel Lebeck
 *
 */
public class BackgroundPainter extends LevelPlugin {
    protected static final String IMAGE_TAG = "image"; 
    
    private Image myBgImg;
    
    /**
     * Default constructor. To be used only for the purposes of getting the config
     * string params by calling getConfigStringParams().
     */
    public BackgroundPainter() {
        
    }
    
    /**
     * @param configString config string
     */
    public BackgroundPainter(String configString) {
        Map<String, String> configMap = ConfigStringParser.parseConfigString(configString);
        String imagePath = configMap.get(IMAGE_TAG);
        try {
            myBgImg = ImageIO.read(new File(imagePath));
        }
        catch (IOException e) {
            System.out.println("invalid image path: " + imagePath);
        }
    }
    
    @Override
    public Map<String, String> getConfigStringParams () {
        Map<String, String> params = new HashMap<String, String>();
        params.put(IMAGE_TAG, "path of background image");
        return params;
    }
    
    
    
    @Override
    public void update (Iterable<GameObject> objCollection) {

    }

    @Override
    public void paint (Graphics pen, Iterable<GameObject> objList, Camera cam) {
        pen.drawImage(myBgImg, 0, 0, null);
    }

    /**
     * The background is the very first thing painted, so the priority is 0.
     */
    @Override
    public int getPriority () {
        return 0;
    }

}
