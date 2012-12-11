package games.chrono.monsters;

import java.awt.Image;
import java.util.List;
import java.util.Set;
import vooga.turnbased.gameobject.battleobject.BattleObject;


/**
 * The base monster class. This class provides methods relating to performing
 * and taking physical damage.
 *
 * @author Kevin Gao
 *
 */
public abstract class BasicMonster extends BattleObject {

    private MonsterOptions myOptions;
    private final static int OPTION_1 = 1;
    private final static int OPTION_2 = 2;
    private final static int OPTION_3 = 3;
    private final static int OPTION_4 = 4;
    private final static String ATTACK_STAT = "atk";
    private final static String HP_STAT = "health";
    private final static String MAX_HP = "maxHealth";

    /**
     * Constructor for Basic Monster.
     *
     * @param allowableModes Game modes the monster can exist in
     * @param condition Condition that the monster activates
     * @param image Monster's image
     * @param stats The monster's stats
     */
    public BasicMonster (Set<String> allowableModes, String condition, Image image,
                         List<String> stats) {
        super(allowableModes, condition, image, stats);
        initOptions();
    }

    /**
     * Must set myOptions here.
     */
    public abstract void initOptions ();

    @Override
    public void doRandomOption (BattleObject target, List<String> battleMessages) {
        doOption(myOptions.getRandomOption(), target, battleMessages);
    }

    @Override
    public void doOption (int menuOptionSelected, BattleObject target,
                          List<String> battleMessages) {
        battleMessages.add(String.format("%s USED %s", getName(),
                                         myOptions.getOptionName(menuOptionSelected)));
        switch (menuOptionSelected) {
            case OPTION_1:
                doOption1(target, battleMessages);
                break;
            case OPTION_2:
                doOption2(target, battleMessages);
                break;
            case OPTION_3:
                doOption3(target, battleMessages);
                break;
            case OPTION_4:
                doOption4(target, battleMessages);
                break;
            default:
                doOption1(target, battleMessages);
        }
    }

    @Override
    public void takeDamage (int damageDone, List<String> battleMessages) {
        int newHP = getStat(HP_STAT).intValue() - damageDone;
        changeStat(HP_STAT, (newHP > 0) ? newHP : 0);
    }

    protected void doPhysicalDamage (int amount, BattleObject enemy, List<String> battleMessages) {
        enemy.takeDamage(amount, battleMessages);
    }

    @Override
    public String getDeathMessage () {
        return getName() + " died";
    }

    @Override
    public String getStartFightingMessage (boolean isPlayerControlled) {
        return "Encountered " + getName();
    }

    @Override
    public String[] getOptions () {
        return myOptions.getOptions();
    }

    @Override
    protected String[] getStatLines () {
        String[] stats = new String[1];
        stats[0] =
                String.format("HP: %d/%d", getStat(HP_STAT).intValue(), getStat(MAX_HP).intValue());
        return stats;
    }

    protected void setOptions (MonsterOptions options) {
        myOptions = options;
    }

    protected int getHP () {
        return getStat(HP_STAT).intValue();
    }

    protected void setHP (int hp) {
        changeStat(HP_STAT, hp);
    }

    protected void changeHP (int changeInHP) {
        changeStat(HP_STAT, getHP() + changeInHP);
    }

    protected int getAttack () {
        return getStat(ATTACK_STAT).intValue();
    }

    protected void setAttack (int atk) {
        changeStat(ATTACK_STAT, atk);
    }

}
