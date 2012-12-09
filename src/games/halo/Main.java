package games.halo;

import java.awt.Image;
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


/**
 * 
 * @author Sam Rang AND Zach Michealov
 *
 */
public class Main implements IArcadeGame{
    public static void main (String[] args) {
        new Main().runGame("", null);
    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        JFrame frame = new JFrame ("Halo: Combat Devolved");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller = new PlatformerController("src/games/halo/level0.xml", 
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> ss = new ArrayList<Image>();
        try {
            ss.add(ImageIO.read(new File("src/games/halo/images/Screen Shot 2012-12-09 at 12.02.52 PM.png")));
            ss.add(ImageIO.read(new File("src/games/halo/images/Screen Shot 2012-12-09 at 12.06.22 PM.png")));
            ss.add(ImageIO.read(new File("src/games/halo/images/Screen Shot 2012-12-09 at 12.07.42 PM.png")));
        }
        catch (IOException e) {
            /*
             * well darn....
             */
        }
        return ss;
    }

    @Override
    public Image getMainImage () {
        try {
            return ImageIO.read(new File("src/games/halo/images/halo-4-helmet.jpg"));
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public String getDescription () {
        String desc = "Halo: Combat Devolved\nBasically the best game ever. Although many of the characters" +
        		" may look similar to the popular title by Bungie, this has no relation to the game Halo: Combat Evolved. " +
        		"Ours is better.";
        return desc;
    }

    @Override
    public String getName () {
        return "Halo: Combat Devolved";
    }

}
