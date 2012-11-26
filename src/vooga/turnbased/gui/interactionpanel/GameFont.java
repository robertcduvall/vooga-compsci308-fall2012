package vooga.turnbased.gui.interactionpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 * this class is to paint strings with different effects and in more beautiful ways
 * Graphics object, string content, positions are needed for painting these effects
 * Inspired after reading this article: http://www.javaworld.com/javaworld/javatips/jw-javatip81.html
 * 
 * @author rex
 *
 */
public class GameFont {
    
    private Graphics myGraphics;
    private Font myFont;
    
    /**
     * Constructor
     * @param g the Graphics object it paints to
     * @param font the Font of all text
     */
    public GameFont (Graphics g, Font font) {
        myGraphics = g;
        myFont = font;
    }
    
    /**
     * shadow the text
     * @param text Text to be painted
     * @param textColor Text color
     * @param position Position on the graphics it paints onto
     */
    public void shodow(String text, Color textColor, Point position) {
        final Color SHADOW_COLOR = new Color (220, 220, 220);
        myGraphics.setColor(SHADOW_COLOR);
        myGraphics.drawString(text, ShiftEast(position.x, 2), ShiftSouth(position.y, 2));
        myGraphics.setColor(textColor);
        myGraphics.drawString(text, position.x, position.y);
    }
    
    /**
     * set a new font
     * @param font New Font object
     */
    public void setFont(Font font) {
        myFont = font;
    }

    private int ShiftNorth (int p, int distance) {
        return (p - distance);
    }

    private int ShiftSouth (int p, int distance) {
        return (p + distance);
    }

    private int ShiftEast (int p, int distance) {
        return (p + distance);
    }

    private int ShiftWest (int p, int distance) {
        return (p - distance);
    }
}
