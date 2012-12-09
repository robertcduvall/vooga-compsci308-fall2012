package games.mindchallenge;

import java.awt.Image;
import java.util.List;
import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * Walk around, talk to NPCs, answer questions.
 * 
 * Features:
 * 1. Complex game goals (need to complete several tasks before moving to next level)
 * 2. Multiple levels
 * 3. Custom graphics and sounds
 * 4. Custom user-written game mode and its game object (OptionMode.java & OptionObject.java)
 * 5. Conversation tree
 * 
 * @author volodymyr
 */
public class MindChallenge implements IArcadeGame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String RESOURCE_ADDRESS = "games.mindchallenge.MindChallenge";
    private static final String XML_PATH = "src/games/mindchallenge/MindChallenge.xml";

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        GameWindow myGameWindow =
                new GameWindow("The Mind Challenge", RESOURCE_ADDRESS, WIDTH, HEIGHT, XML_PATH);
        myGameWindow.changeActivePane(GameWindow.GAME);
    }

    @Override
    public List<Image> getScreenshots () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Image getMainImage () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDescription () {
        return "How smart are you?";
    }

    @Override
    public String getName () {
        return "The Mind Challenge";
    }

    /**
     * for debugging purposes
     * 
     * @param args
     *        arguments
     */
    public static void main (String[] args) {
        GameWindow myGameWindow =
                new GameWindow("The Mind Challenge", RESOURCE_ADDRESS, WIDTH, HEIGHT, XML_PATH);
        myGameWindow.changeActivePane(GameWindow.GAME);
    }

}
