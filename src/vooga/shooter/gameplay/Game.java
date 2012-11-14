package vooga.shooter.gameplay;

import java.awt.Graphics;
import java.util.List;
import util.input.core.KeyboardController;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.level_editor.Level;


/**
 * Initializes the top-down shooter game and owns all sprites and levels
 * initiated throughout the course of the game.  
 * 
 * @author Tommy Petrilak
 * 
 */
public class Game {
    private List<Sprite> mySprites;
    private Level myCurrentLevel;
    private KeyboardController myController;

    private void initializeGame () {
        myController = new KeyboardController(null);
        Level firstLevel = null;
        startLevel(firstLevel);
    }

    private void startLevel (Level level) {
        myCurrentLevel = level;
        update();
    }

    private void update () {
        for (Sprite s : mySprites) {
            s.update();
        }

        // will work when Levels contain winning conditions

        // if (myCurrentLevel.winningConditionsMet(this)) {
        // startLevel(myCurrentLevel.nextLevel());
        // }
    }

    private void paint (Graphics pen) {
        for (Sprite s : mySprites) {
            s.paint(pen);
        }

    }

    /**
     * Add a sprite to the list of sprites currently existing in the Game.
     * 
     * @param sprite to be added to list of existing sprites
     */
    public void addSprite (Sprite sprite) {
        mySprites.add(sprite);
    }

}
