package games.squareattack.sprites;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * @author Ben Schwab
 *
 */
public class WallBall extends Sprite {
    
    private int myRadius;
    private Color myColor = Color.LIGHT_GRAY;

    public WallBall (Dimension boundingBox) {
        super(boundingBox);
        myRadius = Math.min(boundingBox.height, boundingBox.width);
    }

    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(myColor);
        Rectangle bounds = getBounds();
        pen.fillOval(bounds.x, bounds.y, bounds.width, bounds.height);
    }

}
