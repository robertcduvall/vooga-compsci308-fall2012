package arcade;

import arcade.usermanager.GameData;
import arcade.usermanager.User;


/**
 * Class that the games use to save preferences and score.
 * GameSaver will update User object.
 * 
 * @author Jei Min Yoo
 * 
 */
public class GameSaver {

    private User myUser;
    private IArcadeGame myGame;

    /**
     * Constructor for GameSaver.
     */
    public GameSaver(User user, IArcadeGame game) {
        myUser = user;
        myGame = game;
    }

    /**
     * Used by the game, this method updates gameInfo in User's GameData
     * 
     * @param userGameInfo preferences
     */
    public void saveGameInfo (String userGameInfo) {
//        /*
        for (GameData gameData : myUser.getGameData()) {
            if (gameData.getMyGameName() == myGame.getName()) {
                gameData.setMyGameInfo(userGameInfo);
                return;
            }
        }
        
        // gameData does not exist in the user: create a new GameData
        GameData gameData = new GameData(myGame.getName(), userGameInfo, 0, 0);
        myUser.getGameData().add(gameData);
//        */

    }

    /**
     * Used by the game, this method updates high score for a game in User's
     * GameData.
     * 
     * @param score score to be saved
     */
    public void saveHighScore (int score) {
//        /*
        for (GameData gameData : myUser.getGameData()) {
            if (gameData.getMyGameName() == myGame.getName()) {
                gameData.setMyHighScore(score);
                return;
            }
        }
        
        // gameData does not exist in the user: create a new GameData
        GameData gameData = new GameData(myGame.getName(), "", score, 0);
        myUser.getGameData().add(gameData);
//        */
    }
}
