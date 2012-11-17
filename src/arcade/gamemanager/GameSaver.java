package arcade.gamemanager;

import arcade.IArcadeGame;
import arcade.usermanager.GameData;
import arcade.usermanager.SocialCenter;
import arcade.usermanager.User;


/**
 * Class that the games use to save preferences and score.
 * GameSaver will update User object.
 * 
 * @author Jei Min Yoo, Seon Kang
 * 
 */
public class GameSaver {

    private User myUser;
    private IArcadeGame myGame;
    private SocialCenter mySocialCenter;

    /**
     * Constructor for GameSaver.
     */
    public GameSaver(User user, IArcadeGame game) {
    	setMyUser(user);
        myGame = game;
        mySocialCenter = SocialCenter.getInstance();
    }

    protected void setMyUser(User user) {
    	myUser = user;
	}

    /**
     * 
     * @author Seon Kang
     * @param property
     * @param value
     */
    public void saveUserProperty(String property, String value) {
    	myUser.getGameData(myGame.getName()).setGameInfo(property, value);
    }
    
    public void saveUserProperty(String property, int i) {
    	saveUserProperty(property, ((Integer) i).toString());
    }
    
	/**
     * Used by the game, this method updates gameInfo in User's GameData
     * 
     * @param userGameInfo preferences
     */
    public void saveGameInfo (String userGameInfo) {
    	saveUserProperty(myUser.getGameData(myGame.getName()).getGameInfoKeyString(),
    			userGameInfo);
    }

    /**
     * Used by the game, this method updates high score for a game in User's
     * GameData.
     * @param score score to be saved
     */
    public void saveHighScore (int score) {
    	saveUserProperty(myUser.getGameData(myGame.getName()).getHighScoreKeyString(), 
    			score);
    }
}
