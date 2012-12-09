package games.trexgame.interaction;

import java.awt.event.KeyEvent;
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
		System.out.println("yes");
	}

	@Override
	public void configureInputHandling() {
		super.configureInputHandling();
		try {
			getGameManager().getKeyboardController().setControl(KeyEvent.VK_A,
					KeyboardController.PRESSED, this, "showStats");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
