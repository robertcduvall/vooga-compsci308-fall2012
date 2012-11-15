package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import javax.swing.ImageIcon;

public class MovingMapObject extends MapObject{
	
	static private boolean ourSmoothMovingOn;
	private int myMovementTimePerTile;
	private int myTimePassed;
	private double myXProportion;
	private double myYProportion;
	private int myXOriginInTile;
	private int myYOriginInTile;
	private Point myDirection;
	
    public MovingMapObject (int id, Point coord, Image mapImage) {
        super(id, coord, mapImage);
        ourSmoothMovingOn = true;
        myMovementTimePerTile = 900;
        myXOriginInTile = 0;
        myYOriginInTile = 0;
        myTimePassed = 0;
        myDirection = new Point(0, 0);
    }
    
    public Point calcScreenDisplacement(int tileWidth, int tileHeight, int delayTime) {
    	myXOriginInTile = (int)(-tileWidth * myXProportion);
    	myYOriginInTile = (int)(-tileWidth * myYProportion);
    	return new Point(myXOriginInTile, myYOriginInTile);
    }

    @Override
    public void update(int delayTime) {
    	myTimePassed += delayTime;
    	myXProportion = myDirection.x * ((double)myTimePassed / myMovementTimePerTile);
    	myYProportion = myDirection.y * ((double)myTimePassed / myMovementTimePerTile);
    	if (myTimePassed == myMovementTimePerTile) {
    		setMoving(false);
    		myTimePassed = 0;
    		myDirection = new Point(0, 0);
    	}
    }
    
    public void setDirection(Point dir) {
    	myDirection = dir;
    	//this.setMoving(true);
    }
    
    @Override
    public void handleKeyPressed(KeyEvent e) {
        
    }
    
    @Override
    public void handleKeyReleased(KeyEvent e) {
        
    }
    
    @Override
    public void paint(Graphics g, int xOffset, int yOffset, int width, int height) {
    	g.drawImage(getMapImage(), xOffset - myXOriginInTile, yOffset - myYOriginInTile, width, height, null);
    }
}
