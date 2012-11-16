package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;

/**
 * Map objects that can moves smoothly, but nevertheless restricted to tiles
 * @author rex
 *
 */
public class MovingMapObject extends MapObject{

	private int myMovementTimePerTile;
	private int myTimePassed;
	private double myXProportion;
	private double myYProportion;
	private int myXOriginInTile;
	private int myYOriginInTile;
	private Point myDirection;
	private Point myPreviousLocation;
	
    public MovingMapObject (int id, GameManager.GameEvent event, Point coord, Image mapImage, MapMode mapMode) {
        super(id, event, coord, mapImage, mapMode);
        //need to be read in
        myMovementTimePerTile = 900;
        myXOriginInTile = 0;
        myYOriginInTile = 0;
        myTimePassed = 0;
        myDirection = new Point(0, 0);
        myPreviousLocation = getLocation();
    }
    
    public Point calcScreenDisplacement(int tileWidth, int tileHeight) {
    	myXOriginInTile = (int)(tileWidth * myXProportion);
    	myYOriginInTile = (int)(tileHeight * myYProportion);
    	//System.out.println(-myXOriginInTile + " " + -myYOriginInTile);
    	return new Point(-myXOriginInTile, -myYOriginInTile);
    }

    @Override
    public void update(int delayTime) {
    	super.update(delayTime);
    	if (isMoving()) {
    		myTimePassed += delayTime;
    	}
    	myXProportion = myDirection.x * ((double)myTimePassed / myMovementTimePerTile);
    	myYProportion = myDirection.y * ((double)myTimePassed / myMovementTimePerTile);
    	if (myTimePassed >= myMovementTimePerTile) { //stop movements
    		finishMovement();
    	}
    	calcScreenDisplacement(myTileDimensions.width, myTileDimensions.height);
    }
    
    public void setDirection(Point dir) {
    	myDirection = dir;
    	this.setMoving(true);
    }
    
    public Point getDirection() {
    	return myDirection;
    }
    
    @Override
    public void paint(Graphics g) {
    	g.drawImage(getImage(), myOffset.x - myDirection.x
				* myTileDimensions.width + myXOriginInTile, myOffset.y
				- myDirection.y * myTileDimensions.height + myYOriginInTile,
				myTileDimensions.width, myTileDimensions.height, null);
    }
    
    @Override
    public void setLocation(Point p) {
    	myPreviousLocation = getLocation();
    	super.setLocation(p);
    }
    
    /**
     * only for painting this MovingMapObject at the centre
     * @return previous location before the most recent movement
     */
    public Point getPreviousLocation() {
    	return myPreviousLocation;
    }
    
    private void finishMovement() {
    	setMoving(false);
		myTimePassed = 0;
		myXProportion = 0;
		myYProportion = 0;
		myXOriginInTile = 0;
		myYOriginInTile = 0;
		myDirection = new Point(0, 0);
		myPreviousLocation = getLocation();
    }
}
