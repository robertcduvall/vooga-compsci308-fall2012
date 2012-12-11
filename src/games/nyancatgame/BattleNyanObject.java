package games.nyancatgame;

import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import vooga.turnbased.gameobject.battleobject.BattleObject;


/**
 * This is a battleobject to be used in the NyanCat Game.
 * 
 * @author Jenni Mercado
 * 
 */
public class BattleNyanObject extends BattleObject {
    private final String HEALTH_STAT = "health";
    private final String MAX_HEALTH_STAT = "maxHealth";

    private final String DEFENSE_STAT = "defense";
    private final String ATTACK_STAT = "attack";
    private final String MAGIC_STAT = "magic";
    private final String RESISTANCE_STAT = "resistance";

    private final String USED = " used ";

    private final double OPTION1_LOWER_BOUND = 0;
    private final double OPTION1_UPPER_BOUND = .55;
    private final double OPTION2_LOWER_BOUND = .55;
    private final double OPTION2_UPPER_BOUND = .7;
    private final double OPTION3_LOWER_BOUND = .7;
    private final double OPTION3_UPPER_BOUND = .95;
    private final double OPTION4_LOWER_BOUND = .95;
    private final double OPTION4_UPPER_BOUND = 1;

    private final int CRIT_SCALAR = 2;
    private final double myAccuracy = .9;

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
    public BattleNyanObject (Set<String> allowableModes, String condition,
            Map<String, Number> stats, String name, Image image) {
        super(allowableModes, condition, stats, name, image);
    }
    
    public BattleNyanObject (Set<String> allowableModes, String condition,
            Image image, List<String> stats) {
        super(allowableModes, condition, image, stats);
    }

    @Override
    public void takeDamage (int attackDealt, List<String> messages) {
        int healthLost = attackDealt;
        if (healthLost > 0) {
            changeStat(HEALTH_STAT, getStat(HEALTH_STAT).intValue()
                    - healthLost);
        }
    }

    @Override
    public void attackEnemy (BattleObject enemy, List<String> battleMessages) {
        enemy.takeDamage(
                getStat(ATTACK_STAT).intValue()
                        / enemy.getStat(DEFENSE_STAT).intValue(),
                battleMessages);
    }

    @Override
    public String getDeathMessage () {
        return getName() + " has died.";
    }

    @Override
    public String getStartFightingMessage (boolean isPlayerControlled) {
        String name = getName();
        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(2);

        if (isPlayerControlled) {
            String[] messages = { name + " has been challenged. ",
                    name + " is ready to fight!" };
            return messages[i];
        }
        else {
            String[] messages = { name + " has challenged you.",
                    name + " is ready to fight!" };
            return messages[i];
        }

    }

    @Override
    public String[] getOptions () {
        String[] ret = { "ATTACK", "POWER ATTACK", "RAINBOW BEAM",
                "HEALING SPELL" };
        return ret;

    }

    private boolean doesNotMiss () {
        Random randomGenerator = new Random();
        double random = randomGenerator.nextDouble();
        return (random < myAccuracy);
    }
    
    @Override
    public void doOption1 (BattleObject target, List<String> battleMessages) {
        attackEnemy(target, battleMessages);
        battleMessages.add(getName() + USED + getOptions()[0]);
    }

    @Override
    public void doOption2 (BattleObject target, List<String> battleMessages) {
        battleMessages.add(getName() + USED + getOptions()[1]);
        if (doesNotMiss()) {
            target.takeDamage(CRIT_SCALAR * getStat(ATTACK_STAT).intValue()
                    / target.getStat(DEFENSE_STAT).intValue(), battleMessages);
            battleMessages.add("The attack was successful!");
            return;
        }
        battleMessages.add(getName() + "has missed!");
    }

    @Override
    public void doOption3 (BattleObject target, List<String> battleMessages) {
        target.takeDamage(getStat(MAGIC_STAT).intValue()
                / target.getStat(RESISTANCE_STAT).intValue(), battleMessages);
        battleMessages.add((getName() + USED + getOptions()[2]));
    }

    @Override
    public void doOption4 (BattleObject target, List<String> battleMessages) {
        battleMessages.add(getName() + USED + getOptions()[3]);
        if (doesNotMiss()) {
            changeStat(HEALTH_STAT,
                    getStat(HEALTH_STAT).intValue()
                            + getStat(MAX_HEALTH_STAT).intValue() / 10);
            if (getStat(HEALTH_STAT).intValue() > getStat(MAX_HEALTH_STAT).intValue()) {
                changeStat(HEALTH_STAT, getStat(MAX_HEALTH_STAT).intValue());
            }
            return;
        }
        battleMessages.add("The spell failed!");
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
        String[] ret = new String[5];
        int myHealth = getStat(HEALTH_STAT).intValue();
        int myMaxHealth = getStat(MAX_HEALTH_STAT).intValue();
        int myAttack = getStat(ATTACK_STAT).intValue();
        int myDefense = getStat(DEFENSE_STAT).intValue();
        int myMagic = getStat(MAGIC_STAT).intValue();
        int myResistance = getStat(RESISTANCE_STAT).intValue();

        ret[0] = "Health: " + myHealth + "/" + myMaxHealth;
        ret[1] = "Attack: " + myAttack;
        ret[2] = "Defense: " + myDefense;
        ret[3] = "Magic: " + myMagic;
        ret[4] = "Magic Resistance: " + myResistance;

        return ret;

    }

    @Override
    public void doOption (int MenuOptionSelected, BattleObject target, List<String> battleMessages) {
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
