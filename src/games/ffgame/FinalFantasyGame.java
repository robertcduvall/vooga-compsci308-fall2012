package games.ffgame;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * @author mark hoffman
 */
public class FinalFantasyGame implements IArcadeGame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String FILE_PATH = "src/games/ffgame/game.xml";
    private static final File SCREENSHOT = new File(
            "src/games/ffgame/arcade_images/ScreenShot.png");
    private static final Image SCREENSHOT_IMG = new ImageIcon(SCREENSHOT.getAbsolutePath()).getImage();

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        GameWindow myGameWindow = new GameWindow("FinalFantasy", "GameSetting", WIDTH, HEIGHT, FILE_PATH);
        myGameWindow.changeActivePane(GameWindow.GAME);

    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> list = new ArrayList<Image>();
        list.add(SCREENSHOT_IMG);
        return list;
    }

    @Override
    public Image getMainImage () {
        return SCREENSHOT_IMG;
    }

    @Override
    public String getDescription () {
        return "Quickly created Final Fantasy RPG game";
    }

    @Override
    public String getName () {
        return "Final Fantasy";
    }

    /**
     * 
     * @param args
     */
    public static void main (String[] args) {
        new GameWindow("FinalFantasy", "GameSetting", WIDTH, HEIGHT, FILE_PATH);
    }
}
