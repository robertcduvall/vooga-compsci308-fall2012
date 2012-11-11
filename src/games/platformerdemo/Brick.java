package games.platformerdemo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;

public class Brick extends GameObject{
    private Image myImg;
    public Brick (String configString){
        super(configString);
        try {
            myImg = ImageIO.read(new File("src/games/platformerdemo/brick.png"));
        }
        catch (IOException e) {
            System.out.println("no file find");
            e.printStackTrace();
        }
    }
    
    @Override
    public Image getCurrentImage() {
        return myImg;
    }
}
