package games.geo4games.movingbricks;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;
import vooga.platformer.gameobject.GameObject;
import vooga.platformer.level.Level;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * A game written to demonstrate adding a strategy to a GameObject.
 * 
 * @author Grant Oakley
 * 
 */
public class MovingBricks implements IArcadeGame {

    private static final String FIRST_LEVEL = "src/games/geo4games/movingbricks/levels/level1.xml";

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        JFrame frame = new JFrame("Moving Bricks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller =
                new PlatformerController(FIRST_LEVEL, new KeyControllerOnePlayerInputInitializer());
        Level myLevel = controller.getLevel();
        List<GameObject> myGameObjects = myLevel.getObjectList();
        for (GameObject g : myGameObjects) {
            if (g.getId() == 4) {
                g.addStrategy(new SideToSideScrollStrategy(g, 175, 300, 1));
            }
            if (g.getId() == 5) {
                g.addStrategy(new SideToSideScrollStrategy(g, 390, 510, 1));
            }
        }
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

    // TODO delete this test method
    public static void main (String[] args) {
        MovingBricks mim = new MovingBricks();
        mim.runGame("", null);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> screenShots = new ArrayList<Image>();
        try {
            // TODO take screenshots of the game
            screenShots.add(ImageIO.read(new File("src/games/geo4games/movingbricks/iamges/screenshot1.png")));
        }
        catch (IOException e) {
            /*
             * If an exception is reached, just return whatever images were
             * added so far, or an empty list
             */
        }
        return screenShots;
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
        return "A very simple game to illustrate update strategies.";
    }

    @Override
    public String getName () {
        return "Moving Bricks";
    }

}
