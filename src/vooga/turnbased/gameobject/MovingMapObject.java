package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class MovingMapObject extends MapObject {
	
	static private boolean smoothMovingOn;
	
    public MovingMapObject (int id, Point coord, Image mapImage, Rectangle camera) {
        super(id, coord, mapImage, camera);
        smoothMovingOn = true;
    }
    
    public void update() {
    	
    }
    
    @Override
    public void handleKeyPressed(KeyEvent e) {
        
    }
    
    @Override
    public void handleKeyReleased(KeyEvent e) {
        
    }
}
