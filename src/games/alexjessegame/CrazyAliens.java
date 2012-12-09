package games.alexjessegame;

import games.JesseGame.AlienShooter;
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
 * @author Alex Browne, Jesse starr
 *
 */
public class CrazyAliens extends AbstractGame{
Level myStartLevel;
    
    @Override
    protected void createGame () {
        createLevels();
        setMyCurrentLevel(myStartLevel);
    }
    
    public static void main(String[] args){
        AbstractGame myGame = new CrazyAliens();
        myGame.runGame(null, null);
    }
    
    private void createLevels(){
        File level1file = new File("src/games/alexjessegame/crazylevel1.xml");
        File level2file = new File("src/games/alexjessegame/nowyoureintrouble.xml");
        myStartLevel = LevelFactory.loadLevel(level1file);
        Level level2 = LevelFactory.loadLevel(level2file);
        myStartLevel.setNextLevel(level2);
    }

    @Override
    public Image getMainImage () {
        File imgpath = new File("src/games/alexjessegame/galaxy.gif");
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

    @Override
    public String getDescription () {
        return "Aliens are after you and you need to escape!";
    }

    @Override
    public String getName () {
        return "Crazy Aliens";
    }

}
