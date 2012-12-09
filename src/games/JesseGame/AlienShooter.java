package games.JesseGame;

import java.io.File;
import vooga.shooter.gameplay.AbstractGame;
import vooga.shooter.gameplay.SplashLevel;
import vooga.shooter.level_editor.Level;
import vooga.shooter.level_editor.LevelFactory;
import arcade.IArcadeGame;

public class AlienShooter extends AbstractGame implements IArcadeGame{
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
}
