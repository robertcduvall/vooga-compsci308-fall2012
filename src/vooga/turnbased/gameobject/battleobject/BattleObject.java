package vooga.turnbased.gameobject.battleobject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.HashMap;
import java.util.Map;

import util.graphicprocessing.ImageLoop;
import vooga.turnbased.gameobject.GameObject;

/**
 * Abstract class that is extended to create monsters/sprites in the BattleMode.
 * @author Michael Elgart, Tony
 *
 */
public abstract class BattleObject extends GameObject {
    private final String HEALTH = "health";
    private Map<String, Number> myStats;
    private String myName;
    private ImageLoop myImageLoop;

    private final double FONT_SCALAR = 18;
    private final float FONT_SPACING_SCALAR = (float) 1.2;
    private final float STAT_FONT_SHIFT = 10;

    /**
     * Create the BattleObject for this sprite which will be used in
     * the BattleMode.
     * @param id the ID number of the object.
     * @param event The action that this object can pass to the GameManager,
     * can be GameEvent.NO_ACTION if no action needed
     * @param stats the battle stats of the object, including its defense, attack, and health
     * @param name the name of the object
     * @param image The image of this BattleObject
     */
    public BattleObject(int id, String event, Map<String, Number> stats, String name, Image image) {
        super(id, event, image);
        myStats = new HashMap<String, Number>();
        setStats(stats);
        myName = name;
    }

    /**
     * Set the stats of the battle object
     * @param stats A map of Attributes (Strings) to values (Numbers)
     */
    public void setStats (Map<String, Number> stats) {
        for (String key:stats.keySet()) {
            myStats.put(key, stats.get(key));
        }
    }
    /**
     * Fetch a particular stat
     * @param statName 
     * @return
     */
    public Number getStat (String statName) {
        if (myStats.containsKey(statName)) {
            return myStats.get(statName);
        }
        else {
            return 0;
        }
    }
    /**
     * Change the value of the given stat
     * @param statName 
     * @param value 
     */
    public void changeStat (String statName, Number value) {
        if (myStats.containsKey(statName)) {
            myStats.put(statName, value);
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
     * Implement this method to determine how much of the attack done to this monster
     * actually affects its health.
     * @param damageDone The raw damage done by the other monster's attack.
     */
    public abstract void takeDamage(int damageDone);

    /**
     * Implement this method to determine how to attack the other enemy, depending on
     * which stats for input. Can call <enemy>.takeDamage(damageDone) once the total
     * amount of damage is called.
     * @param enemy The other BattleObject which this BattleObject is fighting.
     */
    public abstract void attackEnemy(BattleObject enemy);

    /**
     * Checks to see if this BattleObject has any health left.
     * @return True if health > 0.
     */
    public boolean isAlive() {
        if (myStats.containsKey(HEALTH)) {
            Number health = myStats.get(HEALTH);
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

    //fix this at some point...
    public void paintStats (Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setPaint(Color.CYAN);
        //g2d.draw3DRect(x, y, width, height, true);
        //g2d.fill(new Rectangle2D.Double(x, y, width, height));
        int fontSize = calcFontSize(width, height);
        Font font = new Font("Sans_Serif", Font.PLAIN, fontSize);
        FontRenderContext frc = g2d.getFontRenderContext();
        g2d.setColor(Color.BLACK);
        GlyphVector gv = font.createGlyphVector(frc, myName);
        g2d.drawGlyphVector(gv, x + STAT_FONT_SHIFT, y + fontSize * FONT_SPACING_SCALAR);


        int myHealth = myStats.get(HEALTH).intValue();
        int myMaxHealth = myStats.get("maxHealth").intValue();
        int myAttack = myStats.get("attack").intValue();
        int myDefense = myStats.get("defense").intValue();
        
        String health = "Health: " + myHealth + "/" + myMaxHealth;
        gv = font.createGlyphVector(frc, health);
        g2d.drawGlyphVector(gv, x + STAT_FONT_SHIFT, y + 2 * fontSize * FONT_SPACING_SCALAR);

        String attack = "Attack: " + myAttack;
        gv = font.createGlyphVector(frc, attack);
        g2d.drawGlyphVector(gv, x + STAT_FONT_SHIFT, y + 3 * fontSize * FONT_SPACING_SCALAR);

        String defense = "Defense: " + myDefense;
        gv = font.createGlyphVector(frc, defense);
        g2d.drawGlyphVector(gv, x + STAT_FONT_SHIFT, y + 4 * fontSize * FONT_SPACING_SCALAR);
    }
    

    @Override 
    public void clear() {
        // remove all instances of this battleobject in the game
    }

    @Override 
    public void update () {
        if(myImageLoop != null)
            myImage = myImageLoop.next();
    }

    @Override 
    public void paint (Graphics g) {

    }
    
    public void setImageLoop (ImageLoop imgLoop) {
        myImageLoop = imgLoop;
    }
}
