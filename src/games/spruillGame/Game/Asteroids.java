package games.spruillGame.Game;

import games.spruillGame.levels.Level1;
import games.spruillGame.levels.LoseGame;
import java.awt.Image;
import java.util.List;
import vooga.shooter.gameplay.Game;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * This game has 4 levels of an increasing number of asteroids randomly
 * assaulting you in a field through which you must either dodge or shoot your
 * way through. As to what design features this implements of the vooga code
 * base... this implements the interfaces needed to plug into the arcade, and it
 * utilizes Level architecture from shooters along with that Game class to
 * control how the levels shift, display to the screen, update, etc.. The
 * ParticleEngine systems are utilized in the game class, so this inherits that
 * use as well.
 * 
 * @author David Spruill
 * 
 */
@SuppressWarnings("serial")
public class Asteroids extends Game implements IArcadeGame {

    /**
     * Contructs this object
     */
    public Asteroids () {
        super();
    }

    /**
     * The method called by the arcade that instantiates the game
     * @param userPreferences the userpreferences to be passed into the game
     * @param s the gamesaver to save/load this game's state
     */
    @Override
    public void runGame (String userPreferences, GameSaver s) {
        super.runGame(userPreferences, s);
        super.startLevel(new Level1(this));
        super.setLoseLevel(new LoseGame(this));
    }

    @Override
    public List<Image> getScreenshots () {
        return null;
    }

    @Override
    public Image getMainImage () {
        return null;
    }

    @Override
    public String getDescription () {
        return "Dodge or shoot your way through a dangerous asteroid field.";
    }

    @Override
    public String getName () {
        return "Asteroids";
    }

}
