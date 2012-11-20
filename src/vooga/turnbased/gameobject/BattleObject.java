package vooga.turnbased.gameobject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/**
 * Abstract class that is extended to create monsters/sprites in the BattleMode.
 * @author Michael Elgart
 *
 */
public abstract class BattleObject extends GameObject {

    private int myDefense;
    private int myAttack;
    private int myHealth;
    private int myMaxHealth;

    /**
     * Create the BattleObject for this sprite which will be used in
     * the BattleMode.
     * @param id the ID number of the object.
     * @param event The action that this object can pass to the GameManager,
     * can be GameEvent.NO_ACTION if no action needed
     * @param defense The amount of defense that mitigates losses to health.
     * @param attack The amount of damage the sprite/monster does with each attack.
     * @param health The amount of health that must be destroyed for the sprite/monster to die.
     * @param image The image of this BattleObject
     */
    public BattleObject(int id, String event, int defense,
            int attack, int health, Image image) {
        super(id, event, image);
        myMaxHealth = health;
        setDefense(defense);
        setAttack(attack);
        setHealth(health);
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
        return myHealth > 0;
    }

    @Override
    /**
     * Paints the GameObject.
     * @param g Image to be painted.
     * @param x X location of object.
     * @param y Y location of object.
     * @param width Width of image.
     * @param height Height of image.
     */
    public void paint (Graphics g, int x, int y, int width, int height) {
        super.paint(g, x, y, width/2, height);
        paintStats(g, x+width/2, y, width/2, height);
    }

    private void paintStats (Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.CYAN);
        g2d.draw3DRect(x, y, width, height, true);
        g2d.fill(new Rectangle2D.Double(x, y, width, height));
        String name = "Cool Battler";
        Font font = new Font("Sans_Serif", Font.PLAIN, 25);
        FontRenderContext frc = g2d.getFontRenderContext();
        g2d.setColor(Color.BLACK);
        GlyphVector gv = font.createGlyphVector(frc, name);
        g2d.drawGlyphVector(gv, x+10, y+30);
        String health = "Health: " + myHealth + "/" + myMaxHealth;
        gv = font.createGlyphVector(frc, health);
        g2d.drawGlyphVector(gv, x+10, y+60);
    }

    /**
     * Set defense to the input parameter.
     * @param defense The value to set the defense at.
     */
    public void setDefense (int defense) {
        myDefense = defense;
    }

    /**
     * Returns the defense stat of the BattleObject/monster.
     * @return This the defense stat of the BattleObject/monster
     */
    public int getDefense () {
        return myDefense;
    }

    /**
     * Set the attack to be a new value.
     * @param attack The new value for attack.
     */
    public void setAttack (int attack) {
        myAttack = attack;
    }

    /**
     * Returns the attack stat of the BattleObjet/monster.
     * @return The value of the attack that is returned.
     */
    public int getAttack () {
        return myAttack;
    }

    /**
     * Set the health to be a new value.
     * @param health The new value for health.
     */
    public void setHealth (int health) {
        myHealth = health;
    }

    /**
     * Returns the current health stat of the BattleObjet/monster.
     * @return The value of the health that is returned.
     */
    public int getHealth () {
        return myHealth;
    }

    /**
     * Changes the current health by healthDiff.
     * @param healthDiff amount to change health by.
     */
    public void changeHealth(int healthDiff) {
        myHealth += healthDiff;
    }
    
    @Override 
    public void clear() {
        // remove all instances of this battleobject in the game
    }
}
