package arcade.datatransfer;

import java.awt.Image;
import java.util.List;
import arcade.gamemanager.GameCenter;
import arcade.gui.Arcade;
import arcade.usermanager.SocialCenter;


/**
 * Methods that facilitate exchange of data between
 * the view and models of the Arcade
 * 
 * @author Michael Deng
 * 
 */
public class ModelInterface {

    private Arcade myArcade;
    private GameCenter myGameCenter;
    private SocialCenter mySocialCenter;

    public ModelInterface (Arcade a) {
        myArcade = a;

        // myGameCenter = new GameCenter(); // GameCenter is currently broken.
        // mySocialCenter = SocialCenter.getInstance();
        
    }

    // #############################################################
    // Implemented by the Game team
    // #############################################################

    public String getGameName () {
        // TODO Auto-generated method stub
        return null;
    }

    public Image getGameProfilePicture (String gameName) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> getListOfGames () {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRating () {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> getListOfReviews () {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> getUsersThatPlayedGame () {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Integer> getUserScores () {
        // TODO Auto-generated method stub
        return null;
    }

    // #############################################################
    // Implemented by the User team
    // #############################################################

    /**
     * Should change the user of UserLink to whatever user is successfully
     * logged in.
     * 
     * @param username String of the username to be logged in.
     * @param password Array of characters for the attempted password. Must be
     *        array.
     * @return True if successful login, false if unsuccessful.
     */
    public boolean loginUser (String username, char[] password) {
        // TODO Implement this. Return true if login successful, false if
        // unsuccessful.
        return false;
    }

    public String getUserName () {
        // TODO Auto-generated method stub
        return null;

    }

    public Image getUserProfilePicture () {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> getListOfGamesPlayed () {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Integer> getListOfScores () {
        // TODO Auto-generated method stub
        return null;
    }

}
