package vooga.turnbased.gameobject.map;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import vooga.turnbased.gui.GameWindow;

public class MapSprite {
    private Point myCoordinate;
    private boolean myIsVisible;
    private boolean myIsMoving;
    private int mySpeed;
    private Image myImage;
    
    public MapSprite (Point coord) {
        myCoordinate = coord;
        myIsVisible = true;
        myImage = new ImageIcon("src/vooga/turnbased/resources/image/grass.png").getImage();
    }
    
    public void move(Point dir) {
        myCoordinate.x += dir.x;
        myCoordinate.y += dir.y;
    }
    
    public void animateMove() {
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
}
