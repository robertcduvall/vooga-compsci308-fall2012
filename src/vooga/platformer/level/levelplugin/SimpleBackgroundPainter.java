package vooga.platformer.level.levelplugin;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import util.configstring.ConfigStringParser;
import vooga.platformer.gameobject.GameObject;

/**
 * A simple BackgroundPainter that paints a static background image on the screen.
 * @author Niel Lebeck
 *
 */
public class SimpleBackgroundPainter extends BackgroundPainter {
    protected static final String IMAGE_TAG = "image"; 
    
    private ImageIcon myBgImg;
    
    /**
     * Default constructor. To be used only for the purposes of getting the config
     * string params by calling getConfigStringParams().
     */
    public SimpleBackgroundPainter() {
        
    }
    
    /**
     * @param configString config string
     */
    public SimpleBackgroundPainter(String configString) {
        Map<String, String> configMap = ConfigStringParser.parseConfigString(configString);
        String imagePath = configMap.get(IMAGE_TAG);
        try {
            myBgImg = new ImageIcon(ImageIO.read(new File(imagePath)));
        }
        catch (IOException e) {
            System.out.println("invalid image path: " + imagePath);
        }
    }
    
    /**
     * @param bgImg background image
     */
    public SimpleBackgroundPainter(File imgFile) {
        try {
            myBgImg = new ImageIcon(ImageIO.read(imgFile));
        }
        catch (IOException e) {
            System.out.println("invalid image path: " + imgFile);
        }
    }
    
    @Override
    public Map<String, String> getConfigStringParams () {
        Map<String, String> params = new HashMap<String, String>();
        params.put(IMAGE_TAG, "path of background image");
        return params;
    }

    @Override
    public Image getCurrentBackgroundImage (List<GameObject> objList) {
        return myBgImg.getImage();
    }

    @Override
    public ImageIcon getDefaultImage () {
        return myBgImg;
    }

}
