package vooga.platformer.gameobject;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import util.configstring.ConfigStringParser;


/**
 * A basic GameObject object with no additional functionality
 * @author Grant Oakley (modified)
 * @author Zach Michaelov
 */
public class StaticObject extends GameObject {
    public StaticObject(String configString) {
        super(configString);
    }
    public StaticObject () {
        super();
    }
}
