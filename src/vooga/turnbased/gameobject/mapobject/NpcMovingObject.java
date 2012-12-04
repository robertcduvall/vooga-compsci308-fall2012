package vooga.turnbased.gameobject.mapobject;

import java.awt.Image;
import java.awt.Point;
import java.util.Map;
import java.util.Set;

import util.graphicprocessing.ImageLoop;
import vooga.turnbased.gamecore.gamemodes.MapMode;

public class NpcMovingObject extends MovingMapObject {

	private static final int ANIMATION_FRAME_RATE = 3;
	private Map<String, Image> myImages;
	private Map<String, ImageLoop> myImageLoops;
	private String myDownLabel = "down";
	private String myUpLabel = "up";
	private String myLeftLabel = "left";
	private String myRightLabel = "right";
	private int myFrameCount;
	private int counter;

	public NpcMovingObject(Set<String> allowableModes, String condition,
			Point coord, Map<String, Image> mapImages) {
		super(allowableModes, condition, coord, mapImages.get(0));
		myImages = mapImages;
		setImage(mapImages.get(myRightLabel));
		toggleRunning();
	}

	/**
	 * Sets the map of strings to ImageLoops to the parameter.
	 * 
	 * @param imageLoops
	 *            Map of strings to imageloops.
	 */
	public void setImageLoops(Map<String, ImageLoop> imageLoops) {
		myImageLoops = imageLoops;

	}

	@Override
	public void update() {
		super.update();
		counter++;
		if (counter > 10) {
			Point dest = new Point(getLocation().x + 1, getLocation().y);
			tryMove(MapMode.RIGHT);
			counter = 0;
		}
		myFrameCount++;
		if (myFrameCount >= ANIMATION_FRAME_RATE) {
			animateCharactor();
			myFrameCount = 0;
		}
	}

	private void animateCharactor() {
		if (isMoving()) {
			if (getDirection().equals(MapMode.DOWN)) {
				setImage((Image) myImageLoops.get(myDownLabel).next());
			} else if (getDirection().equals(MapMode.LEFT)) {
				setImage((Image) myImageLoops.get(myLeftLabel).next());
			} else if (getDirection().equals(MapMode.UP)) {
				setImage((Image) myImageLoops.get(myUpLabel).next());
			} else if (getDirection().equals(MapMode.RIGHT)) {
				setImage((Image) myImageLoops.get(myRightLabel).next());
			}
		} else {
			if (getDirection().equals(MapMode.DOWN)) {
				setImage(myImages.get(myDownLabel));
			} else if (getDirection().equals(MapMode.LEFT)) {
				setImage(myImages.get(myLeftLabel));
			} else if (getDirection().equals(MapMode.UP)) {
				setImage(myImages.get(myUpLabel));
			} else if (getDirection().equals(MapMode.RIGHT)) {
				setImage(myImages.get(myRightLabel));
			}
		}
	}

}
