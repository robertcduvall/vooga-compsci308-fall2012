package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.util.Map;

import util.imageprocessing.ImageLoop;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;

public class MapPlayerObject extends MovingMapObject {
	
	private Map<String, Image> myImages;
	private Map<String, ImageLoop> myImageLoops;
	private ImageLoop myCurrentImageLoop;

	public MapPlayerObject (int id, GameManager.GameEvent event, Point coord, Image mapImage, MapMode mapMode) {
        super(id, event, coord, mapImage, mapMode);
    }
    
    public MapPlayerObject (int id, GameManager.GameEvent event, Point coord, 
    		Map<String, Image> mapImages, MapMode mapMode) {
    	super(id, event, coord, mapImages.get(0), mapMode);
    	myImages = mapImages;
    	setImage(mapImages.get("down"));
    }
    
    // for testing with Xml parsing, can be deleted later
    public Map<String,Image> getImageMap() {
        return myImages;
    }
    
    public void setImageLoops(Map<String, ImageLoop> imageLoops) {
    	myImageLoops = imageLoops;
    }

    @Override
    public void update (int delayTime) {
        super.update(delayTime);
        if(isMoving()) {
        	if (getDirection().equals(MapMode.DOWN)) {
	        	setImage((Image)myImageLoops.get("down").next());
	        }
	        else if (getDirection().equals(MapMode.LEFT)) {
	        	setImage((Image)myImageLoops.get("left").next());
	        }
	        else if (getDirection().equals(MapMode.UP)) {
	        	setImage((Image)myImageLoops.get("up").next());
	        }
	        else if (getDirection().equals(MapMode.RIGHT)){
	        	setImage((Image)myImageLoops.get("right").next());
	        }
        }
        else {
	        if (getDirection().equals(MapMode.DOWN)) {
	        	setImage(myImages.get("down"));
	        }
	        else if (getDirection().equals(MapMode.LEFT)) {
	        	setImage(myImages.get("left"));
	        }
	        else if (getDirection().equals(MapMode.UP)) {
	        	setImage(myImages.get("up"));
	        }
	        else if (getDirection().equals(MapMode.RIGHT)){
	        	setImage(myImages.get("right"));
	        }
        }
    }
    
}
