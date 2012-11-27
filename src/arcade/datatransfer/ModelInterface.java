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

    // #############################################################
    // Implemented by the User team
    // #############################################################

    /**
     * Should change the user of UserLink to whatever user is successfully
     * logged in.
     * 
     * @param username String of the username to be logged in.
     * @param password string password
     * @return True if successful login, false if unsuccessful.
     */
    public boolean executeLogin (String username, String password) {
        // TODO Implement this. Return true if login successful, false if
        // unsuccessful.

        // test code
        if (username.equals("mdeng1990") && password.equals("pswd")) { return true; }

        return false;
    }

    /**
     * method creates a new user
     * 
     * @param username
     * @param password
     * @param fn
     * @param ln
     * @return true if successful, false if unsuccessful
     */
    public boolean executeNewUser (String username, String password, String fn, String ln) {

        // test code
        if (username.equals("mdeng1990")) { return true; }

        return false;
    }

}
