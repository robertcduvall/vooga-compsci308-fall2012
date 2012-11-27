package vooga.turnbased.gameobject.battleobject;

import java.awt.Image;
import java.util.Map;


/**
 * This is a test battle monster that will be used to see if the BattleMode functions.
 * Extends the abstract class BattleObject.
 * @author Michael Elgart, Tony
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
    public TestMonster (int id, String event, Map<String, Number> stats, String name, Image image) {
        super(id, event, stats, name, image);
    }

    @Override
    public void takeDamage (int damageDone) {
        int healthLost =  damageDone - getStat("defense").intValue();
        //System.out.println(healthLost);
        changeStat("health", getStat("health").intValue() - healthLost);
    }

    @Override
    public void attackEnemy (BattleObject enemy) {
        enemy.takeDamage(getStat("attack").intValue());
    }
}
