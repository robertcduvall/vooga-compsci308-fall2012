package games.nielgame;

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
import vooga.platformer.gameobject.LevelGoalZone;
import vooga.platformer.level.Level;
import vooga.platformer.level.condition.DestroySpecificObjectCondition;
import vooga.platformer.level.condition.PlayerInZoneCondition;
import vooga.platformer.level.levelplugin.GravityPlugin;
import vooga.platformer.level.levelplugin.SimpleBackgroundPainter;
import vooga.platformer.levelfileio.LevelFileIOException;
import vooga.platformer.util.camera.StaticCamera;
import vooga.platformer.util.enums.Direction;


/**
 * This game demonstrates the extensible nature of LevelPlugins and Conditions. I wrote a new LevelPlugin
 * to provide gravity, used it to apply gravity upwards, and wrote a new Condition to let the player win
 * the game by reaching a certain zone in the level. I integrated these new features into the game without
 * touching any existing Level code and using only a few lines of "factory code". See the README for more
 * details.
 * @author Niel Lebeck
 *
 */
public class ReverseGravityGame implements IArcadeGame {
    
    private static final String myFirstLevelName = "src/games/nielgame/levels/reverse-gravity-5.xml";
    
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        start(myFirstLevelName);
    }
    
    public static void main (String[] args) {
        ReverseGravityGame game = new ReverseGravityGame();
        game.start(myFirstLevelName);
    }
    
    public void start(String levelName) {
        JFrame frame = new JFrame("Niel's Reverse Gravity Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller = new PlatformerController(levelName,
                new KeyControllerOnePlayerInputInitializer());
        
        Level l = controller.getLevel();
        l.setCamera(new StaticCamera(new Dimension(800, 600), new Rectangle(l.getDimension().width,
                l.getDimension().height)));
        l.addPlugin(new SimpleBackgroundPainter(new File("src/games/nielgame/levels/opposite-land-final-bg.png")));
        l.addCondition(new DestroySpecificObjectCondition("src/games/nielgame/levels/lose-level.xml", 7));

        
        /*
         * The next few lines add my new LevelPlugin and new Condition to the level. Note that only
         * a few lines of "factory code" are needed, and the Level class does not need to be changed
         * at all. Also, note that this plugin and condition completely determine the behavior of
         * the level: gravity is applied upwards, and the player must reach a level goal zone to win.
         */
        l.addPlugin(new GravityPlugin(0.5, Direction.UP));
        l.addCondition(new PlayerInZoneCondition());
        
        //The PlayerInZoneCondition assumes the existence of a level goal GameObject. There should be
        //the ability to add a level goal object in the level editor, but that functionality did not
        //exist when I first designed my level, so it is performed here.
        try {
            File levelGoalImage = new File("src/games/nielgame/images/blank_transparent.png");
            l.addGameObject(new LevelGoalZone(560.0, 100.0, 30.0, 50.0, Integer.MAX_VALUE, levelGoalImage,"src/games/nielgame/levels/win-level.xml"));
        }
        catch (IOException e) {
            throw new LevelFileIOException("Could not find file", e);
        }
        
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> imgList = new ArrayList<Image>();
        Image ss = null;
        try {
            ss = ImageIO.read(new File("src/games/nielgame/images/reverse-gravity-ss.png"));
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
            img = ImageIO.read(new File("src/games/nielgame/images/mario-reverse-gravity.png"));
        }
        catch (IOException e) {
            System.out.println("Could not find main image");
        }
        return img;
    }

    @Override
    public String getDescription () {
        return "A platforming game that takes place in opposite land, where gravity pulls the player" +
        		"not down but up";
    }

    @Override
    public String getName () {
        return "Niel's Reverse Gravity Game";
    }

}
