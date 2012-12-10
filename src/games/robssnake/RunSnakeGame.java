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
 * 
 */
public class RunSnakeGame implements IArcadeGame{
    private String levelOneLocation = "src/games/robssnake/levels/level1.xml";
    public static void main (String[] args) {
        new RunSnakeGame().runGame("", null);
    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        JFrame frame = new JFrame ("Mario's Mushrooms");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        PlatformerController controller = new PlatformerController(levelOneLocation,
                new SnakeInputInitializer());        
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(controller.getLevel().getDimension());
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
        return "Mario's Mushrooms is a take on the classic game of snake. " +
        		"As Mario, once you start moving, you can't stop " +
        		"until you've gotten all the mushrooms!" +
        		" As you progress, the levels get harder and harder." +
        		" See how far you can make it, and make sure you avoid the walls! \n\n\n" +
        		"Game made by Robert Bruce using the Platformer Engine.";
    }

    @Override
    public String getName () {
        return "Mario's Mushrooms";
    }

}
