package util.graphicprocessing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;


/**
 * this class is to paint strings with different effects and in more beautiful
 * ways
 * Graphics object, string content, positions are needed for painting these
 * effects
 * 
 * @author rex
 * 
 */
public class FontEffect {

    private Graphics2D myGraphics;
    private Font myFont;

    /**
     * Constructor
     * 
     * @param g the Graphics object it paints to
     * @param font the Font of all text
     */
    public FontEffect (Graphics g, Font font) {
        myGraphics = (Graphics2D) g;
        myFont = font;
        g.setFont(font);
    }

    /**
     * Constructor
     * 
     * @param g the Graphics2D object it paints to
     * @param font the Font of all text
     */
    public FontEffect (Graphics2D g, Font font) {
        myGraphics = g;
        myFont = font;
        g.setFont(font);
    }

    /**
     * shadow the text
     * 
     * @param text Text to be painted
     * @param textColor Text color
     * @param position Position on the graphics it paints onto
     */
    public void shodowEffect (String text, Color textColor, Point position) {
        final Color SHADOW_COLOR = new Color(220, 220, 220);
        myGraphics.setColor(SHADOW_COLOR);
        myGraphics.drawString(text, ShiftEast(position.x, 2), ShiftSouth(position.y, 2));
        myGraphics.setColor(textColor);
        myGraphics.drawString(text, position.x, position.y);
    }

    /**
     * paint a word in 3D effect!!
     * 
     * @param text Text to be painted
     * @param mainColor the color of the text
     * @param topColor the color of the top of the 3D text
     * @param sideColor the color of the side of the 3D text
     * @param position position on which the text is painted
     */
    public void threeDimensionEffect (String text, Color mainColor, Color topColor,
                                      Color sideColor, int thickness, Point position) {
        for (int i = 0; i < thickness; i++) {
            myGraphics.setColor(topColor);
            myGraphics.drawString(text, ShiftEast(position.x, i),
                                  ShiftNorth(ShiftSouth(position.y, i), 1));
            myGraphics.setColor(sideColor);
            myGraphics.drawString(text, ShiftWest(ShiftEast(position.x, i), 1),
                                  ShiftSouth(position.y, i));
        }
        myGraphics.setColor(mainColor);
        myGraphics.drawString(text, ShiftEast(position.x, 5), ShiftSouth(position.y, 5));
    }

    /**
     * outline a word with specific color
     * @param text Text to be painted
     * @param mainColor the color of the text
     * @param outlineColor the color of the outline
     * @param position position on which the text is painted
     */
    public void outlineEffect (String text, Color mainColor, Color outlineColor, Point position) {
        myGraphics.setColor(outlineColor);
        myGraphics.drawString(text, ShiftWest(position.x, 1), ShiftNorth(position.y, 1));
        myGraphics.drawString(text, ShiftWest(position.x, 1), ShiftSouth(position.y, 1));
        myGraphics.drawString(text, ShiftEast(position.x, 1), ShiftNorth(position.y, 1));
        myGraphics.drawString(text, ShiftEast(position.x, 1), ShiftSouth(position.y, 1));
        myGraphics.setColor(mainColor);
        myGraphics.drawString(text, position.x, position.y);
    }

    /**
     * set a new font
     * 
     * @param font New Font object
     */
    public void setFont (Font font) {
        myFont = font;
        myGraphics.setFont(myFont);
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
