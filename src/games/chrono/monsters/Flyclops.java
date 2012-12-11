package games.chrono.monsters;

import java.awt.Image;
import java.util.List;
import java.util.Random;
import java.util.Set;
import vooga.turnbased.gameobject.battleobject.BattleObject;

/**
 * A concrete monster capable of using magic.
 *
 * @author Kevin Gao
 *
 */
public class Flyclops extends MagicMonster {

    private final static int MAX_MANA_DRAIN = 20;
    private final static int ABSORB_COST = 10;
    private final Random myRand;

    /**
     * Constructor for Flyclops monster.
     *
     * @param allowableModes Game modes the monster can exist in
     * @param condition Condition that the monster activates
     * @param image Monster's image
     * @param stats The monster's stats
     */
    public Flyclops (Set<String> allowableModes, String condition, Image image,
                     List<String> stats) {
        super(allowableModes, condition, image, stats);
        myRand = new Random();
    }

    @Override
    protected void doOption1 (BattleObject target, List<String> battleMessages) {
        doPhysicalDamage(myRand.nextInt(getAttack()) + 1, target,
                         battleMessages);
    }

    @Override
    protected void doOption2 (BattleObject target, List<String> battleMessages) {
        int currMana = getMP();
        if (currMana > ABSORB_COST) {
            setMP(currMana - ABSORB_COST);
            int manaToDrain = myRand.nextInt(MAX_MANA_DRAIN) + 1;
            if (target instanceof MagicMonster) {
                MagicMonster castTarget = (MagicMonster) target;
                castTarget.setMP(castTarget.getMP() - manaToDrain);
            }
        }
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
        String[] options = {"Fly", "Absorb"};
        double[] probabilities = {.75, .25};
        setOptions(new MonsterOptions(options, probabilities));
    }

    @Override
    protected void attackEnemy (BattleObject enemy, List<String> battleMessages) {
        // Use doPhysicalDamage() method instead
    }

}
