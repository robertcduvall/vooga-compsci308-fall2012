package games.lotrsimple;

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
 * This is the game submission for Michael Deng and Jei Min Yoo.
 * It is a platformer game based off of the Lord of the Rings.
 * 
 * This is the SIMPLE version.. showing that the platformer game 
 * engine can create fun games quickly. 
 * 
 * @author Michael Deng
 * @author Jei Min Yoo
 * 
 * 
 *         This game demonstrates the use of the platormer's game engine.
 *         In this game, we are demonstrating the game engine's ability to:<br>
 *         - create a bunch of levels <br>
 *         - set different winning/losing conditions for each level <br>
 *         - use our own characters and images <br>
 *         - use the inputs teams functionality <br>
 *         - make fun games quickly <br>
 *
 */
public class LOTRSimpleGame implements IArcadeGame {

    private static final String DESCRIPTION = "This is the simple LOTR Game!";
    private static final String NAME = "The Simple Lord of the Rings Game";
//    private static final String MAIN_IMAGE = "title.jpg"; // title picture
    
    private static final String myFirstLevelName = "src/games/lotrsimple/levels/game.xml";

    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        start(myFirstLevelName);
    }
    
    
    
    public void start(String levelName) {
        JFrame frame = new JFrame("The Simple Lord of the Rings Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller = new PlatformerController(levelName,
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }
    
    

    @Override
    public List<Image> getScreenshots () {
//        List<Image> screenShots = new ArrayList<Image>();
//        screenShots.add(getMainImage());
//        return screenShots;
        
        List<Image> imgList = new ArrayList<Image>();
        Image ss = null;
        try {
            ss = ImageIO.read(new File("src/games/lotrsimple/images/title.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find screenshot");
        }
        imgList.add(ss);
        return imgList;
        
    }

    @Override
    public Image getMainImage () {
//        ImageIcon mainImage = new ImageIcon(getClass().getResource(MAIN_IMAGE));
//        return mainImage.getImage();
        
        Image img = null;
        try {
            img = ImageIO.read(new File("src/games/lotrsimple/images/title.jpg"));
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
        return NAME;

    }

    
    
    
    //*****************************
    // TEST CODE
    
    public static void main (String[] args) {
        LOTRSimpleGame game = new LOTRSimpleGame();
        game.start(myFirstLevelName);
    }
    
    
}
