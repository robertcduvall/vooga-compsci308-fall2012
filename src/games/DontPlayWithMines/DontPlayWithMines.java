package games.DontPlayWithMines;

import java.awt.Image;
import java.util.List;

import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

public class DontPlayWithMines implements IArcadeGame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final String RESOURCE_ADDRESS = "games.trexgame.resources.GameSetting";
	private final String myFilePath = "src/games/DontPlayWithMines/myLevel.xml";

	@Override
	public void runGame(String userPreferences, GameSaver s) {
		GameWindow myGameWindow = new GameWindow("A Game by Game of Game",
				RESOURCE_ADDRESS, WIDTH, HEIGHT, myFilePath);
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
		return "Walk carefully";
	}

	@Override
	public String getName() {
		return "Mine Walker";
	}

}
