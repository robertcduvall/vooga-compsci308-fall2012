package arcade.gui;

import java.awt.Image;

/**
 * Abstract class for an information storage object from which
 * the GUI will read from. The object is populated with appropriate
 * data received from the Game and User sides, so that the windows 
 * needing game and user information can be filled in.
 * @author Kannan
 *
 */
public abstract class InfoStorageObject {

    /**
     * Retrieves the profile picture of either a game, or a
     * user's avatar. 
     * @return
     */
    public abstract Image getProfilePicture();
    
    /**
     * Retrieves the username or game name.
     * @return
     */
    public abstract String getName();
}
