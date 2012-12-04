package vooga.turnbased.gameobject.optionobject;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Set;
import util.graphicprocessing.FontEffect;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.GameObject;
import vooga.turnbased.gameobject.mapobject.MapObject;


public class OptionObject extends GameObject {

    private static final int FONT_SIZE = 16;
    private static final int EPSILON = 10;
    // Amount of change in x, y coordinates when highlighted
    private static final Point DISPLACEMENT = new Point(2, 3);
    private static final Color HIGHLIGHT_COLOR = Color.CYAN;
    private static final Color DEFAULT_COLOR = Color.MAGENTA;
    private Point myPosition;
    private FontMetrics myFontMetrics;
    private Rectangle myRespondRegion;
    private Color myColor;
    private boolean isHighlighted;
    private String myMessage;

    public OptionObject (Set<String> allowableModes, String condition, String message) {
        super(allowableModes, condition, null);
        myMessage = message;
        myColor = DEFAULT_COLOR;
        isHighlighted = false;
    }
    
    public void setPosition(Point paintPosition) {
        myPosition = paintPosition;
    }

    public void setMessage (String message) {
        myMessage = message;
    }

    public String getMessage () {
        return myMessage;
    }

    public void paint (Graphics g) {
        Font optionFont = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        g.setFont(optionFont);
        g.setColor(myColor);
        FontEffect myGameFont = new FontEffect(g, optionFont);
        myFontMetrics = g.getFontMetrics();
        if (myRespondRegion == null) {
            calculateRespondRegion();
        }
        Color topColor = new Color(0, 10, 115);
        Color sideColor = new Color(60, 0, 115);
        int layer = 4;
        myGameFont.threeDimensionEffect(myMessage, myColor, topColor, sideColor, layer,
                                        myPosition);
    }

    private void calculateRespondRegion () {
        int x = myPosition.x - EPSILON;
        int y = myPosition.y - myFontMetrics.getHeight() - EPSILON;
        int width = myFontMetrics.stringWidth(myMessage) + 2 * EPSILON;
        int height = myFontMetrics.getHeight() + 2 * EPSILON;
        myRespondRegion = new Rectangle(x, y, width, height);
    }

    public boolean highlight (Point mousePosition) {
        if (myRespondRegion.contains(mousePosition)) {
            myColor = HIGHLIGHT_COLOR;
            isHighlighted = true;
            myPosition.translate(DISPLACEMENT.x, DISPLACEMENT.y);
            return true;
        }
        else {
            return false;
        }
    }

    public void dehighlight () {
        if (isHighlighted) {
            myColor = DEFAULT_COLOR;
            isHighlighted = false;
            myPosition.translate(-DISPLACEMENT.x, -DISPLACEMENT.y);
        }
    }

    public boolean optionIsHighlighted () {
        return isHighlighted;
    }
    
    public boolean isTriggered(Point focusPosition) {
        return myRespondRegion.contains(focusPosition);
    }
    
    public void executeOption(OptionMode myOptionMode) {
        myOptionMode.setModeIsOver();
    }

    @Override
    public void update () {
    }

    @Override
    public void clear () {
    }
}
