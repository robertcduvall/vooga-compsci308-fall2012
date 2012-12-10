package games.DontPlayWithMines;

import java.awt.Image;
import java.util.List;

import vooga.turnbased.gui.GameWindow;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;

/**
 * This game shows off a number of features of our RPG framework. The first is
 * that no mode is intrinsically special, for instance there is no battle mode
 * in this game, instead every game is a combination of whatever modes the user
 * desires. Second is that both modes and game objects are easily extended to
 * provide functionality that has not been predefined. This game showcases that
 * by way of the custom MapTeleportObject and extended Map Modes that remap the
 * controls. Zero code is actually necessary to build a fully functional game
 * and additional functionality requires only a minimal amount of coding at
 * that. Given a level editor with a better UI, making games would be made
 * intuitively simply and very easy to do. It is also (not obviously) shown that
 * the game is able to manage related objects that have a game representation in
 * multiple modes -- the painted image of the sign and the conversation dialogue
 * that follows. Lastly aside from camera/key controls, player is not special.
 * We could parse player from our xml using a sprite tag, it's the exact same.
 * This demonstrates the core principle of our design that modes, sprites, and
 * objects are all treated the same, totally generic. This means that adding new
 * ones requires only adding a new class without modifications elsewhere in the
 * code, i.e. a new map object requires no additional modification to map mode
 * or the XML reader.
 * 
 * @author David Howdyshell
 * 
 */
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
