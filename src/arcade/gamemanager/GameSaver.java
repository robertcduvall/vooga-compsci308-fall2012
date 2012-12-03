package arcade.gamemanager;

import arcade.IArcadeGame;
import arcade.usermanager.User;


/**
 * Class that the games use to save preferences and score.
 * GameSaver will update User object.
 * 
 * @author Seon Kang
 * 
 */
public class GameSaver {

    private User myUser;
    private IArcadeGame myGame;

    /**
     * Constructor for GameSaver.
     * 
     * @param user to whose file we should save the data
     * @param game the game that will provide the data
     */
    public GameSaver (User user, IArcadeGame game) {
        setMyUser(user);
        myGame = game;
    }

    protected void setMyUser (User user) {
        myUser = user;
    }

    /**
     * @author Seon Kang
     * 
     * @param property string desribing the user property
     * @param value string describing the value of that property
     */
    public void saveUserProperty (String property, String value) {
        myUser.getGameData(myGame.getName()).setGameInfo(myUser.getName(),property, value);
    }

    /**
     * @author Seon Kang
     * 
     * @param property string describing the user property
     * @param i int describing the value of that property
     */
    public void saveUserProperty (String property, int i) {
        saveUserProperty(property, ((Integer) i).toString());
    }

    /**
     * Used by the game, this method updates gameInfo in User's GameData
     * 
     * @author Seon Kang
     * @param userGameInfo preferences
     */
    public void saveGameInfo (String userGameInfo) {
        saveUserProperty(myUser.getGameData(
                myGame.getName()).getGameInfoKeyString(), userGameInfo);
    }

    /**
     * Used by the game, this method updates high score for a game in User's
     * GameData.
     * 
     * @author Seon Kang
     * @param score score to be saved
     */
    public void saveHighScore (int score) {
        saveUserProperty(myUser.getGameData(
                myGame.getName()).getHighScoreKeyString(), score);
    }
}
