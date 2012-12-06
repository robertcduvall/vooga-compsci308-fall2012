package arcade.gamemanager;

import arcade.IArcadeGame;
import arcade.usermanager.GameData;
import arcade.usermanager.User;
import arcade.usermanager.UserManager;


/**
 * Class that the games use to save preferences and score.
 * GameSaver will use the GameData object to save game information.
 * 
 * @author Seon Kang
 * 
 */
public class GameSaver {

    private String myUserName;
    private IArcadeGame myGame;
    private UserManager myUserManager;
    private GameData myGameData;

    /**
     * Constructor for GameSaver.
     * 
     * @param user to whose file we should save the data
     * @param game the game that will provide the data
     */
    public GameSaver (String userName, IArcadeGame game) {
        setMyGame(game);
        myUserManager = UserManager.getInstance();
        setMyUserName(userName);
        setMyGameData();
    }

    protected void setMyUserName (String userName) {
        myUserName = userName;
    }
    
    protected void setMyGame (IArcadeGame game) {
    	myGame = game;
    }
    
    protected void setMyGameData () {
    	myGameData = myUserManager.getGame(myUserName, myGame.getName());
    }
    /**
     * 
     * @param property string describing the user property
     * @param value string describing the value of that property
     */
    public void saveGameInfo (String property, String value) {
        myGameData.setGameInfo(myUserName, property, value);
    }
    
    /**
     * 
     * @param property Name of what you want to load
     * @return This is the value of the property that you're loading
     */
    public String loadGameInfo (String property) {
    	return myGameData.getGameInfo(property);
    }
}
