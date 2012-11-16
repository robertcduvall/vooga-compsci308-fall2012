package vooga.turnbased.gameobject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import vooga.turnbased.gamecore.GameManager;

/**
 * This is a test battle monster that will be used to see if the BattleMode functions.
 * Extends the abstract class BattleObject.
 * @author Michael Elgart
 *
 */
public class TestMonster extends BattleObject {

    /**
     * Creates a test battle monster.
     * @param id the ID number of the object.
     * @param event The action that this object can pass to the GameManager,
     * can be GameEvent.NO_ACTION if no action needed
     * @param defense The amount of defense that mitigates losses to health.
     * @param attack The amount of damage the sprite/monster does with each attack.
     * @param health The amount of health that must be destroyed for the sprite/monster to die.
     * @param image The image of this testMonster
     */
    public TestMonster (int id, GameManager.GameEvent event, int defense,
            int attack, int health, Image image) {
        super(id, event, defense, attack, health, image);
    }

    @Override
    public void takeDamage (int damageDone) {
        int healthLost =  damageDone - getDefense();
        setHealth(getHealth() - healthLost);
    }

    @Override
    public void attackEnemy (BattleObject enemy) {
        enemy.takeDamage(getAttack());
    }

    @Override
    public void update (int delayTime) {
        // TODO Auto-generated method stub

    }

}
