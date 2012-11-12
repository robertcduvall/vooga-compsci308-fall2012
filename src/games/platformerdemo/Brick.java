package games.platformerdemo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;

/**
 * @author Yaqi
 *
 */
public class Brick extends MovingObject{
    /**
     * @param configString
     */
    public Brick (String configString){
        super(configString, "brick");
    }
}
