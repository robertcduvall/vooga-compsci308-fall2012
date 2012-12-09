package games.AdventureRPG;

import java.awt.Image;
import java.util.List;

import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * @author Michael Elgart
 *
 */
public class AdventureGame implements IArcadeGame {
	private final String myFilePath = "src/games/AdventureRPG/AdventureRPGMap.xml";
    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        GameWindow myGameWindow = new GameWindow("AdventureLand", "GameSetting", WIDTH, HEIGHT, myFilePath);

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
        return "An adventure RPG that demonstrates the flexibility of the RPG's framework battle aspects";
    }

    @Override
    public String getName () {
        return "Adventure Land";
    }

}
