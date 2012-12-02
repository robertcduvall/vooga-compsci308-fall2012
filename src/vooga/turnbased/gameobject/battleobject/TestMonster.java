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
    private final String HEALTH_STAT = "health";
    private final String ATTACK_STAT = "attack";
    private final String DEFENSE_STAT = "defense";
    private final String MAX_HEALTH_STAT = "maxHealth";
    
    private final double INCREASE_ATTACK_VAL = 1;
    private final double INCREASE_DEFENSE_VAL = 1;
    private final double INCREASE_HEALTH_VAL = 3;
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
        int healthLost =  damageDone - getStat(DEFENSE_STAT).intValue();
        if (healthLost > 0) {
            changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue() - healthLost);
        }
    }

    @Override
    public void attackEnemy (BattleObject enemy) {
        enemy.takeDamage(getStat(ATTACK_STAT).intValue());
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

    @Override
    public String[] getOptions () {
        String[] ret = {"ATTACK", "DEFEND", "CHARGE", "HEAL"};
        return ret;

    }

    @Override
    public void doOption1 (BattleObject target) {
        attackEnemy(target);
    }

    @Override
    public void doOption2 (BattleObject target) {
        changeStat(DEFENSE_STAT, getStat(DEFENSE_STAT).intValue() + 1);
    }

    @Override
    public void doOption3 (BattleObject target) {
        changeStat(ATTACK_STAT, getStat(ATTACK_STAT).intValue() +1);
    }

    @Override
    public void doOption4 (BattleObject target) {
        changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue() + 3);
        if (getStat(HEALTH_STAT).intValue() > getStat(MAX_HEALTH_STAT).intValue()) {
            changeStat(HEALTH_STAT, getStat(MAX_HEALTH_STAT).intValue());
        }
    }
}
