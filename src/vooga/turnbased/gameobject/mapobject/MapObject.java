package vooga.turnbased.gameobject.mapobject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import vooga.turnbased.gamecore.MapMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.mapstrategy.MapStrategy;
import vooga.turnbased.gameobject.mapstrategy.NullStrategy;


/**
 * Map Object class that is extended to create objects to exist in the MapMode.
 * 
 * @author TurnBased team
 * 
 */
public class MapObject extends GameObject {
    protected Dimension myTileDimensions;
    protected Point myCameraOrigin;
    protected Point myOffset;

    private Point myLocation;
    private boolean myIsVisible;
    private MapMode myMapMode;

    private List<MapStrategy> myMapStrategies; // addition of Strategy hardcoded
                                               // right now

    /**
     * Creates the MapObject that will be used in MapMode.
     * 
     * @param id Integer ID associated with the MapObject.
     * @param event GameEvent that can be passed to GameManager.
     * @param location Location of object on the map.
     * @param mapImage Image of the object.
     * @param mapMode MapMode in which the object exists.
     */
    public MapObject (int id, String event, Point location, Image mapImage, MapMode mapMode) {
        super(id, event, mapImage);
        setLocation(location);
        setVisible(true);
        setMapMode(mapMode);
        myMapStrategies = new ArrayList<MapStrategy>();
        myMapStrategies.add(new NullStrategy(mapMode));
    }

    private void setMapMode (MapMode mapMode) {
        myMapMode = mapMode;
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

    // ...this is poorly named
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
        getMapMode().flagEvent("CONVERSATION_START", involvedSpriteIDs);
    }

    public void addStrategy (MapStrategy mapStrategy) {
        myMapStrategies.add(mapStrategy);
    }

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
     * Updates MapObject; delayTime not used.
     * 
     * @param delayTime Not used.
     */
    public void update () {
        myTileDimensions = new Dimension(myMapMode.getTileDimensions());
        myCameraOrigin = new Point(myMapMode.getOrigin());
        Rectangle camera = myMapMode.getCamera();
        int xOffset = (getLocation().x - (camera.x)) * myTileDimensions.width + myCameraOrigin.x;
        int yOffset = (getLocation().y - (camera.y)) * myTileDimensions.height + myCameraOrigin.y;
        myOffset = new Point(xOffset, yOffset);
    }

    /**
     * Paints object.
     * 
     * @param g Graphics object.
     */
    public void paint (Graphics g) {
        paintInProportion(g, myOffset, myTileDimensions, 1);
    }

    protected void paintInProportion (Graphics g, Point offset, Dimension tileDimension,
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
}
