package games.JesseGame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import vooga.shooter.gameplay.AbstractGame;
import vooga.shooter.level_editor.Level;
import vooga.shooter.level_editor.LevelFactory;

/**
 * This code takes advantage of the level factory and creating a custom level
 * to use in the game.  It also uses the ability to start a game from a custom 
 * level chosen by the programmer. The game class uses the input team's packages
 * to get keyboard input from the user. We also implement the AI that the enemies employ,
 * chase, bounce, and random movement. We can also customize sprite dimensions, appearance, and
 * movement within the level editor.
 *
 * @author Jesse Starr, Tommy Petrilak
 *
 */
public class AlienShooter extends AbstractGame{
    Level myStartLevel;
    
    @Override
    protected void createGame () {
        createLevels();
        setMyCurrentLevel(myStartLevel);
    }
    
    public static void main(String[] args){
        AbstractGame myGame = new AlienShooter();
        myGame.runGame(null, null);
    }
    
    private void createLevels(){
        File level1file = new File("src/games/JesseGame/jlevel1.xml");
        File level2file = new File("src/games/JesseGame/level2.xml");
        myStartLevel = LevelFactory.loadLevel(level1file);
        Level level2 = LevelFactory.loadLevel(level2file);
        myStartLevel.setNextLevel(level2);
    }
    
    @Override
    public String getName(){
        return "Alien Shooter";
    }
    
    @Override
    public String getDescription(){
        return "Kill the aliens!";
    }

    @Override
    public Image getMainImage () {
        File imgpath = new File("src/games/JesseGame/galaxy.gif");
        Image i = null;
        try {
            i = ImageIO.read(imgpath);
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return i;
    }
}
