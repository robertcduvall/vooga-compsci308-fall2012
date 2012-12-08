package vooga.turnbased.gameobject.battleobject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.graphicprocessing.ImageLoop;
import vooga.turnbased.gameobject.GameObject;

/**
 * Abstract class that is extended to create monsters/sprites in the BattleMode.
 * @author Michael Elgart, Tony
 *
 */
public abstract class BattleObject extends GameObject {
    private String myCurrentMessage = null;
    private final String myHEALTH = "health";
    private Map<String, Number> myDefaultStats;
    private Map<String, Number> myChangingStats;
    private String myName;
    private ImageLoop myImageLoop;

    private final double FONT_SCALAR = 18;
    private final float FONT_SPACING_SCALAR = (float) 1.2;
    private final float STAT_FONT_SHIFT = 10;

    /**
     * Create the BattleObject for this sprite which will be used in
     * the BattleMode.
     * @param allowableModes A set of Strings that holds all the possible modes this can exist in
     * @param condition The action that this object can pass to the GameManager,
     * can be GameEvent.NO_ACTION if no action needed
     * @param stats the battle stats of the object, including its defense, attack, and health
     * @param name the name of the object
     * @param image The image of this BattleObject
     */
    public BattleObject(Set<String> allowableModes, String condition, Map<String, Number>
    stats, String name, Image image) {
        super(allowableModes, condition, image);
        myDefaultStats = new HashMap<String, Number>();
        myChangingStats = new HashMap<String, Number>();
        setDefaultStats(stats);
        initializeStats();
        myName = name;
    }
    

    /**
     * Initializes the stats of the BattleObjects to their default values (i.e. before each 
     * battle)
     */
    public void initializeStats(){
        myChangingStats = new HashMap<String, Number>();
        for (String key: myDefaultStats.keySet()){
            myChangingStats.put(key, myDefaultStats.get(key));
        }
    }

    /**
     * Set the stats of the battle object
     * @param stats A map of Attributes (Strings) to values (Numbers)
     */
    public void setDefaultStats (Map<String, Number> stats) {
        for (String key:stats.keySet()) {
            myDefaultStats.put(key, stats.get(key));
        }
    }
    /**
     * Fetch a particular stat
     * @param statName 
     * @return
     */
    public Number getStat (String statName) {
        if (myChangingStats.containsKey(statName)) {
            return myChangingStats.get(statName);
        }
        else {
            return 0;
        }
    }
    /**
     * Fetch a particular default stat
     * @param statName 
     * @return
     */
    public Number getDefaultStat (String statName) {
        if (myDefaultStats.containsKey(statName)) {
            return myDefaultStats.get(statName);
        }
        else {
            return 0;
        }
    }
    /**
     * Change the value of the given stat
     * @param statName The stat you'd like to change
     * @param value The value you want to change the stat to
     */
    protected void changeStat (String statName, Number value) {
        if (myChangingStats.containsKey(statName)) {
            myChangingStats.put(statName, value);
        }
        if(myHEALTH.equals(statName)){
            myDefaultStats.put(statName, value);
        }
    }
    /**
     * Reset all the changing stats back to their default value
     */
    protected void resetStats () {
        for (String stat : myDefaultStats.keySet()) {
            myChangingStats.put(stat, myDefaultStats.get(stat));
        }
    }
    

    /**
     * Get the name of the object
     * @return
     */
    public String getName() {
        return myName;
    }

    /**
     * Will randomly select an option, based on game designer's choice
     * @param target The target enemy of this object, should it choose to attack it.
     * @return Returns the message that describes what was the BattleObject just did
     */
    public abstract void doRandomOption(BattleObject target, List<String> battleMessages);

    /**
     * This controls the BattleObject and receives which option was selected
     * @param MenuOptionSelected The int of the Option that was selected
     * @param target The target of the option
     * @param battleMessages 
     * @return Returns a message that describes what the Action was that the BattleObject just took. 
     */
    public abstract void doOption(int MenuOptionSelected, BattleObject target, List<String> battleMessages);

    /**
     * Executes the first option for this BattleObject.
     * @param target The target of this move/attack, can be null, depending on implementation.
     * @return Returns a message that describes what the Action was that the BattleObject just took. 
     */
    protected abstract void doOption1(BattleObject target, List<String> battleMessages);

    /**
     * Executes the second option for this BattleObject.
     * @param target The target of this move/attack, can be null, depending on implementation.
     * @return Returns a message that describes what the Action was that the BattleObject just took. 
     */
    protected abstract void doOption2(BattleObject target, List<String> battleMessages);

    /**
     * Executes the third option for this BattleObject.
     * @param target The target of this move/attack, can be null, depending on implementation.
     * @return Returns a message that describes what the Action was that the BattleObject just took. 
     */
    protected abstract void doOption3(BattleObject target, List<String> battleMessages);

    /**
     * Executes the fourth option for this BattleObject.
     * @param target The target of this move/attack, can be null, depending on implementation.
     * @return Returns a message that describes what the Action was that the BattleObject just took. 
     */
    protected abstract void doOption4(BattleObject target, List<String> battleMessages);

    /**
     * Implement this method to determine how much of the attack done to this monster
     * actually affects its health.
     * @param damageDone The raw damage done by the other monster's attack.
     */
    public abstract void takeDamage(int damageDone, List<String> battleMessages);

    /**
     * Implement this method to determine how to attack the other enemy, depending on
     * which stats for input. Can call <enemy>.takeDamage(damageDone) once the total
     * amount of damage is called.
     * @param enemy The other BattleObject which this BattleObject is fighting.
     */
    protected abstract void attackEnemy(BattleObject enemy, List<String> battleMessages);

    /**
     * Checks to see if this BattleObject has any health left.
     * @return True if health > 0.
     */
    public boolean isAlive() {
        if (myChangingStats.containsKey(myHEALTH)) {
            Number health = myChangingStats.get(myHEALTH);
            return health.intValue() > 0;
        }
        else {
            return false;
        }
    }

    /**
     * Returns the message for when your BattleObject dies/runs out of health.
     * @return The String you want return upon death.
     */
    public abstract String getDeathMessage();

    /**
     * Returns the message for when your BattleObject is sent out to fight
     * @param isPlayerControlled Pass True if this is a player controlled BattleObject,
     * False if it is not
     * @return The String the will be displayed when this monster starts fighting
     */
    public abstract String getStartFightingMessage(boolean isPlayerControlled);
    
    /**
     * Gets the options that this BattleObject can perform, to be displayed in the GUI
     * @return String array of the options, paradigm is defaulted to 4,
     * could be extended by a game developer
     */
    public abstract String[] getOptions();

    /**
     * Get the current battle message of the Object.
     * @return String that contains the current message.
     */
    public String getCurrentMessage() {
        return myCurrentMessage;
    }

    protected void setCurrentMessage(String message) {
        myCurrentMessage = message;
    }

    /**
     * Paints the BattleObject.
     * @param g Image to be painted.
     * @param x X location of object.
     * @param y Y location of object.
     * @param width Width of image.
     * @param height Height of image.
     */
    // see comments in battlemode.paint(g) explaining why this was renamed
    public void paintBattleObject (Graphics g, int x, int y, int width, int height) {
        super.drawRectangularImage(g, x, y, width, height);
    }

    private int calcFontSize(int width, int height) {
        // current hypotenuse of regular window size is ~965, with font size 25
        // 965/25 = 37.4
        return (int) (Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2)) / FONT_SCALAR);
    }

    /**
     * Paints the current stats for this BattleObject. Will be drawn at x,y, with given 
     * width and height.
     * @param g This is passed in to draw the stats.
     * @param x The x location of where the stats should be drawn
     * @param y  The y location of where the stats should be drawn
     * @param width The width of the box for the stats
     * @param height The height of the box for the stats.
     */
    public void paintStats (Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        int fontSize = calcFontSize(width, height);
        Font font = new Font("Sans_Serif", Font.PLAIN, fontSize);
        FontRenderContext frc = g2d.getFontRenderContext();
        g2d.setColor(Color.BLACK);
        GlyphVector gv = font.createGlyphVector(frc, myName);
        g2d.drawGlyphVector(gv, x + STAT_FONT_SHIFT, y + fontSize * FONT_SPACING_SCALAR);

        String[] statLines = getStatLines();

        for (int i = 0; i < statLines.length; i += 1) {
            gv = font.createGlyphVector(frc, statLines[i]);
            g2d.drawGlyphVector(gv, x + STAT_FONT_SHIFT, y + (2 + i) *
                    fontSize * FONT_SPACING_SCALAR);
        }
    }

    protected abstract String[] getStatLines ();

    @Override 
    public void clear() {
        // remove all instances of this battleobject in the game
    }

    @Override 
    public void update () {
        if (myImageLoop != null) {
            myImage = myImageLoop.next();
        }
    }

    @Override 
    public void paint (Graphics g) {

    }

    /**
     * Sets the animation loop of images.
     * @param imgLoop The ImageLoop that will be used for animation
     */
    public void setImageLoop (ImageLoop imgLoop) {
        myImageLoop = imgLoop;
    }
}
