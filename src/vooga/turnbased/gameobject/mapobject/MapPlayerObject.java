package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import util.graphicprocessing.ImageLoop;
import vooga.turnbased.gamecore.gamemodes.MapMode;
import vooga.turnbased.gui.GameWindow;


/**
 * Player object controlled by the user when navigating a map.
 * 
 * @author volodymyr, Rex
 * 
 */
public class MapPlayerObject extends MapMovingObject {
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final int ANIMATION_FRAME_RATE = 3;
    private Map<String, Image> myImages;
    private Map<String, ImageLoop> myImageLoops;
    private String myDownLabel = "down";
    private String myUpLabel = "up";
    private String myLeftLabel = "left";
    private String myRightLabel = "right";
    private int myFrameCount;

    /**
     * Creates a MapPlayerObject with a series of images instead of a single
     * image.
     * 
     * @param allowableModes
     * @param condition GameEvent that can be passed to GameManager.
     * @param coord Point at which object is located.
     * @param mapImages Map of strings to images that will be used for
     *        animation of the player object.
     */
    public MapPlayerObject (Set<String> allowableModes, String condition,
            Point coord, Map<String, Image> mapImages) {
        super(allowableModes, condition, coord, mapImages.get(0));
        myImages = mapImages;
        setImage(mapImages.get(myDownLabel));
    }

    public MapPlayerObject (Set<String> allowableModes, String condition,
            Image image, List<String> additionalParams) {
        super(allowableModes, condition, new Point(
                Integer.parseInt(additionalParams.get(0)),
                Integer.parseInt(additionalParams.get(1))), null);
        myImages = new HashMap<String, Image>();
        for (int i = 3; i < additionalParams.size(); i += 2) {
            ImageIcon imageIcon = new ImageIcon(additionalParams.get(i - 1));
            myImages.put(additionalParams.get(i), imageIcon.getImage());
        }
        myImageLoops = parseImageLoops(myImages);
        setImage(myImages.get(myDownLabel));
    }

    private Map<String, ImageLoop> parseImageLoops (Map<String, Image> map) {
        Map<String, ImageLoop> imageLoops = new HashMap<String, ImageLoop>();
        List<Image> leftList = parseImageList(LEFT, map);
        List<Image> rightList = parseImageList(RIGHT, map);
        List<Image> upList = parseImageList(UP, map);
        List<Image> downList = parseImageList(DOWN, map);
        imageLoops.put(LEFT, new ImageLoop(leftList));
        imageLoops.put(RIGHT, new ImageLoop(rightList));
        imageLoops.put(UP, new ImageLoop(upList));
        imageLoops.put(DOWN, new ImageLoop(downList));
        return imageLoops;
    }

    /**
     * Gets a desired subset of the user's images
     * 
     * @param direction - descriptor of the image
     * @param map - maps images and their names
     * @return a list of images
     */
    private List<Image> parseImageList (String direction, Map<String, Image> map) {
        List<Image> list = new ArrayList<Image>();
        for (String key : map.keySet()) {
            if (key.contains(direction)) {
                list.add(map.get(key));
            }
        }
        return list;
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