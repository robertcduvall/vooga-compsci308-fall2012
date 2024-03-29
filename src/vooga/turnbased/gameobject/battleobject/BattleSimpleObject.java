package vooga.turnbased.gameobject.battleobject;

import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


/**
 * This is a test battle monster that will be used to see if the BattleMode
 * functions.
 * Extends the abstract class BattleObject.
 * 
 * @author Michael Elgart, Tony
 * 
 */
public class BattleSimpleObject extends BattleObject {
    private final String HEALTH_STAT = "health";
    private final String ATTACK_STAT = "attack";
    private final String DEFENSE_STAT = "defense";
    private final String MAX_HEALTH_STAT = "maxHealth";
    private final String USED = " used ";

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
     * 
     * @param allowableModes Set of string names of modes in which object should
     *        be.
     * @param condition The action that this object can pass to the GameManager,
     *        can be GameEvent.NO_ACTION if no action needed
     * @param stats Map of string names of characteristics to their numerical
     *        values i.e. defense to
     *        10.
     * @param name String name of monster.
     * @param image The image of this testMonster.
     */
    public BattleSimpleObject (Set<String> allowableModes, String condition,
            Map<String, Number> stats, String name, Image image) {
        super(allowableModes, condition, stats, name, image);
    }

    public BattleSimpleObject (Set<String> allowableModes, String condition,
            Image image, List<String> stats) {
        super(allowableModes, condition, image, stats);
    }

    @Override
    public void takeDamage (int damageDone, List<String> battleMessages) {
        int healthLost = damageDone - getStat(DEFENSE_STAT).intValue();
        if (healthLost > 0) {
            changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue()
                    - healthLost);
        }
    }

    @Override
    public void attackEnemy (BattleObject enemy, List<String> battleMessages) {
        enemy.takeDamage(getStat(ATTACK_STAT).intValue(), battleMessages);
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
            String[] messages = { name + " sent out.", name + " GO!" };
            return messages[i];
        }
        else {
            String[] messages = { name + " encountered.", name + " appeared." };
            return messages[i];
        }

    }

    @Override
    public String[] getOptions () {
        String[] ret = { "ATTACK", "DEFEND", "CHARGE", "HEAL" };
        return ret;
    }

    @Override
    public void doOption1 (BattleObject target, List<String> battleMessages) {
        attackEnemy(target, battleMessages);
        battleMessages.add(getName() + USED + getOptions()[0]);
    }

    @Override
    public void doOption2 (BattleObject target, List<String> battleMessages) {
        changeStat(DEFENSE_STAT, getStat(DEFENSE_STAT).intValue() + 1);
        battleMessages.add(getName() + USED + getOptions()[1]);
    }

    @Override
    public void doOption3 (BattleObject target, List<String> battleMessages) {
        changeStat(ATTACK_STAT, getStat(ATTACK_STAT).intValue() + 1);
        battleMessages.add(getName() + USED + getOptions()[2]);
    }

    @Override
    public void doOption4 (BattleObject target, List<String> battleMessages) {
        setCurrentMessage(getName() + USED + getOptions()[3]);
        changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue() + 3);
        if (getStat(HEALTH_STAT).intValue() > getStat(MAX_HEALTH_STAT)
                .intValue()) {
            changeStat(HEALTH_STAT, getStat(MAX_HEALTH_STAT).intValue());
        }
        battleMessages.add(getName() + USED + getOptions()[3]);
    }

    @Override
    public void doRandomOption (BattleObject target, List<String> battleMessages) {
        Random randomGenerator = new Random();
        double random = randomGenerator.nextDouble();
        if (random >= OPTION1_LOWER_BOUND && random < OPTION1_UPPER_BOUND) {
            doOption1(target, battleMessages);
        }
        if (random >= OPTION2_LOWER_BOUND && random < OPTION2_UPPER_BOUND) {
            doOption2(target, battleMessages);
        }
        if (random >= OPTION3_LOWER_BOUND && random < OPTION3_UPPER_BOUND) {
            doOption3(target, battleMessages);
        }
        if (random >= OPTION4_LOWER_BOUND && random < OPTION4_UPPER_BOUND) {
            doOption4(target, battleMessages);
        }
    }

    @Override
    protected String[] getStatLines () {
        String[] ret = new String[3];
        int myHealth = getStat("health").intValue();
        int myMaxHealth = getStat("maxHealth").intValue();
        int myAttack = getStat("attack").intValue();
        int myDefense = getStat("defense").intValue();

        ret[0] = "Health: " + myHealth + "/" + myMaxHealth;
        ret[1] = "Attack: " + myAttack;
        ret[2] = "Defense: " + myDefense;

        return ret;

    }

    @Override
    public void doOption (int MenuOptionSelected, BattleObject target,
            List<String> battleMessages) {
        switch (MenuOptionSelected) {
            case 1:
                doOption1(target, battleMessages);
                break;
            case 2:
                doOption2(target, battleMessages);
                break;
            case 3:
                doOption3(target, battleMessages);
                break;
            case 4:
                doOption4(target, battleMessages);
                break;
            default:
                break;
        }
    }

}
