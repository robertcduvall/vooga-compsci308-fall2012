package vooga.shooter.gameplay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import javax.swing.ImageIcon;
import vooga.shooter.level_editor.Level;
import vooga.shooter.gameObjects.Sprite;
import vooga.shooter.gameObjects.Enemy;
import vooga.shooter.gameplay.Game;


/**
 * First level (initializes enemies, sets winning conditions)
 * 
 * @author Tommy Petrilak
 * 
 */
public class LostGame extends Level {

    private static final String BACKGROUND_IMAGEPATH = "vooga/shooter/images/galaxy.png";

    private AbstractGame myGame;
    private Level myNextLevel;

    public LostGame (AbstractGame game) {
        super();
        myGame = game;
        myNextLevel = null;
        setBackgroundImage(BACKGROUND_IMAGEPATH);
    }

    @Override
    public boolean winningConditionsMet () {
        return false;
    }

}