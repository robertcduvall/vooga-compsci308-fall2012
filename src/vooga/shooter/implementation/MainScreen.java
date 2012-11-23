package vooga.shooter.implementation;

import java.awt.Image;
import vooga.shooter.gameplay.Game;
import vooga.shooter.level_editor.Level;

public class MainScreen extends Level{

    private Game myGame;
    private Level myNextLevel;
    
    public MainScreen(Game game, String backgroundPath) {
        super();
        myGame = game;
        setBackgroundImage(backgroundPath);
        setNextLevel(new Level1(myGame));
    }
    
    public void startLevel () {
        
    }
    
    public boolean winningConditionsMet () {
        return false;
    }
    
}
