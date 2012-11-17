package vooga.turnbased.gameobject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.MapMode;

public abstract class MapObject extends GameObject {
    private Point myLocation;
    private boolean myIsVisible;
    private boolean myIsMoving;
    private MapMode myMapMode;
    protected Dimension myTileDimensions;
    protected Point myOrigin;
    protected Point myOffset;
    
    public MapObject (int id, GameManager.GameEvent event, Point location, Image mapImage, MapMode mapMode) {
        super(id, event, mapImage);
        setLocation(location);
        setVisible(true);
        setMapMode(mapMode);
    }

    private void setMapMode(MapMode mapMode) {
		myMapMode = mapMode;
	}
    
    public MapMode getMapMode(MapMode mapMode) {
		return myMapMode;
	}
    
    public void setLocation (Point location) {
        myLocation = location;
    }
    
    public Point getLocation () {
        return myLocation;
    }
    
    public Point getLocation (Point p) {
        int x = getLocation().x + p.x;
        int y = getLocation().y + p.y;
        return new Point(x,y);
    }

    public void setVisible (boolean b) {
        myIsVisible = b;
    }
    
    public boolean isVisible () {
        return myIsVisible;
    }

    public void setMoving(boolean b) {
        myIsMoving = b;
    }
    
    public boolean isMoving () {
        return myIsMoving;
    }
    
    public void interact(MapObject target) {
    }
    
    public void update(int delayTime) {
    	myTileDimensions = new Dimension(myMapMode.getTileDimensions());
    	myOrigin = new Point(myMapMode.getOrigin());
    	Rectangle camera = myMapMode.getCamera();
    	int xOffset = (getLocation().x - (camera.x)) * myTileDimensions.width
				+ myOrigin.x;
    	int yOffset = (getLocation().y - (camera.y)) * myTileDimensions.height
				+ myOrigin.y;
    	myOffset = new Point(xOffset,yOffset);
    }
    
    public void paint(Graphics g) {
    	if (getImage() == null || myOffset == null || myTileDimensions == null) {
    		return;
    	}
    	g.drawImage(getImage(), myOffset.x, myOffset.y, myTileDimensions.width, myTileDimensions.height, null);
    }
}
