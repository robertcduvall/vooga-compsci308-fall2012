package vooga.turnbased.gameobject;

import java.awt.Image;

import vooga.turnbased.gamecore.GameManager;

/**
 * Abstract class that is extended to create monsters/sprites in the BattleMode.
 * @author Michael Elgart
 *
 */
public abstract class BattleObject extends GameObject {

    private int myDefense;
    private int myAttack;
    private int myHealth;

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
}
