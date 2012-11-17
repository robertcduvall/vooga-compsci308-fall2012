package vooga.turnbased.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import util.input.core.KeyboardController;
import util.input.core.MouseController;
import util.input.factories.ControllerFactory;

@SuppressWarnings("serial")
public abstract class DisplayPane extends JPanel implements KeyListener {
	private GameWindow myGameWindow;
	private KeyboardController myKeyboardController;
	private MouseController myMouseController;

	public DisplayPane(GameWindow gameWindow) {
		myGameWindow = gameWindow;
		setSize(myGameWindow.getSize());
		enableFocus();
		setControllers();
	}

	private void setControllers() {
		myKeyboardController = (KeyboardController) ControllerFactory.createKeyboardController(this);
		myMouseController = (MouseController) ControllerFactory.createMouseController(this);
	}
	
	public KeyboardController getKeyboardController() {
		return myKeyboardController;
	}
	
	public MouseController getMouseController() {
		return myMouseController;
	}
	
	public GameWindow getGameWindow() {
		return myGameWindow;
	}

	public void enableFocus() {
		setFocusable(true);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				((JComponent) e.getSource()).requestFocusInWindow();
			}
		});
	}

	public void initialize() {
	}
}
