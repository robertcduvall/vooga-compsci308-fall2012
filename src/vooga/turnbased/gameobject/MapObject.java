package vooga.turnbased.gameobject;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class MapObject extends GameObject {
    private Point myLocation;
    private boolean myIsVisible;
    private boolean myIsMoving;
    private Image myImage;
    private Rectangle myCamera;
    
    public MapObject (int id, Point location, Image mapImage, Rectangle camera) {
        super(id);
        setLocation(location);
        setVisible(true);
        setMapImage(mapImage);
        myCamera = camera;
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
    
    public void setMapImage(Image img) {
        myImage = img;
    }
    
    public Image getMapImage() {
        return myImage;
    }
    
    public void interact() {
        
    }
}
