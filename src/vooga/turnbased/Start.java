package vooga.turnbased;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

public class Start implements IArcadeGame {

    @Override
    public String getDescription () {
        return "This is the turnbased RPG game.";
    }

    @Override
    public Image getMainImage () {
        return GameWindow.importImage("z2i9V.gif");
    }

    @Override
    public String getName () {
        return "Turnbased RPG";
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> someImages = new ArrayList<Image>();
        someImages.add(GameWindow.importImage("something.png"));
        someImages.add(GameWindow.importImage("377.gif"));
        return someImages;
    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        int WIDTH = 800;
        int HEIGHT = 600;
        GameWindow myGameWindow = new GameWindow("Turn-Based RPG", "GameSetting", WIDTH, HEIGHT);        
    }

}