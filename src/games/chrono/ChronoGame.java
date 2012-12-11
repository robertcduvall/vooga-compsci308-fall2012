package games.chrono;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * Turn-based RPG game based on Chrono Trigger.
 * @author Kevin Gao
 *
 */
public class ChronoGame implements IArcadeGame {

    private static final String GAME_NAME = "Chrono Trigger";
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 450;
    private static final String RESOURCES_PACKAGE = "games.chrono.resources.GameSetting";
    private static final String GAME_CONFIG = "src/games/chrono/levels/level1.xml";
    private static final String GAME_DESCRIPTION =
            "A game based on the popular game Chrono Trigger";

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        GameWindow gw =
                new GameWindow(GAME_NAME, RESOURCES_PACKAGE, GAME_WIDTH, GAME_HEIGHT, GAME_CONFIG);
        gw.changeActivePane(GameWindow.GAME);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> screenshots = new ArrayList<Image>();
        Image ss;
        try {
            ss = ImageIO.read(new File("src/games/chrono/resources/screenshot.png"));
            screenshots.add(ss);
        }
        catch (IOException e) {
        }
        return screenshots;
    }

    @Override
    public Image getMainImage () {
        Image main = null;
        try {
            main = ImageIO.read(new File("src/games/chrono/resources/main.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find screenshot");
        }
        return main;
    }

    @Override
    public String getDescription () {
        return GAME_DESCRIPTION;
    }

    @Override
    public String getName () {
        return GAME_NAME;
    }

    /**
     * Main method provided for launching game independent of arcade.
     * @param args command-line arguments
     */
    public static void main (String[] args) {
        new GameWindow(GAME_NAME, RESOURCES_PACKAGE, GAME_WIDTH, GAME_HEIGHT, GAME_CONFIG);
    }

}
