package games.difangame;

import java.awt.Image;
import java.util.List;
import javax.swing.JFrame;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

public class GarfieldGame implements IArcadeGame {
    
    private static final String myFirstLevelName = "src/games/difangame/levels/difantest.xml";
    
    
    public static void main (String[] args) {
        GarfieldGame game = new GarfieldGame();
        game.start(myFirstLevelName);
    }
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        start(myFirstLevelName);
        
    }

    public void start(String levelName) {
        JFrame frame = new JFrame("Mario Style Game");
        PlatformerController controller = new PlatformerController(levelName,
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName () {
        // TODO Auto-generated method stub
        return null;
    }

}
