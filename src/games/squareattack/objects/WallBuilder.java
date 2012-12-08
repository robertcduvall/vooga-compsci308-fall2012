package games.squareattack.objects;

import games.squareattack.gui.PowerBar;
import games.squareattack.sprites.SmallSquare;
import games.squareattack.sprites.Square;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import util.input.android.events.LineSegment;
import util.mathvector.MathVector2D;
/**
 * 
 * @author Ben Schwab
 *
 */
public class WallBuilder {

    public List<SmallSquare> myWallSegments;
    private int boardHeight;
    private int boardWidth;
    private Color myColor;
    private PowerBar myPowerBar;

    public WallBuilder(int width, int height, Color wallColor, PowerBar powerBar){
        myWallSegments = new ArrayList<SmallSquare>();
        boardHeight = height;
        boardWidth = width;
        myColor = wallColor;
        myPowerBar = powerBar;
    }

    public void buildWall(LineSegment l){
        int startX = (int) (l.getRelativeStartX()*boardWidth);
        int startY = (int) (l.getRelativeStartY()*boardHeight);
        int endX = (int) (l.getRelativeEndX()*boardWidth);
        int endY = (int) (l.getRelativeEndY()*boardHeight);
        Point start = new Point(startX, startY);
        Point end = new Point(endX, endY);
        double dist = start.distance(end);
        int numSquares = (int) (dist/10);
        int xChange = (startX-endX)/numSquares;
        int yChange = (startY-endY)/numSquares;
        int x = startX;
        int y = startY;
        for(int i=0; i<numSquares; i++){
            SmallSquare s = new SmallSquare(myColor);
            s.setCorner(new Point(x,y));
            s.setSize(25);
            if(myPowerBar.usePower(3)){
                myWallSegments.add(s);
                x+=xChange;
                y+=yChange;
            }
        }

    }

    public void paint(Graphics2D pen){
        for( int i = myWallSegments.size()-1; i>=0; i--){
            SmallSquare s = myWallSegments.get(i); 
            s.paint(pen);
        }
    }

    public void collide(Square collider){
        for( int i = myWallSegments.size()-1; i>=0; i--){
            System.out.println("checkingCollision");
            SmallSquare s = myWallSegments.get(i); 
            if(s.intersects(collider)){
                s.kill();
                collider.addExternalForce(new ExternalMathVector2D((MathVector2D) collider.getLastMovementVector().scale(-2), .8));
            }

        }

    }

}
