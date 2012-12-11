package games.chrono.monsters;

import java.awt.Image;
import java.util.List;
import java.util.Random;
import java.util.Set;
import vooga.turnbased.gameobject.battleobject.BattleObject;


/**
 * Monster class to be used by player.
 *
 * @author Kevin Gao
 *
 */
public class Crono extends MagicMonster {

    private final static int CYCLONE_COST = 10;
    private final static double CYCLONE_MULTIPLIER = 1.5;
    private final static int HEAL_AMOUNT = 50;
    private final static int HEAL_COST = 20;
    private final Random myRand;

    /**
     * Constructor for Crono - the player.
     *
     * @param allowableModes Game modes the monster can exist in
     * @param condition Condition that the monster activates
     * @param image Monster's image
     * @param stats The monster's stats
     */
    public Crono (Set<String> allowableModes, String condition, Image image, List<String> stats) {
        super(allowableModes, condition, image, stats);
        myRand = new Random();
    }

    @Override
    protected void doOption1 (BattleObject target, List<String> battleMessages) {
        doPhysicalDamage(myRand.nextInt(getAttack()) + 1, target, battleMessages);
    }

    @Override
    protected void doOption2 (BattleObject target, List<String> battleMessages) {
        if (getMP() > CYCLONE_COST) {
            changeMP(-CYCLONE_COST);
            int damage = (int) ((myRand.nextInt(getAttack()) + 1) * CYCLONE_MULTIPLIER);
            doPhysicalDamage(damage, target, battleMessages);
        }
    }

    @Override
    protected void doOption3 (BattleObject target, List<String> battleMessages) {
        if (getMP() > HEAL_COST) {
            changeMP(-HEAL_COST);
            changeHP(HEAL_AMOUNT);
        }
    }

    @Override
    protected void doOption4 (BattleObject target, List<String> battleMessages) {
        // No fourth move
    }

    @Override
    public void initOptions () {
        String[] options = {"Slash", "Cyclone", "Heal"};
        double[] probabilities = {.70, .20, .10};
        setOptions(new MonsterOptions(options, probabilities));
    }

    @Override
    protected void attackEnemy (BattleObject enemy, List<String> battleMessages) {
        // Use doPhysicalDamage() method instead
    }

}
