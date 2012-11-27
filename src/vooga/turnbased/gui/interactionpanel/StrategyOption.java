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


public class StrategyOption {
    
    private static final int FONT_SIZE = 20;
    //Amount of change in x, y coordinates when highlighted
    private static final Point DISPLACEMENT = new Point(2, 3);
    private String myOptionMessage;
    private Point myPosition;
    private FontMetrics myFontMetrics;
    private Rectangle mySize;
    private Color myColor;
    private boolean isHighlighted;

    public StrategyOption (String message, Point position) {
        myOptionMessage = message;
        myPosition = position;
        myColor = Color.RED;
        isHighlighted = false;
    }

    public void paintOption (Graphics g) {
        Font optionFont = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        g.setFont(optionFont);
        g.setColor(myColor);
        GameFont myGameFont = new GameFont(g, optionFont);
        myFontMetrics = g.getFontMetrics();
        if (mySize == null) {
            mySize =
                    new Rectangle(myPosition.x, myPosition.y - myFontMetrics.getHeight(),
                                  myFontMetrics.stringWidth(myOptionMessage),
                                  myFontMetrics.getHeight());
        }
        myGameFont.shodow(myOptionMessage, myColor, myPosition);
    }

    public void highlight (MouseEvent e) {
        if (mySize.contains(e.getPoint())) {
            myColor = Color.BLUE;
            isHighlighted = true;
            myPosition.translate(DISPLACEMENT.x, DISPLACEMENT.y);
        }
    }
    
    public void dehighlight (MouseEvent e) {
        if (isHighlighted) {
            myColor = Color.RED;
            isHighlighted = false;
            myPosition.translate(-DISPLACEMENT.x, -DISPLACEMENT.y);
        }
    }
    
    public boolean optionIsHighlighted() {
        return isHighlighted;
    }
}
