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
    
    private final double OPTION1_LOWER_BOUND = .0;
    private final double OPTION1_UPPER_BOUND = .5;
    private final double OPTION2_LOWER_BOUND = .5;
    private final double OPTION2_UPPER_BOUND = .7;
    private final double OPTION3_LOWER_BOUND = .7;
    private final double OPTION3_UPPER_BOUND = .9;
    private final double OPTION4_LOWER_BOUND = .9;
    private final double OPTION4_UPPER_BOUND = 1;
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
        setCurrentMessage(getName() + " used " + getOptions()[0]);
        attackEnemy(target);
    }

    @Override
    public void doOption2 (BattleObject target) {
        setCurrentMessage(getName() + " used " + getOptions()[1]);
        changeStat(DEFENSE_STAT, getStat(DEFENSE_STAT).intValue() + 1);
    }

    @Override
    public void doOption3 (BattleObject target) {
        setCurrentMessage(getName() + " used " + getOptions()[2]);
        changeStat(ATTACK_STAT, getStat(ATTACK_STAT).intValue() +1);
    }

    @Override
    public void doOption4 (BattleObject target) {
        setCurrentMessage(getName() + " used " + getOptions()[3]);
        changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue() + 3);
        if (getStat(HEALTH_STAT).intValue() > getStat(MAX_HEALTH_STAT).intValue()) {
            changeStat(HEALTH_STAT, getStat(MAX_HEALTH_STAT).intValue());
        }
    }

    @Override
    public void doRandomOption (BattleObject target) {
        Random randomGenerator = new Random();
        double random = randomGenerator.nextDouble();
        if (random >= OPTION1_LOWER_BOUND && random < OPTION1_UPPER_BOUND) {
            doOption1(target);
        }
        if (random >= OPTION2_LOWER_BOUND && random < OPTION2_UPPER_BOUND) {
            doOption2(target);
        }
        if (random >= OPTION3_LOWER_BOUND && random < OPTION3_UPPER_BOUND) {
            doOption3(target);
        }
        if (random >= OPTION4_LOWER_BOUND && random < OPTION4_UPPER_BOUND) {
            doOption4(target);
        }
    }
}
