package games.robssnake;

import games.robssnake.strategy.DoNothingUpdateStrategy;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;
import vooga.platformer.gameobject.Player;
import vooga.platformer.gameobject.UpdateStrategy;
import vooga.platformer.gameobject.strategy.movement.StopStrategy;
import vooga.platformer.gameobject.strategy.update.GravityStrategy;
import vooga.platformer.level.Level;
import vooga.platformer.level.levelplugin.GravityPlugin;
import vooga.platformer.util.camera.StaticCamera;
import vooga.platformer.util.enums.Direction;


/**
 * 
 * @author Robert Bruce
 * This game uses the platformer level editor with custom
 * conditions and physics / plugins.
 */
public class RunSnakeGame implements IArcadeGame{
    private String levelOneLocation = "src/games/robssnake/levels/level1.xml";
    public static void main (String[] args) {
        new RunSnakeGame().runGame("", null);
    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        JFrame frame = new JFrame ("RunSnakeGame");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // needed for proper handling by Arcade
        PlatformerController controller = new PlatformerController(levelOneLocation,
                new SnakeInputInitializer());
        Level myLevel = controller.getLevel();
        Player myPlayer = myLevel.getPlayer();
        myLevel.addPlugin(new GravityPlugin(0, Direction.UP));
        myLevel.setCamera(new StaticCamera(myLevel.getDimension(), new Rectangle(myLevel.getDimension().width,
                myLevel.getDimension().height)));
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> myScreenShots = new ArrayList<Image>();
        try {
            myScreenShots.add(ImageIO.read(new File("src/games/halo/images/Screen Shot 2012-12-09 at 12.02.52 PM.png")));
        }
        catch (IOException e) {
            System.out.println("Something went wrong loading a screenshot.");
        }
        return myScreenShots;
    }

    @Override
    public Image getMainImage () {
        try {
            return ImageIO.read(new File("src/games/halo/images/halo-4-helmet.jpg"));
        }
        catch (IOException e) {
            System.out.println("Something went wrong loading the main image.");
            return null;
        }
    }

    @Override
    public String getDescription () {
        return "RunSnakeGame isn't done yet. Give it some time.";
    }

    @Override
    public String getName () {
        return "RunSnakeGame";
    }

}
