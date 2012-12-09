package games.JesseGame;

import java.io.File;
import vooga.shooter.gameplay.AbstractGame;
import vooga.shooter.gameplay.SplashLevel;
import vooga.shooter.level_editor.Level;
import vooga.shooter.level_editor.LevelFactory;

/**
 * This code takes advantage of the level factory and creating a custom level
 * to use in the game.  It also uses the ability to start a game from a custom 
 * level chosen by the programmer. The game class uses the input team's packages
 * to get keyboard input from the user.
 *
 * @author Jesse Starr, Tommy Petrilak
 *
 */
public class AlienShooter extends AbstractGame{
    File myFile;
    Level myStartLevel;
    
    @Override
    protected void createGame () {
        myFile = new File("src/vooga/shooter/levels/level1.xml");
        myStartLevel = LevelFactory.loadLevel(myFile);
        
        setMyCurrentLevel(new SplashLevel(this, myStartLevel));
    }
    
    public static void main(String[] args){
        AbstractGame myGame = new AlienShooter();
        myGame.runGame(null, null);
    }
    
    @Override
    public String getName(){
        return "Alien Shooter";
    }
    
    @Override
    public String getDescription(){
        return "Kill the aliens!";
    }
}
