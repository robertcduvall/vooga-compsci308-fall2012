package vooga.turnbased.gui.interactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import util.graphicprocessing.FontEffect;


public class StrategyOption {

    private static final int FONT_SIZE = 20;
    private static final int EPSILON = 5;
    // Amount of change in x, y coordinates when highlighted
    private static final Point DISPLACEMENT = new Point(2, 3);
    private static final Color HIGHLIGHT_COLOR = Color.CYAN;
    private static final Color DEFAULT_COLOR = Color.MAGENTA;
    private String myOptionMessage;
    private Point myPosition;
    private FontMetrics myFontMetrics;
    private Rectangle myRespondRegion;
    private Color myColor;
    private boolean isHighlighted;

    public StrategyOption (String message, Point position) {
        myOptionMessage = message;
        myPosition = position;
        myColor = DEFAULT_COLOR;
        isHighlighted = false;
    }

    public void paintOption (Graphics g) {
        Font optionFont = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        g.setFont(optionFont);
        g.setColor(myColor);
        FontEffect myGameFont = new FontEffect(g, optionFont);
        myFontMetrics = g.getFontMetrics();
        if (myRespondRegion == null) {
            calculateRespondRegion();
        }
        // myGameFont.shodow(myOptionMessage, myColor, myPosition);
        Color topColor = new Color(0, 10, 115);
        Color sideColor = new Color(60, 0, 115);
        myGameFont.threeDimensionEffect(myOptionMessage, myColor, topColor, sideColor, 5,
                                        myPosition);
    }

    private void calculateRespondRegion () {
        int x = myPosition.x - EPSILON;
        int y = myPosition.y - myFontMetrics.getHeight() - EPSILON;
        int width = myFontMetrics.stringWidth(myOptionMessage) + 2 * EPSILON;
        int height = myFontMetrics.getHeight() + 2 * EPSILON;
        myRespondRegion = new Rectangle(x, y, width, height);
    }

    public void highlight (MouseEvent e) {
        if (myRespondRegion.contains(e.getPoint())) {
            myColor = HIGHLIGHT_COLOR;
            isHighlighted = true;
            myPosition.translate(DISPLACEMENT.x, DISPLACEMENT.y);
        }
    }

    public void dehighlight (MouseEvent e) {
        if (isHighlighted) {
            myColor = DEFAULT_COLOR;
            isHighlighted = false;
            myPosition.translate(-DISPLACEMENT.x, -DISPLACEMENT.y);
        }
    }

    public boolean optionIsHighlighted () {
        return isHighlighted;
    }
}
