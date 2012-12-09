package games.nyancatgame;

import java.awt.Image;
import java.util.List;
import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * Runs NyanCatGame and allows Arcade to play NyanCatGame.
 * 
 * This game showcases the ability of the game-maker to create their own kinds of battles by only
 * writing one class (as an extension of BattleObject). 
 * 
 * @author Jenni Mercado
 *
 */

public class NyanCatGame implements IArcadeGame {

    private final static String loadLevelLocation = "src/games/nyancatgame/NyanCatGame.xml";
    private final int width = 800;
    private final int height = 600;

    @Override
    public void runGame (String userPreferences, GameSaver s) {
        start();
    }

    public static void main (String[] args) {
        NyanCatGame game = new NyanCatGame();
        game.start();
    }

    private void start () {
        GameWindow myGameWindow = new GameWindow("NyanCat saves the day!",
                "GameSetting", width, height, loadLevelLocation);
        myGameWindow.changeActivePane(GameWindow.GAME);

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
        return "This is an implementation of the RPG-turnbased game engine, starring NyanCat. The player controls the daring NyanCat as he battles through legions of evil toasters to save the rainbow, and with it, the universe!";
    }

    @Override
    public String getName () {
        return "NyanCat saves the day!";
    }

}
