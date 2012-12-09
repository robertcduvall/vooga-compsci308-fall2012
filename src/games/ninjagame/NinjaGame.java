package games.ninjagame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * @author Kannan Raju
 *
 */
public class NinjaGame implements IArcadeGame {

    private static final String DESCRIPTION = "A game with ninjas, only the strongest" +
    		"will succeed (tbh its easy-medium difficulty). Basic controls are the arrow keys" +
    		"and the spacebar for jump. ";
    private static final String myFirstLevelName = "src/games/ninjagame/levels/ninjagamewin";
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        start(myFirstLevelName);
    }
    
    public static void main (String[] args) {
        NinjaGame game = new NinjaGame();
        game.start(myFirstLevelName);
    }
    
    public void start(String levelName) {
        JFrame frame = new JFrame("THE NINJA GAME YEA BUDDY");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PlatformerController controller = new PlatformerController(levelName,
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> imgList = new ArrayList<Image>();
        Image ss = null;
        try {
            ss = ImageIO.read(new File("src/games/ninjagame/images/ninjagame.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find screenshot");
        }
        imgList.add(ss);
        return imgList;
    }

    @Override
    public Image getMainImage () {
        Image img = null;
        try {
            img = ImageIO.read(new File("src/games/ninjagame/images/ninjaGame.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find main image");
        }
        return img;
    }

    @Override
    public String getDescription () {
        return DESCRIPTION;
    }

    @Override
    public String getName () {
        return "NinjaGame";
    }

}
