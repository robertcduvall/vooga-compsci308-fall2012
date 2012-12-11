package games.DontPlayWithMines;

import java.awt.event.KeyEvent;
import java.util.List;

import util.input.core.KeyboardController;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.MapMode;

public class MapReverseMode extends MapMode {
    public MapReverseMode (GameManager gm, String modeName, List<Integer> involvedIDs) {
        super(gm, modeName, involvedIDs);
    }
	
    @Override
    public void configureInputHandling () {
    	getGameManager().resetControllers();
        try {
            getGameManager().getKeyboardController().setControl(KeyEvent.VK_LEFT, KeyboardController.PRESSED,
                    getPlayer(), "moveRight");
            getGameManager().getKeyboardController().setControl(KeyEvent.VK_UP, KeyboardController.PRESSED,
            		getPlayer(), "moveDown");
            getGameManager().getKeyboardController().setControl(KeyEvent.VK_RIGHT, KeyboardController.PRESSED,
            		getPlayer(), "moveLeft");
            getGameManager().getKeyboardController().setControl(KeyEvent.VK_DOWN, KeyboardController.PRESSED,
            		getPlayer(), "moveUp");
            getGameManager().getKeyboardController().setControl(KeyEvent.VK_R, KeyboardController.PRESSED,
            		getPlayer(), "toggleRunning");
        }
        catch (NoSuchMethodException e) {
            System.out.println("A method was called that does not exist!");
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
