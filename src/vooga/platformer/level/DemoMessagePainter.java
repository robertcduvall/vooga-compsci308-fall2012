package vooga.platformer.level;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;
import util.camera.Camera;
import vooga.platformer.gameobject.GameObject;

/**
 * 
 * @author unknown--Yaqi?
 * @author Niel refactored this code out of Controller into a LevelPlugin
 *
 */
public class DemoMessagePainter extends FixedMessagePainter {

    public DemoMessagePainter() {
        
    }
    
    public DemoMessagePainter(String configString) {
        super("color=Black, messages={M - Menu,Space - Shoot}, x=500, y=500, space=15");
    }

    /*public void paint (Graphics pen, Iterable<GameObject> objList, Camera cam) {
        pen.setColor(Color.BLACK);
        pen.drawString("M - Menu", (int)cam.getBounds().getWidth()*3/5, (int)cam.getBounds().getHeight()/4);
        pen.drawString("¡û ¡ú - Move left and right", (int)cam.getBounds().getWidth()*3/5, (int)cam.getBounds().getHeight()/4+15);
        pen.drawString("¡ü - Jump", (int)cam.getBounds().getWidth()*3/5, (int)cam.getBounds().getHeight()/4+30);
        pen.drawString("Space - Shoot", (int)cam.getBounds().getWidth()*3/5, (int)cam.getBounds().getHeight()/4+45);        
    }*/

    @Override
    public int getPriority () {
        return MAX_PRIORITY;
    }

}
