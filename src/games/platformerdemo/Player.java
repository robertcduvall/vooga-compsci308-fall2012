package games.platformerdemo;

import java.awt.Graphics;
import java.awt.Image;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;

public class Player extends GameObject {


    public Player (String configString) {
        super(configString);
    }

    @Override
    public void paint (Graphics pen, Camera cam) {
        
    }

    @Override
    public Image getCurrentImage () {
        // TODO Auto-generated method stub
        return null;
    }
 
}
