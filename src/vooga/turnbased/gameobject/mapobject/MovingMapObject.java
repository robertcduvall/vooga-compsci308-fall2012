package vooga.turnbased.gameobject.mapobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gui.GamePane;


/**
 * Map objects that can move smoothly, but are nevertheless restricted to tiles.
 * 
 * @author rex
 * 
 */
public class MovingMapObject extends MapObject {

    private int myMovementTimePerTile;
    private int myTimePassed;
    private double myXProportion;
    private double myYProportion;
    private int myXOriginInTile;
    private int myYOriginInTile;
    private Point myDirection;
    private Point myPreviousLocation;
    private boolean myCanMove;
    private boolean myIsMoving;

    /**
     * Creates the MovingMapObject that will be used in MapMode.
     * 
     * @param id Integer ID associated with the MovingMapObject.
     * @param event GameEvent that can be passed to GameManager.
     * @param location Location of object on the map.
     * @param mapImage Image of the object.
     * @param mapMode MapMode in which the object exists.
     */
    public MovingMapObject (int id, String event, Point location, Image mapImage,
            MapMode mapMode) {
        super(id, event, location, mapImage, mapMode);
        // need to be read in
        myMovementTimePerTile = 900;
        myXOriginInTile = 0;
        myYOriginInTile = 0;
        myTimePassed = 0;
        myDirection = new Point(0, 0);
        myPreviousLocation = getLocation();
        myCanMove = true;
    }

    /**
     * Calculates the displacement of the screen.
     * 
     * @param tileWidth Integer width of the tile.
     * @param tileHeight Integer height of the tile.
     * @return
     */
    public Point calcScreenDisplacement (int tileWidth, int tileHeight) {
        myXOriginInTile = (int) (tileWidth * myXProportion);
        myYOriginInTile = (int) (tileHeight * myYProportion);
        return new Point(-myXOriginInTile, -myYOriginInTile);
    }

    @Override
    public void update () {
        int delayTime = GamePane.getDelayTime();
        super.update();
        if (isMoving()) {
            myTimePassed += delayTime;
        }
        myXProportion = myDirection.x * ((double) myTimePassed / myMovementTimePerTile);
        myYProportion = myDirection.y * ((double) myTimePassed / myMovementTimePerTile);
        // stop movements
        if (myTimePassed >= myMovementTimePerTile) {
            finishMovement();
        }
        calcScreenDisplacement(myTileDimensions.width, myTileDimensions.height);
    }

    /**
     * Sets destination(?) of object and sets moving to true.
     * 
     * @param dir Point destination.
     */
    // this is also poorly named
    public void setDirection (Point dir) {
        myDirection = dir;
    }

    /**
     * Gets the current moving direction of the object.
     * 
     * @return the direction (left, right, up, down)
     */
    public Point getDirection () {
        return myDirection;
    }

    @Override
    /**
     * paint differently when the object is moving
     * @param g Graphics object onto which the MapObject is painted
     */
    public void paint (Graphics g) {
        if (isMoving()) {
            g.drawImage(getImage(), myOffset.x - myDirection.x * myTileDimensions.width +
                    myXOriginInTile, myOffset.y - myDirection.y * myTileDimensions.height +
                    myYOriginInTile, myTileDimensions.width, myTileDimensions.height, null);
        }
        else {
            super.paint(g);
        }

    }

    @Override
    public void setLocation (Point p) {
        myPreviousLocation = getLocation();
        super.setLocation(p);
    }

    /**
     * only for painting this MovingMapObject at the centre
     * 
     * @return previous location before the most recent movement
     */
    public Point getPreviousLocation () {
        return myPreviousLocation;
    }

    private void finishMovement () {
        setMoving(false);
        myTimePassed = 0;
        myXProportion = 0;
        myYProportion = 0;
        myXOriginInTile = 0;
        myYOriginInTile = 0;
        // myDirection = new Point(0, 0);
        myPreviousLocation = getLocation();
    }
    
    /**
     * Checks whether the object can move.
     * @return if the MapObject can move
     */
    public boolean canMove() {
    	return myCanMove;
    }
    
    /**
     * Sets whether the object can move in this turn.
     * Obstacles will set it to false in interact()
     * @param b Boolean to set state of "can move".
     */
    public void setCanMove(boolean b) {
    	myCanMove = b;
    }

    /**
     * Sets whether the object is moving or not.
     * @param b Boolean to set state of "moving".
     */
    public void setMoving (boolean b) {
        myIsMoving = b;
    }

    /**
     * Checks whether the object is moving or not.
     * @return myIsMoving True if moving, false if not.
     */
    public boolean isMoving () {
        return myIsMoving;
    }
}
