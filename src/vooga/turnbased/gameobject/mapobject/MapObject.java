package vooga.turnbased.gameobject.mapobject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gamecore.gamemodes.MapMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.mapstrategy.MapStrategy;


/**
 * Map Object class that is extended to create objects to exist in the MapMode.
 * 
 * @author TurnBased team
 * 
 */
public class MapObject extends GameObject {
    private Dimension myTileDimension;
    private Point myCameraOrigin;
    private Point myOffset;
    private Point myLocation;
    private boolean myIsVisible;
    private MapMode myMapMode;
    private List<MapStrategy> myMapStrategies;

    /**
     * Creates the MapObject that will be used in MapMode.
     * 
     * @param allowableModes Modes that are allowed
     * @param condition GameEvent that can be passed to GameManager.
     * @param location Location of object on the map.
     * @param mapImage Image of the object.
     */
    public MapObject (Set<String> allowableModes, String condition, Point location, 
            Image mapImage) {
        super(allowableModes, condition, mapImage);
        setLocation(location);
        setVisible(true);
        myMapStrategies = new ArrayList<MapStrategy>();
    }

    /**
     * Set the mode that this object belongs to
     * @param mapMode 
     */
    public void setMapMode (MapMode mapMode) {
        myMapMode = mapMode;
    }
    /**
     * Get the dimension of the tiles
     * @return
     */
    public Dimension getTileDimension() {
        return myTileDimension;
    }
    /**
     * Get the origin of the camera window
     * @return
     */
    public Point getCameraOrigin() {
        return myCameraOrigin;
    }
    /**
     * Get the Offset
     * @return
     */
    public Point getOffset() {
        return myOffset;
    }

    /**
     * Returns MapMode in which this object exists.
     * 
     * @return myMapMode MapMode associated with this object.
     */
    public MapMode getMapMode () {
        return myMapMode;
    }

    /**
     * Sets the location of the object to the parameter.
     * 
     * @param location Point location at which the object will be located.
     */
    public void setLocation (Point location) {
        myLocation = location;
    }

    /**
     * Returns the location of the object.
     * 
     * @return myLocation Point location of the object.
     */
    public Point getLocation () {
        return myLocation;
    }

    /**
     * Change the current location by p
     * @param p the amount of change
     * @return
     */
    public Point incrementLocation (Point p) {
        int x = getLocation().x + p.x;
        int y = getLocation().y + p.y;
        return new Point(x, y);
    }

    /**
     * Sets whether the object is visible or not.
     * 
     * @param b Boolean to set visibility.
     */
    public void setVisible (boolean b) {
        myIsVisible = b;
    }

    /**
     * Checks whether the object is visible or not.
     * 
     * @return myIsVisible True if visible, false if not.
     */
    public boolean isVisible () {
        return myIsVisible;
    }

    /**
     * 
     * @param target MapObject to be interacted with.
     */
    public void interact (MapObject target) {
        if (target instanceof MapPlayerObject) {
            interactWithPlayer((MapPlayerObject)target);
        }
        else {
            for (MapStrategy strategy : myMapStrategies) {
                strategy.performStrategy(target);
            }
        }
    }
    private void interactWithPlayer(MapPlayerObject player) {
     // if conversation has started, there is no need to start again in the
        // same interact method
        boolean conversationStarted = false;
        for (MapStrategy strategy : myMapStrategies) {
            if (strategy.isDisplayable()) {
                if (conversationStarted) {
                    continue;
                }
                startConversation(player);
                conversationStarted = true;
            }
            else {
                strategy.performStrategy(player);
            }
        }
    }

    /**
     * start conversation if there are strategies to be displayed in the
     * Conversation Mode
     * 
     * @param involvedObject the object involved in the conversation with this
     *        MapObject
     */
    private void startConversation (MapObject involvedObject) {
        List<Integer> involvedSpriteIDs = new ArrayList<Integer>();
        involvedSpriteIDs.add(this.getID());
        involvedSpriteIDs.add(involvedObject.getID());
        getMapMode().flagCondition(getConditionFlag(), involvedSpriteIDs);
    }
    /**
     * Add a map strategy to the object
     * @param mapStrategy 
     */
    public void addStrategy (MapStrategy mapStrategy) {
        myMapStrategies.add(mapStrategy);
    }
    /**
     * Get the list of all displayble strategies
     * @return
     */
    public List<MapStrategy> getDisplayableStrategies () {
        List<MapStrategy> displayableStrategies = new ArrayList<MapStrategy>();
        for (MapStrategy strategy : myMapStrategies) {
            if (strategy.isDisplayable()) {
                displayableStrategies.add(strategy);
            }
        }
        return displayableStrategies;
    }

    /**
     * Updates MapObject
     */
    public void update () {
        myTileDimension = new Dimension(myMapMode.getTileDimensions());
        myCameraOrigin = new Point(myMapMode.getOrigin());
        Rectangle camera = myMapMode.getCamera();
        int xOffset = (getLocation().x - (camera.x)) * myTileDimension.width + myCameraOrigin.x;
        int yOffset = (getLocation().y - (camera.y)) * myTileDimension.height + myCameraOrigin.y;
        myOffset = new Point(xOffset, yOffset);
    }

    /**
     * Paints object.
     * 
     * @param g Graphics object.
     */
    public void paint (Graphics g) {
        paintInProportion(g, myOffset, myTileDimension, 1);
    }

    /**
     * Paint the object in proportion
     * @param g 
     * @param offset 
     * @param tileDimension 
     * @param proportion 
     */
    public void paintInProportion (Graphics g, Point offset, Dimension tileDimension,
                                      double proportion) {
        if (getImage() == null || offset == null || tileDimension == null) { return; }
        offset.x += (1 - proportion) / 2 * tileDimension.width;
        offset.y += (1 - proportion) / 2 * tileDimension.height;
        int imageWidth = (int) Math.round(tileDimension.width * proportion);
        int imageHeight = (int) Math.round(tileDimension.height * proportion);
        g.drawImage(getImage(), offset.x, offset.y, imageWidth, imageHeight, null);
    }

    @Override
    public void clear () {
        myMapMode.removeMapObject(this);
    }
    /**
     * Have the map mode that the object belongs to flag a condition
     * @param conditionName 
     * @param involvedSpriteIDs 
     */
    public void flagCondition(String conditionName, List<Integer> involvedSpriteIDs) {
        myMapMode.flagCondition(conditionName, involvedSpriteIDs);
    }
}
