package games.chrono.monsters;

import java.awt.Image;
import java.util.List;
import java.util.Random;
import java.util.Set;
import vooga.turnbased.gameobject.battleobject.BattleObject;


/**
 * A concrete enemy that is capable of performing physical damage.
 *
 * @author Kevin Gao
 *
 */
public class Anion extends BasicMonster {

    private final Random myRand;

    /**
     * Constructor for Anion monster.
     *
     * @param allowableModes Game modes the monster can exist in
     * @param condition Condition that the monster activates
     * @param image Monster's image
     * @param stats The monster's stats
     */
    public Anion (Set<String> allowableModes, String condition, Image image, List<String> stats) {
        super(allowableModes, condition, image, stats);
        myRand = new Random();
    }

    @Override
    protected void doOption1 (BattleObject target, List<String> battleMessages) {
        doPhysicalDamage(myRand.nextInt(getAttack()) + 1, target, battleMessages);
    }

    @Override
    protected void doOption2 (BattleObject target, List<String> battleMessages) {
        // No second move
    }

    @Override
    protected void doOption3 (BattleObject target, List<String> battleMessages) {
        // No third move
    }

    @Override
    protected void doOption4 (BattleObject target, List<String> battleMessages) {
        // No fourth move
    }

    @Override
    public void initOptions () {
        String[] options = {"Bash"};
        double[] probabilities = {1};
        setOptions(new MonsterOptions(options, probabilities));
    }

    @Override
    protected void attackEnemy (BattleObject enemy, List<String> battleMessages) {
        // Use doPhysicalDamage() method instead
    }

}
