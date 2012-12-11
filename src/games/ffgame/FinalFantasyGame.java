package games.ffgame;

import java.awt.Image;
import java.util.List;

import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * @author mark hoffman
 */
public class FinalFantasyGame implements IArcadeGame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    private static final String FILE_PATH = "src/games/ffgame/game.xml";

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        GameWindow myGameWindow = new GameWindow("FinalFantasy", "GameSetting", WIDTH, HEIGHT, FILE_PATH);

    }

    @Override
    public List<Image> getScreenshots () {
        return null;
    }

    @Override
    public Image getMainImage () {
        return null;
    }

    @Override
    public String getDescription () {
        return "Quickly created Final Fantasy RPG game";
    }

    @Override
    public String getName () {
        return "Final Fantasy";
    }
}
