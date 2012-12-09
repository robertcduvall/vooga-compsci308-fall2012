package games.zackguygame;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameplay.AbstractGame;
import vooga.shooter.gameplay.Game;
import vooga.shooter.gameplay.MainScreen;
import vooga.shooter.gameplay.SplashLevel;
import vooga.shooter.gameplay.inputInitialize.InputTeamSpriteActionAdapter;
import vooga.shooter.level_editor.Level;
import vooga.shooter.level_editor.LevelFactory;
import arcade.IArcadeGame;
/**
 * Primary class for shooter game demo.  This game shows off the capabilities of AI and 
 * the Particle Engine as well as the ability to customize Sprite appearance and Enemy behavior 
 * within the Level Editor.  The Level Editor allows the user to set all Enemy characteristics 
 * including AI type as well as customize
 * the starting background image and initial conditions for the Player.
 * @author Zachary Hopping
 * @author Guy Tracy
 *
 */
public class ZackGuyGame extends AbstractGame implements IArcadeGame {
    
    File myFile;
    Level myLevel;
    private static final String DESCRIPTION = "Zack and Guy's Shooter Demo";
    private static final String NAME = "JUSTICE BLASTER";

    public ZackGuyGame () {
        super();
    }
    

    protected void createGame(){
        Level level1 = LevelFactory.loadLevel(new File("src/vooga/shooter/levels/ggtLevel1.xml"));
        Level level2 = LevelFactory.loadLevel(new File("src/vooga/shooter/levels/ggtRandomLevel2.xml"));
        Level level3 = LevelFactory.loadLevel(new File("src/vooga/shooter/levels/ggtChaseLevel3.xml"));
        level1.setNextLevel(level2);
        level2.setNextLevel(level3);
        setMyCurrentLevel(level1); 
    }
    
    public static void main(String[] args) {
        System.out.println("testing testing");
        AbstractGame myGame = new ZackGuyGame();
        myGame.runGame(null, null);
    }

    @Override
    public List<Image> getScreenshots () {
        List<Image> screenshots = new ArrayList<Image>();
        Image screenshot_one = null;
        Image screenshot_two = null;
        try {
            screenshot_one = ImageIO.read(new File("src/games/zackguygame/images/screenshot_1.jpg"));
            screenshot_two = ImageIO.read(new File("src/games/zackguygame/images/screenshot_2.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find screenshots.");
        }
        screenshots.add(screenshot_one);
        screenshots.add(screenshot_two);
        return screenshots;
    }
    
    @Override
    public Image getMainImage () {
        Image img = null;
        try {
            img = ImageIO.read(new File("src/games/zackguygame/images/mainImage.jpg"));
        }
        catch (IOException e) {
            System.out.println("Could not find main image.");
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
    
    
}