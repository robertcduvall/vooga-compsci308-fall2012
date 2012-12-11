package games.chrono.monsters;

import java.util.Random;


/**
 * A helper class to store a monster's options and pick a random option (based
 * on weighted probabilities).
 *
 * @author Kevin Gao
 *
 */
public class MonsterOptions {

    private final String[] myOptions;
    private final double[] myNetProbabilities;
    private double myTotalProb;
    private final Random myRand;

    /**
     * Constructor for a new set of monster options.
     * @param options array of option names
     * @param probabilities array of option probabilities
     */
    public MonsterOptions (String[] options, double[] probabilities) {
        myOptions = options;
        myNetProbabilities = new double[options.length];
        myTotalProb = 0;
        for (int i = 0; i < options.length; i++) {
            double previous = (i == 0) ? 0 : myNetProbabilities[i - 1];
            myNetProbabilities[i] = previous + probabilities[i];
            myTotalProb += probabilities[i];
        }
        myRand = new Random();
    }

    /**
     * Returns an options 1-n where n is the number of available options.
     * @return
     */
    public int getRandomOption () {
        double rand = myRand.nextDouble() * myTotalProb;
        for (int i = 0; i < myNetProbabilities.length; i++) {
            if (rand < myNetProbabilities[i]) { return i + 1; }
        }
        return 1;
    }

    /**
     * Returns the array of possible options.
     * @return
     */
    public String[] getOptions () {
        return myOptions;
    }

    /**
     * Returns the name of an option given its number.
     * @param i Option number.
     * @return
     */
    public String getOptionName (int i) {
        return myOptions[i - 1];
    }

}
