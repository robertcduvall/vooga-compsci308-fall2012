package vooga.turnbased.gameobject.battleobject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import vooga.turnbased.gameobject.GameObject;

/**
 * Abstract class that is extended to create monsters/sprites in the BattleMode.
 * @author Michael Elgart, Tony
 *
 */
public abstract class BattleObject extends GameObject {
    private Map<String, Number> myStats;
    private String myName;

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
     * @param stats 
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
        if (myStats.containsKey("health")) {
            Number health = myStats.get("health");
            return health.doubleValue() > 0;
        }
        else {
            return false;
        }
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
        super.drawRectangularImage(g, x, y, width / 2, height);
        paintStats(g, x + width / 2, y, width / 2, height);
    }

    //fix this at some point...
    private void paintStats (Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setPaint(Color.CYAN);
        //g2d.draw3DRect(x, y, width, height, true);
        //g2d.fill(new Rectangle2D.Double(x, y, width, height));
        Font font = new Font("Sans_Serif", Font.PLAIN, 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        g2d.setColor(Color.BLACK);
        GlyphVector gv = font.createGlyphVector(frc, myName);
        g2d.drawGlyphVector(gv, x+10, y+30);
        int myHealth = myStats.get("health").intValue();
        int myMaxHealth = myStats.get("maxHealth").intValue();
        String health = "Health: " + myHealth + "/" + myMaxHealth;
        gv = font.createGlyphVector(frc, health);
        g2d.drawGlyphVector(gv, x+10, y+60);
        int myAttack = myStats.get("attack").intValue();
        String attack = "Attack: " + myAttack;
        gv = font.createGlyphVector(frc, attack);
        g2d.drawGlyphVector(gv, x+10, y+90);
        int myDefense = myStats.get("defense").intValue();
        String defense = "Defense: " + myDefense;
        gv = font.createGlyphVector(frc, defense);
        g2d.drawGlyphVector(gv, x+10, y+120);
    }
    
    @Override 
    public void clear() {
        // remove all instances of this battleobject in the game
    }
    
    @Override 
    public void update () {
        
    }
    
    @Override 
    public void paint (Graphics g) {
        
    }
}
