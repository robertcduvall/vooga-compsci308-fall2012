package vooga.turnbased.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class DisplayPane extends JPanel implements KeyListener {

    private GameWindow myGameWindow;
    
	public DisplayPane(GameWindow gameWindow){
		myGameWindow = gameWindow;
		setSize(myGameWindow.getSize());
		enableFocus();
	}
	
	public GameWindow getGameWindow(){
		return myGameWindow;
	}

    public void enableFocus() {
    	setFocusable(true);
        addComponentListener( new ComponentAdapter() {
            @Override
            public void componentShown( ComponentEvent e ) {
                ((JComponent) e.getSource()).requestFocusInWindow();
            }
        });
    }
    
    public void initialize() {
    }
}
