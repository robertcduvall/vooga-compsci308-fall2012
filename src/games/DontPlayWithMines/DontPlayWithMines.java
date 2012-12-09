package games.DontPlayWithMines;

import java.awt.Image;
import java.util.List;

import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * 
 * @author Tony, Rex
 */
public class DontPlayWithMines implements IArcadeGame {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 450;
	private static final String RESOURCE_ADDRESS = "games.trexgame.resources.GameSetting";

	@Override
	public void runGame(String userPreferences, GameSaver s) {
		GameWindow myGameWindow = new GameWindow("The T-Rex Game",
				RESOURCE_ADDRESS, WIDTH, HEIGHT, "");
	}

	@Override
	public List<Image> getScreenshots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getMainImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		return "An awesome game. 'nuff said.";
	}

	@Override
	public String getName() {
		return "The T-Rex Game";
	}

}
