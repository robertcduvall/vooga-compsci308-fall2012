package arcade;

import java.awt.Image;
import java.util.List;


/**
 * Interface that each game must implement that will allow the arcade to
 * initialize the game.
 * 
 * @author Michael Deng
 * 
 */
public interface IArcadeGame {

    /**
     * This method is called by the Arcade to start the game
     * 
     * @param userPreferences string that contains any saved game data
     * @param s pointer to the GameSaver object
     */
    public void runGame(String userPreferences, GameSaver s);

    /**
     * Method called by the Arcade to get screenshots of the game.
     * 
     * @return a list of images that the Arcade can display
     */
    public List<Image> getScreenshots();

    /**
     * Method called by the Arcade to get the profile image of the game.
     * 
     * @return the profile image of the game
     */
    public Image getMainImage();

    /**
     * Method called by the Arcade to get the description of the game.
     * 
     * @return a string containing a description of the game
     */
    public String getDescription();

    /**
     * Method called by the Arcade to get the name of the game.
     * 
     * @return The name of the game.
     */
    public String getName();

}
