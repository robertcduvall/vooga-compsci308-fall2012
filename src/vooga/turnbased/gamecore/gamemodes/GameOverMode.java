package vooga.turnbased.gamecore.gamemodes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;

import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gui.GameWindow;

public class GameOverMode extends GameMode {

    public GameOverMode (GameManager gm, Class modeObjectType, List<Integer> involvedIDs) {
        super(gm, modeObjectType, involvedIDs);
    }

    @Override
    public void pause () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void initialize () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void paint (Graphics g) {
        Image background = GameWindow.importImage("GameOverImage");
        Dimension paneDim = getGameManager().getPaneDimension();
        g.drawImage(background, 0, 0, paneDim.width, paneDim.width, null);
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void processMouseInput (int mousePressed, Point myMousePosition, int myMouseButton) {
        
    }

}