package games.DontPlayWithMines;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;

import javax.swing.ImageIcon;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gamecore.gamemodes.GameMode;
import vooga.turnbased.gui.GameWindow;

public class MyGameWinMode extends GameMode {
	private static final String myFirstImage = "src/games/DontPlayWithMines/resources/win.gif";
	
    public MyGameWinMode (GameManager gm, String modeName, List<Integer> involvedIDs) {
        super(gm, modeName, involvedIDs);
    }

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g) {
        ImageIcon background = new ImageIcon(myFirstImage);
        Dimension paneDim = getGameManager().getPaneDimension();
        g.drawImage(background.getImage(), 0, 0, paneDim.width, paneDim.height, null);		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMouseInput(int mousePressed, Point mousePosition,
			int mouseButton) {
		// TODO Auto-generated method stub
		
	}
}
