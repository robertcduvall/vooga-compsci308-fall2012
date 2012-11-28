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

    private static final double SIZE_RELATIVE_TO_TILE = 1;
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
    public MovingMapObject (int id, String event, Point location, Image mapImage, MapMode mapMode) {
        super(id, event, location, mapImage, mapMode);
        // need to be read in
        myMovementTimePerTile = 600;
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
     * Sets direction
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
        Point offset = new Point(myOffset);
        if (isMoving()) {
            offset.x = myOffset.x - myDirection.x * myTileDimensions.width + myXOriginInTile;
            offset.y = myOffset.y - myDirection.y * myTileDimensions.height + myYOriginInTile;
        }
        paintInProportion(g, offset, myTileDimensions, SIZE_RELATIVE_TO_TILE);
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

    public void finishMovement () {
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
     * 
     * @return if the MapObject can move
     */
    public boolean canMove () {
        return myCanMove;
    }

    /**
     * Sets whether the object can move in this turn.
     * Obstacles will set it to false in interact()
     * 
     * @param b Boolean to set state of "can move".
     */
    public void setCanMove (boolean b) {
        myCanMove = b;
    }

    /**
     * Sets whether the object is moving or not.
     * 
     * @param b Boolean to set state of "moving".
     */
    public void setMoving (boolean b) {
        myIsMoving = b;
    }

    /**
     * Checks whether the object is moving or not.
     * 
     * @return myIsMoving True if moving, false if not.
     */
    public boolean isMoving () {
        return myIsMoving;
    }

    public void tryMove (Point dir) {
        if (isMoving()) { return; }
        setDirection(dir); // direction changed even if not going to move
        Point dest = IncrementLocation(dir);
        if (getMapMode().isWithinBounds(dest)) {
            for (MapObject m : getMapMode().getSpritesOnTile(dest.x, dest.y)) {
                m.interact(this);
                interact(m);
            }
            if (canMove()) {
                moveTo(dest);
            }
            else {
                setCanMove(true); // reset for the next round of movement
            }
        }
    }

    private void moveTo (Point dest) {
        getMapMode().removeMapObject(this);
        getMapMode().addMapObject(dest, this);
        setLocation(dest);
        setMoving(true);
    }

    public void moveUp () {
        tryMove(MapMode.UP);
    }

    public void moveDown () {
        tryMove(MapMode.DOWN);
    }

    public void moveLeft () {
        tryMove(MapMode.LEFT);
    }

    public void moveRight () {
        tryMove(MapMode.RIGHT);
    }
}
