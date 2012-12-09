package vooga.shooter.gameplay;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameplay.AbstractGame;
import vooga.shooter.gameplay.Game;
import vooga.shooter.gameplay.MainScreen;
import vooga.shooter.gameplay.SplashLevel;
import vooga.shooter.gameplay.inputInitialize.InputTeamSpriteActionAdapter;
import vooga.shooter.level_editor.Level;
import vooga.shooter.level_editor.LevelFactory;
import arcade.IArcadeGame;

public class GuysGame2 extends AbstractGame implements IArcadeGame {
    
    File myFile;
    Level myLevel;

    public GuysGame2 () {
        super();
    }
    

    protected void createGame(){
        
        myFile = new File("src/vooga/shooter/levels/level1.xml");
        Level startLevel = LevelFactory.loadLevel(myFile);
        
        setMyCurrentLevel(new SplashLevel(this, startLevel));
        
    }
    
    public static void main(String[] args) {
        AbstractGame myGame = new GuysGame2();
        myGame.runGame(null, null);
        
    }
    
    
}
