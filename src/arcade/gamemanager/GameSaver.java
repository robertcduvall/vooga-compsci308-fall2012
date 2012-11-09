package arcade.gamemanager;

/**
 * Class that the games use to save preferences and score. 
 * GameSaver will directly write to appropriate xml files using XMLWriter.
 * @author Jei Min Yoo
 *
 */
public class GameSaver {

    private String myUser;
    
    public void setUserPreferences(String preferences) {
        //Writer.write(myUser, preferences);
    }
    
    public void setUserScore(int score) {
        //Writer.write(myUser, score);
    }
}
