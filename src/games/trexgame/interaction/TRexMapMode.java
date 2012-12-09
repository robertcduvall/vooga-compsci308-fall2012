package games.trexgame.interaction;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import util.input.core.KeyboardController;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.MapMode;

public class TRexMapMode extends MapMode {

	public TRexMapMode(GameManager gm, String modeName,
			List<Integer> involvedIDs) {
		super(gm, modeName, involvedIDs);
	}

	public void showStats() {
		List<Integer> involvedIDs = new ArrayList<Integer>();
		involvedIDs.add(getPlayer().getID());
		involvedIDs.add(getPlayer().getID());
		flagCondition("viewstats", involvedIDs);
	}
	
	/**
	 * cheat key
	 * for debugging purposes
	 */
	public void jumpToLevelTwo() {
		List<Integer> involvedIDs = new ArrayList<Integer>();
		involvedIDs.add(getPlayer().getID());
		flagCondition("entermap2", involvedIDs);
	}

	@Override
	public void configureInputHandling() {
		super.configureInputHandling();
		try {
			getGameManager().getKeyboardController().setControl(KeyEvent.VK_A,
					KeyboardController.PRESSED, this, "showStats");
			getGameManager().getKeyboardController().setControl(KeyEvent.VK_J,
					KeyboardController.PRESSED, this, "jumpToLevelTwo");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
