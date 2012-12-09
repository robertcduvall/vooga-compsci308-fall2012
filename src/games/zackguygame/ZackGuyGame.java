package games.zackguygame;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
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

public class ZackGuyGame extends AbstractGame implements IArcadeGame {
    
    File myFile;
    Level myLevel;

    public ZackGuyGame () {
        super();
    }
    

    protected void createGame(){
        
        //myFile = new File("src/vooga/shooter/levels/testLevelGuy.xml");
        //Level level1 = LevelFactory.loadLevel(myFile);
        Level level1 = LevelFactory.loadLevel(new File("src/vooga/shooter/levels/ggtLevel1.xml"));
        Level level2 = LevelFactory.loadLevel(new File("src/vooga/shooter/levels/ggtLevel2.xml"));
        Level level3 = LevelFactory.loadLevel(new File("src/vooga/shooter/levels/ggtLevel3.xml"));
        
       // Level level1 = new Level();
        
        //Sprite myEnemy = new Enemy();
        //Sprite myPlayer = new Player();
        //level1.addSprite(myEnemy);
       // level1.setBackgroundImage("vooga/shooter/images/spaceship.gif");
        
       // level1.setPlayer(player);

        
        level1.setNextLevel(level2);
        level2.setNextLevel(level3);
        setMyCurrentLevel(level1);
        //setMyCurrentLevel(new SplashLevel(this, startLevel));
        
    }
    
    public static void main(String[] args) {
        System.out.println("testing testing");
        AbstractGame myGame = new ZackGuyGame();
        myGame.runGame(null, null);
    }
    
    
}