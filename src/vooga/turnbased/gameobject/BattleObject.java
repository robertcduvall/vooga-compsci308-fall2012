package vooga.turnbased.gameobject;

/**
 * Abstract class that is extended to create monsters/sprites in the BattleMode.
 * @author Michael Elgart
 *
 */
public abstract class BattleObject extends GameObject {

    private int myDefense;
    private int myAttack;

    /**
     * Create the BattleObject for this sprite which will be used in
     * the BattleMode.
     * @param defense The amount of health that must be destroyed for the sprite/monster to die.
     * @param attack The amount of damage the sprite/monster does with each attack.
     */
    public BattleObject(int defense, int attack){
        setMyDefense(defense);
        setMyAttack(attack);
    }

    /**
     * Implement this method to determine how much of the attack done to this monster
     * actually affects its health.
     * @param damageDone The raw damage done by the other monster's attack.
     */
    public abstract void takeDamage(int damageDone);

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

}
