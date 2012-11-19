package vooga.turnbased.gameobject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import vooga.turnbased.gamecorE.MapMode;


/**
 * Abstract class that is extended to create objects to exist in the MapMode.
 * 
 * @author TurnBased team
 * 
 */
public abstract class MapObject extends GameObject {
    // checkstyle does not like that these are protected
    protected Dimension myTileDimensions;
    protected Point myCameraOrigin;
    protected Point myOffset;

    private Point myLocation;
    private boolean myIsVisible;
    private boolean myIsMoving;
    private MapMode myMapMode;

    /**
     * Creates the MapObject that will be used in MapMode.
     * @param id Integer ID associated with the MapObject.
     * @param event GameEvent that can be passed to GameManager.
     * @param location Location of object on the map.
     * @param mapImage Image of the object.
     * @param mapMode MapMode in which the object exists.
     */
    public MapObject (int id, String event, Point location, Image mapImage,
            MapMode mapMode) {
        super(id, event, mapImage);
        setLocation(location);
        setVisible(true);
        setMapMode(mapMode);
    }

    private void setMapMode (MapMode mapMode) {
        myMapMode = mapMode;
    }

    /**
     * Returns MapMode in which this object exists.
     * @return myMapMode MapMode associated with this object.
     */
    public MapMode getMapMode () {
        return myMapMode;
    }

    /**
     * Sets the location of the object to the parameter.
     * @param location Point location at which the object will be located.
     */
    public void setLocation (Point location) {
        myLocation = location;
    }

    /**
     * Returns the location of the object.
     * @return myLocation Point location of the object.
     */
    public Point getLocation () {
        return myLocation;
    }

    // ...this is poorly named
    public Point getLocation (Point p) {
        int x = getLocation().x + p.x;
        int y = getLocation().y + p.y;
        return new Point(x, y);
    }

    /**
     * Sets whether the object is visible or not.
     * @param b Boolean to set visibility.
     */
    public void setVisible (boolean b) {
        myIsVisible = b;
    }

    /**
     * Checks whether the object is visible or not. 
     * @return myIsVisible True if visible, false if not.
     */
    public boolean isVisible () {
        return myIsVisible;
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

    /**
     * 
     * @param target MapObject to be interacted with.
     */
    public void interact (MapObject target) {
    }

    /**
     * Updates MapObject; delayTime not used.
     * @param delayTime Not used.
     */
    public void update (int delayTime) {
        myTileDimensions = new Dimension(myMapMode.getTileDimensions());
        myCameraOrigin = new Point(myMapMode.getOrigin());
        Rectangle camera = myMapMode.getCamera();
        int xOffset = (getLocation().x - (camera.x)) * myTileDimensions.width + myCameraOrigin.x;
        int yOffset = (getLocation().y - (camera.y)) * myTileDimensions.height + myCameraOrigin.y;
        myOffset = new Point(xOffset, yOffset);
    }

    /**
     * Paints object.
     * @param g Graphics object.
     */
    public void paint (Graphics g) {
        if (getImage() == null || myOffset == null || myTileDimensions == null) { return; }
        g.drawImage(getImage(), myOffset.x, myOffset.y, myTileDimensions.width,
                myTileDimensions.height, null);
    }
}
