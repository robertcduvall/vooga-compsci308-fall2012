package vooga.turnbased.gameobject.optionobject;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import util.graphicprocessing.FontEffect;
import vooga.turnbased.gamecore.gamemodes.OptionMode;
import vooga.turnbased.gameobject.GameObject;


/**
 * option object that describes an option being displayed in an Option Mode.
 * Such as transport to another level, talking etc.
 * 
 * @author rex
 * 
 */
public class OptionObject extends GameObject {

    private static final int FONT_SIZE = 16;
    private static final int EPSILON = 10;
    // Amount of change in x, y coordinates when highlighted
    private static final Point DISPLACEMENT = new Point(2, 3);
    private Color myHighlightColor;
    private Color myDefaultColor;
    private Point myPosition;
    private FontMetrics myFontMetrics;
    private Rectangle myRespondRegion;
    private Color myColor;
    private boolean myIsHighlighted;
    private String myMessage;

    /**
     * constructor of OptionObject
     * 
     * @param allowableModes the modes it could be in (OptionMode)
     * @param condition event it triggers
     * @param message The option to be displayed on screen
     */
    public OptionObject (Set<String> allowableModes, String condition, String message) {
        super(allowableModes, condition, null);
        myMessage = message;
        initializeProperties();
    }
    
    /**
     * default option: quit
     */
    public static OptionObject getDefaultOptionObject (String quitMessage) {
        Set<String> allowableModes = new HashSet<String>();
        allowableModes.add("option");
        OptionObject defaultOption = new OptionObject (allowableModes, "NO_ACTION", quitMessage);
        return defaultOption;
    }
    
    private void initializeProperties() {
        myHighlightColor = Color.CYAN;
        myDefaultColor = Color.MAGENTA;
        myColor = myDefaultColor;
        myIsHighlighted = false;
    }

    /**
     * set position of where the option is painted
     * 
     * @param paintPosition the position where the option is painted
     */
    public void setPosition (Point paintPosition) {
        myPosition = paintPosition;
    }

    /**
     * set message of the option
     * 
     * @param message the message for this option
     */
    public void setMessage (String message) {
        myMessage = message;
    }

    /**
     * get the option message
     * 
     * @return option message
     */
    public String getMessage () {
        return myMessage;
    }

    /**
     * paint the option
     * 
     * @param g graphics object it paints to
     */
    public void paint (Graphics g) {
        Font optionFont = new Font("Helvetica", Font.BOLD, FONT_SIZE);
        g.setFont(optionFont);
        g.setColor(myColor);
        FontEffect myGameFont = new FontEffect(g, optionFont);
        myFontMetrics = g.getFontMetrics();
        if (myRespondRegion == null) {
            calculateRespondRegion();
        }
        paintMessage(myGameFont);
    }

    /**
     * Different games can override this method for other font effects
     * The reason the colors and layers are defined as final variable is that
     * throughout a game, the font for one kind of options should be consistent.
     * 
     * @param fontEffect
     */
    protected void paintMessage (FontEffect fontEffect) {
        final Color TOP_COLOR = new Color(0, 10, 115);
        final Color SIDE_COLOR = new Color(60, 0, 115);
        final int LAYER = 4;
        fontEffect.threeDimensionEffect(myMessage, myColor, TOP_COLOR, SIDE_COLOR, LAYER,
                                        myPosition);
    }

    private void calculateRespondRegion () {
        int x = myPosition.x - EPSILON;
        int y = myPosition.y - myFontMetrics.getHeight() - EPSILON;
        int width = myFontMetrics.stringWidth(myMessage) + 2 * EPSILON;
        int height = myFontMetrics.getHeight() + 2 * EPSILON;
        myRespondRegion = new Rectangle(x, y, width, height);
    }

    /**
     * highlight the option (if the current focus is on this option)
     * 
     * @param focusPosition the position of the focus on screen (either using a
     *        mouse or other input devices)
     * @return if the option is highlighted
     */
    public boolean highlight (Point focusPosition) {
        if (myRespondRegion.contains(focusPosition)) {
            myColor = myHighlightColor;
            myIsHighlighted = true;
            myPosition.translate(DISPLACEMENT.x, DISPLACEMENT.y);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * dehighlight the option
     */
    public void dehighlight () {
        if (myIsHighlighted) {
            myColor = myDefaultColor;
            myIsHighlighted = false;
            myPosition.translate(-DISPLACEMENT.x, -DISPLACEMENT.y);
        }
    }

    /**
     * check option's highlight status
     * 
     * @return if the option is highlighted
     */
    public boolean optionIsHighlighted () {
        return myIsHighlighted;
    }

    /**
     * check whether the option should be triggered
     * 
     * @param focusPosition the position of the focus on screen (either using a
     *        mouse or other input devices)
     * @return if the option should be triggered
     */
    public boolean isTriggered (Point focusPosition) {
        return myRespondRegion.contains(focusPosition);
    }

    /**
     * execute this option
     * 
     * @param myOptionMode the option mode it is in
     */
    public void executeOption (OptionMode myOptionMode) {
        myOptionMode.setModeIsOver();
    }

    @Override
    public void update () {
        // no animation
        // sub-classes can override to add animation
    }

    @Override
    public void clear () {
        // never clear options
        // sub-classes can change this behavior for the purpose of showing
        // options dynamically
    }
    
    protected void setHighlightColor(Color c) {
        myHighlightColor = c;
    }
    
    protected void setDefaultColor (Color c) {
        myDefaultColor = c;
    }
    
    protected Color getColor () {
        return myColor;
    }
    
    protected Point getPosition() {
        return myPosition;
    }
}
