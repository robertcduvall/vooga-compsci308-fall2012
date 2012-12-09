package games.spruillGame.Game;

import games.spruillGame.levels.Level1;
import games.spruillGame.levels.LoseGame;
import java.awt.Image;
import java.util.List;
import vooga.shooter.gameplay.Game;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


@SuppressWarnings("serial")
public class Asteroids extends Game implements IArcadeGame {

    public static void main (String args[]) {
        Asteroids a = new Asteroids();
        a.runGame(null, null, new Level1(a));
        a.startLevel(new Level1(a));
        a.setLoseLevel(new LoseGame(a));
    }

    public Asteroids () {
        super();
    }

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        super.runGame(userPreferences, s);
    }

    @Override
    public List<Image> getScreenshots () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Image getMainImage () {
        // TODO Auto-generated method stub
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
