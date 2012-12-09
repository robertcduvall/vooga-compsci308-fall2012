package games.game.gameobject;

import games.game.core.IPaintable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;


/**
 * This class represents a health bar that is displayed
 * above all enemy sprites.
 * 
 * @author Paul Dannenberg
 * 
 */
public class HealthBar implements IPaintable {

    private static final int MAX_HEALTH_BARS = 3;
    private static final int WIDTH = 10, HEIGHT = 5;
    private final int MAXIMUM_HEALTH;
    private DamagableSprite mySprite;

    /**
     * Creates a new health bar.
     * 
     * @param initialHealth The initial amount of health this health
     *        bar should display.
     * @param sprite The sprite for which this object will act as a
     *        health bar.
     */
    public HealthBar(int initialHealth, DamagableSprite sprite) {
        MAXIMUM_HEALTH = initialHealth;
        mySprite = sprite;
    }

    /**
     * Paints the health bar.
     */
    @Override
    public void paint(Graphics2D pen) {
        int numberHealthBarsToDraw = computeNumberHealthBars(mySprite
                .getHealth());
        drawHealthBars(pen, mySprite.getLocation(), numberHealthBarsToDraw);
    }

    /**
     * Determines how many health bars should be painted, based on the sprite's
     * current health.
     * @param currentSpriteHealth The amount of health that will determine how
     * many health bars will be drawn.
     * @return The number of health bars to be drawn.
     */
    private int computeNumberHealthBars(int currentSpriteHealth) {
        return (currentSpriteHealth / MAXIMUM_HEALTH) * MAX_HEALTH_BARS;
    }

    /**
     * This method draws the actual health bars, represented as red rectangles
     * on the screen.
     * @param pen The object with which to draw.
     * @param location The position to draw the health bar.
     * @param numberHealthBars The number of health bars to draw.
     */
    private void drawHealthBars(Graphics2D pen, Point2D location,
            int numberHealthBars) {
        Point2D healthBarLocation = location;
        Color originalColor = pen.getColor();
        pen.setColor(Color.red);
        for (int i = 0; i < numberHealthBars; i++) {
            pen.drawRect((int) healthBarLocation.getX(),
                    (int) healthBarLocation.getY(), WIDTH, HEIGHT);
            healthBarLocation = new Point2D.Double(healthBarLocation.getX()
                    + WIDTH, healthBarLocation.getY() + HEIGHT);
        }
        pen.setColor(originalColor);
    }

}
