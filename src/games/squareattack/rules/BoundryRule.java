package games.squareattack.rules;

import games.squareattack.sprites.Sprite;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;


/**
 * 
 * @author Ben Schwab
 * 
 */
public class BoundryRule implements GameRule {
    private List<Sprite> mySprites;
    private int myLeft;
    private int myRight;
    private int myTop;
    private int myBottom;

    public BoundryRule (Rectangle boundry, List<Sprite> boundryChecks) {
        myLeft = boundry.x;
        myTop = boundry.y;
        myBottom = boundry.y + boundry.height;
        myRight = boundry.x + boundry.width;
        mySprites = boundryChecks;
        //System.out.println(myLeft+" "+myTop+" "+myBottom+" "+myRight);
    }

    @Override
    public void checkRule () {
        for (Sprite s : mySprites) {
            Rectangle bounds = s.getBounds();
            int left = bounds.x;
            int top = bounds.y;

            if (bounds.x < myLeft) {
                left = myLeft;
            }
            if (bounds.y < myTop) {
                top = myTop;
            }
            if (bounds.y + bounds.height > myBottom) {
                top = myBottom - bounds.height;
            }
            if (bounds.x + bounds.width > myRight) {
                left = myRight - bounds.width;
            }
            //Rectangle newBounds = new Rectangle(left, top, bounds.width, bounds.height);
           // System.out.println(newBounds.toString());
            //s.setBounds(newBounds);
            s.setCorner(new Point(left,top));

        }

    }

}
