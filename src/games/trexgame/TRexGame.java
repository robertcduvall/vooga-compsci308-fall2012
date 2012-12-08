package games.trexgame;

import java.awt.Image;
import java.util.List;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * @author Tony, Rex
 */
public class TRexGame implements IArcadeGame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        TRexGameWindow myGameWindow = new TRexGameWindow("The T-Rex Game", "GameSetting", WIDTH, HEIGHT);
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
        return "An awesome game. 'nuff said.";
    }

    @Override
    public String getName () {
        return "The T-Rex Game";
    }

}
