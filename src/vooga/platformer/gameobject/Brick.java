package vooga.platformer.gameobject;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import vooga.platformer.util.ConfigStringParser;


/**
 * 
 * @author Grant Oakley (modified)
 * 
 */
public class Brick extends GameObject {
    public Brick (String configString) {
        super(configString);
    }
}
