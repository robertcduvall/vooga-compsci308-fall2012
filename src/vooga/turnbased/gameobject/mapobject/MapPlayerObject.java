package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.Map;
import util.imageprocessing.ImageLoop;
import vooga.turnbased.gamecore.MapMode;


/**
 * Player object controlled by the user when navigating a map.
 * 
 * @author TurnBased mapmode team
 * 
 */
public class MapPlayerObject extends MovingMapObject {

    private static final int ANIMATION_FRAME_RATE = 3;
    private Map<String, Image> myImages;
    private Map<String, ImageLoop> myImageLoops;
    // feel free to rename
    private String myDownLabel = "down";
    private String myUpLabel = "up";
    private String myLeftLabel = "left";
    private String myRightLabel = "right";
    private int myFrameCount;

    /**
     * Creates the MovingMapObject that will be used in MapMode.
     * 
     * @param id Integer ID associated with the MapObject.
     * @param event GameEvent that can be passed to GameManager.
     * @param coord Point at which object is located.
     * @param mapImage Image of the object.
     * @param mapMode MapMode in which the object exists.
     */
    public MapPlayerObject (int id, String event, Point coord, Image mapImage, MapMode mapMode) {
        super(id, event, coord, mapImage, mapMode);
        myFrameCount = 0;
    }

    /**
     * Creates a MapPlayerObject with a series of images instead of a single
     * image.
     * 
     * @param id Integer ID associated with the MapObject.
     * @param event GameEvent that can be passed to GameManager.
     * @param coord Point at which object is located.
     * @param mapImages Map of strings to images that will be used for
     *        animation of the player object.
     * @param mapMode MapMode in which the object exists.
     */
    public MapPlayerObject (int id, String event, Point coord, Map<String, Image> mapImages,
                            MapMode mapMode) {
        super(id, event, coord, mapImages.get(0), mapMode);
        myImages = mapImages;
        setImage(mapImages.get(myDownLabel));
    }

    // Dummy Constructor used for testing XML parsing with reflection
    // Need to remove Map Mode argument for reflection to work correctly
    public MapPlayerObject (int id, String event, Point coord, Map<String, Image> mapImages) {
        super(id, event, coord, mapImages.get(0), null);
        myImages = mapImages;
        setImage(mapImages.get(myDownLabel));
    }

    // for testing with Xml parsing, can be deleted later
    public Map<String, Image> getImageMap () {
        return myImages;
    }

    /**
     * Sets the map of strings to ImageLoops to the parameter.
     * 
     * @param imageLoops Map of strings to imageloops.
     */
    public void setImageLoops (Map<String, ImageLoop> imageLoops) {
        myImageLoops = imageLoops;

    }

    @Override
    public void update () {
        super.update();

        myFrameCount++;
        if (myFrameCount >= ANIMATION_FRAME_RATE) {
            animateCharactor();
            myFrameCount = 0;
        }
    }

    private void animateCharactor () {
        if (isMoving()) {
            if (getDirection().equals(MapMode.DOWN)) {
                setImage((Image) myImageLoops.get(myDownLabel).next());
            }
            else if (getDirection().equals(MapMode.LEFT)) {
                setImage((Image) myImageLoops.get(myLeftLabel).next());
            }
            else if (getDirection().equals(MapMode.UP)) {
                setImage((Image) myImageLoops.get(myUpLabel).next());
            }
            else if (getDirection().equals(MapMode.RIGHT)) {
                setImage((Image) myImageLoops.get(myRightLabel).next());
            }
        }
        else {
            if (getDirection().equals(MapMode.DOWN)) {
                setImage(myImages.get(myDownLabel));
            }
            else if (getDirection().equals(MapMode.LEFT)) {
                setImage(myImages.get(myLeftLabel));
            }
            else if (getDirection().equals(MapMode.UP)) {
                setImage(myImages.get(myUpLabel));
            }
            else if (getDirection().equals(MapMode.RIGHT)) {
                setImage(myImages.get(myRightLabel));
            }
        }
    }

}
