package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import vooga.turnbased.gui.GameWindow;

public class MapObject extends GameObject {
    private Point myCoordinate;
    private boolean myIsVisible;
    private boolean myIsMoving;
    private int mySpeed;
    private Image myImage;
    
    public MapObject (int id, Point coord) {
        super(id);
        myCoordinate = coord;
        myIsVisible = true;
        myImage = GameWindow.importImage("GrassImage");
    }
    
    public void animateMove() {
        myIsMoving = true;
        myIsMoving = true;
    }
    
    public void interact() {
        
    }
    
    public void setImage(Image img) {
        myImage = img;
    }
    
    public Image getImage() {
        return myImage;
    }

    public Point getCoord() {
        return myCoordinate;
    }
    
    public Point getCoord(Point dir) {
        return new Point(myCoordinate.x + dir.x, myCoordinate.y + dir.y);
    }

    public void moveTo (Point dest) {
        myCoordinate.x = dest.x;
        myCoordinate.y = dest.y;
    }
}
