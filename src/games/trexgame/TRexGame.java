package games.trexgame;

import java.awt.Image;
import java.util.List;

import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * Main features: 
 * new battle mechanism (more stats including critical hit,
 * evasion etc.); 
 * conversation tree; 
 * player stats panel (which shows that the
 * InteractionPanel can be extended for all in-game panels);
 * graphics/fonts improvement with little code;
 * stats boosts as a reward for completing level 1;
 * Monsters can be either visible/invisible on the map (all of them can trigger
 * battle with player);
 * Easy extension of MapMode to respond to more events
 * 
 * @author Tony, Rex
 */
public class TRexGame implements IArcadeGame {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 450;
	private static final String RESOURCE_ADDRESS = "games.trexgame.resources.GameSetting";
	private static final String myFilePath = "src/games/trexgame/TTTReXXX.xml";

	@Override
	public void runGame(String userPreferences, GameSaver s) {
		GameWindow myGameWindow = new GameWindow("The T-Rex Game",
				RESOURCE_ADDRESS, WIDTH, HEIGHT, myFilePath);
		myGameWindow.changeActivePane(GameWindow.GAME);
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
		return "'An awesome game. 'nuff said.";
	}

	@Override
	public String getName() {
		return "The T-Rex Game";
	}

	/**
	 * for debugging purposes
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		GameWindow myGameWindow = new GameWindow("The T-Rex Game",
				RESOURCE_ADDRESS, WIDTH, HEIGHT, myFilePath);
	}

}
