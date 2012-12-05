package vooga.turnbased.gameobject.mapobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.MapMode;
import vooga.turnbased.gui.GamePane;


/**
 * Map objects that can move smoothly, but are nevertheless restricted to tiles.
 * 
 * @author rex
 * 
 */
public class MovingMapObject extends MapObject {
    private static final double SIZE_RELATIVE_TO_TILE = 1;
    private static final double RUN_MULTIPLIER = 2;
    private static final int INITIAL_MOVEMENT_TIME = 600;
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
    private boolean myIsRunning;

    /**
     * Creates the MovingMapObject that will be used in MapMode.
     * 
     * @param allowableModes
     * @param condition GameEvent that can be passed to GameManager.
     * @param location Location of object on the map.
     * @param mapImage Image of the object.
     */
    public MovingMapObject (Set<String> allowableModes, String condition, Point location,
                            Image mapImage) {
        super(allowableModes, condition, location, mapImage);
        myMovementTimePerTile = INITIAL_MOVEMENT_TIME;
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
        calcScreenDisplacement(getTileDimension().width, getTileDimension().height);
    }

    /**
     * Sets direction
     * 
     * @param dir direction the object is currently facing.
     */
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
        Point offset = new Point(getOffset());
        if (isMoving()) {
            offset.x = getOffset().x - myDirection.x * getTileDimension().width + myXOriginInTile;
            offset.y = getOffset().y - myDirection.y * getTileDimension().height + myYOriginInTile;
        }
        paintInProportion(g, offset, getTileDimension(), SIZE_RELATIVE_TO_TILE);
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

    /**
     * Reset all the movement related variables
     */
    public void finishMovement () {
        setMoving(false);
        myTimePassed = 0;
        myXProportion = 0;
        myYProportion = 0;
        myXOriginInTile = 0;
        myYOriginInTile = 0;
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

    /**
     * 
     * @param dir the direction of movement
     * @return if the move was successful
     */
    public boolean tryMove (Point dir) {
        if (isMoving()) { return false; }
        // direction changes even if not going to move
        setDirection(dir);
        Point dest = incrementLocation(dir);
        if (getMapMode().isWithinBounds(dest)) {
            for (MapObject m : getMapMode().getSpritesOnTile(dest.x, dest.y)) {
                m.interact(this);
                interact(m);
            }
            if (canMove()) {
                moveTo(dest);
                return true;
            }
            else {
                // reset for the next round of movement
                setCanMove(true);
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean tryMoveTo (Point dest) {
        Point direction = new Point(dest.x - getLocation().x, dest.y - getLocation().y);
        return tryMove(direction);
    }

    /**
     * carry out the movement of this MovingMapObject to destination
     * 
     * @param dest the destination on the map
     */
    private void moveTo (Point dest) {
        // getMapMode().removeMapObject(this);
        // getMapMode().addMapObject(dest, this);
        setLocation(dest);
        setMoving(true);
    }

    /**
     * Move up
     */
    public void moveUp () {
        tryMove(MapMode.UP);
    }

    /**
     * Move down
     */
    public void moveDown () {
        tryMove(MapMode.DOWN);
    }

    /**
     * Move to the left
     */
    public void moveLeft () {
        tryMove(MapMode.LEFT);
    }

    /**
     * Move to the right
     */
    public void moveRight () {
        tryMove(MapMode.RIGHT);
    }

    /**
     * Toggle running
     */
    public void toggleRunning () {
        if (myIsRunning) {
            myIsRunning = false;
            myMovementTimePerTile *= RUN_MULTIPLIER;
        }
        else {
            myIsRunning = true;
            myMovementTimePerTile /= RUN_MULTIPLIER;
        }
    }
}
