package arcade;

/**
 * Class that the games use to save preferences and score.
 * GameSaver will directly write to appropriate xml files using XMLWriter.
 * 
 * @author Jei Min Yoo
 * 
 */
public class GameSaver {

    private String myUser;
    
    /**
     * Constructor for GameSaver.
     */
    public GameSaver() {
        // TODO
    }

    /**
     * Called by the game to store the user preferences, overwriting any
     * existing preferences.
     * 
     * @param userPreferences preferences
     */
    public void saveUserPreferences (String userPreferences) {
        // TODO

    }

    /**
     * Adds the given score to the sorted list of scores for that user.
     * 
     * @param score score to be saved
     */
    public void saveUserScore (int score) {
        // TODO

    }
}
