package games.platformerdemo;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import vooga.platformer.gameobject.GameObject;


/**
 * 
 * @author Grant Oakley (modified)
 * 
 */
public class Brick extends GameObject {
    private Image myImg;

    public Brick (String configString) {
        super(configString);
        String imagePath = parseConfigString(configString).get(DEFAULT_IMAGE_TAG);

        try {
            myImg = ImageIO.read(new File(imagePath));
        }
        catch (IOException e) {
            System.out.println("No file found.");
            e.printStackTrace();
        }
    }

    @Override
    public Image getCurrentImage () {
        return myImg;
    }
}
