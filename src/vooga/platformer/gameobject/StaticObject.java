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
    public StaticObject(double inX, double inY, double inWidth, double inHeight, int inId, File defaultImageFile) throws IOException {
        super(inX, inY, inWidth, inHeight, inId, defaultImageFile);
    }
    public StaticObject () {
        super();
    }
}
