package arcade.gamemanager;

import arcade.IArcadeGame;
import arcade.usermanager.GameData;
import arcade.usermanager.SocialCenter;
import arcade.usermanager.User;


/**
 * Class that the games use to save preferences and score.
 * GameSaver will update User object.
 * 
 * @author Jei Min Yoo
 * 
 */
public class GameSaver {

    private String myUserName;
    private IArcadeGame myGame;
    private SocialCenter socialCenter;

    /**
     * Constructor for GameSaver.
     */
    public GameSaver(String userName, IArcadeGame game) {
        myUserName = userName;
        myGame = game;
        socialCenter = new SocialCenter();
    }

    /**
     * Used by the game, this method updates gameInfo in User's GameData
     * 
     * @param userGameInfo preferences
     */
    public void saveGameInfo (String userGameInfo) {

        socialCenter.writeGameInfo(myGame.getName(), userGameInfo);
        // what happens if saving a new game? handled in SocialCenter?

    }

    /**
     * Used by the game, this method updates high score for a game in User's
     * GameData.
     * 
     * @param score score to be saved
     */
    public void saveHighScore (int score) {
        // method to be implemented in SocialCenter?
        socialCenter.writeGameScore(myGame.getName(),score);
        
    }
}
