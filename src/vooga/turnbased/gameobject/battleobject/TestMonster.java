package vooga.turnbased.gameobject.battleobject;

import java.awt.Image;
import java.util.Map;
import java.util.Random;


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
     * @param stats 
     * @param name 
     * @param image The image of this testMonster
     */
    public TestMonster (int id, String event, Map<String, Number> stats, String name, Image image) {
        super(id, event, stats, name, image);
    }

    @Override
    public void takeDamage (int damageDone) {
        int healthLost =  damageDone - getStat("defense").intValue();
        if (healthLost > 0) {
            changeStat("health", getStat("health").intValue() - healthLost);
        }
    }

    @Override
    public void attackEnemy (BattleObject enemy) {
        enemy.takeDamage(getStat("attack").intValue());
    }

    @Override
    public String getDeathMessage () {
        return this.getName() + " fainted.";
    }

    @Override
    public String getStartFightingMessage (boolean isPlayerControlled) {
        String name = this.getName();
        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(2);
        
        if (isPlayerControlled) {
            String[] messages = {name + " sent out.", name + " GO!"};
            return messages[i];
        }
        else {
            String[] messages = {name + " encountered.", name + " appeared."};
            return messages[i]; 
        }

    }
}
