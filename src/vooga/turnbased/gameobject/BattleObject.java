package vooga.turnbased.gameobject;

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
     * @param defense The amount of defense that mitigates losses to health.
     * @param attack The amount of damage the sprite/monster does with each attack.
     * @param health The amount of health that must be destroyed for the sprite/monster to die.
     */
    public BattleObject(int id, int defense, int attack, int health) {
        super(id);
        setMyDefense(defense);
        setMyAttack(attack);
        setMyHealth(health);
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
     * Set defense to the input parameter.
     * @param defense The value to set the defense at.
     */
    public void setMyDefense (int defense) {
        myDefense = defense;
    }

    /**
     * Returns the defense stat of the BattleObject/monster.
     * @return This the defense stat of the BattleObject/monster
     */
    public int getMyDefense () {
        return myDefense;
    }

    /**
     * Set the attack to be a new value.
     * @param attack The new value for attack.
     */
    public void setMyAttack (int attack) {
        this.myAttack = attack;
    }

    /**
     * Returns the attack stat of the BattleObjet/monster.
     * @return The value of the attack that is returned.
     */
    public int getMyAttack () {
        return myAttack;
    }

    /**
     * Set the health to be a new value.
     * @param health The new value for health.
     */
    public void setMyHealth (int health) {
        this.myHealth = health;
    }

    /**
     * Returns the current health stat of the BattleObjet/monster.
     * @return The value of the health that is returned.
     */
    public int getMyHealth () {
        return myHealth;
    }

    /**
     * Changes the current health by healthDiff.
     * @param healthDiff amount to change health by.
     */
    public void changeHealth(int healthDiff) {
        this.myHealth += healthDiff;
    }

}
