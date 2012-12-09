package games.geo4games.marioinminutes;

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
 * A game written using the platformer engine that requires no additional
 * classes be written. It was completed in only a few minutes to demonstrate how
 * easy the platformer engine makes it to write new games.
 * 
 * @author Grant Oakley
 * 
 */
public class MarioInMinutes implements IArcadeGame {

    private static final String FIRST_LEVEL = "src/games/geo4games/marioinminutes/levels/level1.xml";

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        JFrame frame = new JFrame("Mario in Minutes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller =
                new PlatformerController(FIRST_LEVEL, new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }
    
    // TODO delete this test method
    public static void main(String[] args) {
        MarioInMinutes mim = new MarioInMinutes();
        System.out.println(mim.getMainImage());
        mim.runGame("", null);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> screenShots = new ArrayList<Image>();
        try {
            // TODO take screenshots of the game
            screenShots.add(ImageIO.read(new File("")));
        }
        catch (IOException e) {
            /*
             * If an exception is reached, just return whatever images were
             * added so far, or an empty list
             */
        }
        return null;
    }

    @Override
    public Image getMainImage () {
        try {
            return ImageIO.read(new File("src/vooga/platformer/data/Player.png"));
        }
        catch (IOException e) {
            // TODO confirm that getMainImage should return null if no image is
            // found
            return null;
        }
    }

    @Override
    public String getDescription () {
        return "A simple, Mario-style game built in only a couple minutes using the platformer framework.";
    }

    @Override
    public String getName () {
        return "Mario in Minutes";
    }

}
