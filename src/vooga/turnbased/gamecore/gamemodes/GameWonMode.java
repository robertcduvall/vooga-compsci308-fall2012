package vooga.turnbased.gamecore.gamemodes;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import vooga.turnbased.gamecore.GameManager;
import vooga.turnbased.gui.GameWindow;

/**
 * Mode designed to manage what happens when the game is won. 
 * @author volodymyr
 *
 */
public class GameWonMode extends GameMode {

    /**
     * Constructor for GameWonMode.
     * @param gm GameManager to which this mode belongs.
     * @param modeName String name of mode.
     * @param involvedIDs List of IDs of involved sprites.
     */
    public GameWonMode (GameManager gm, String modeName, List<Integer> involvedIDs) {
        super(gm, modeName, involvedIDs);
    }

    @Override
    public void initialize () {
        playModeEntranceSound("GameWonSound");
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void paint (Graphics g) {
        Image background = GameWindow.importImage("GameWonImage");
        Dimension paneDim = getGameManager().getPaneDimension();
        g.drawImage(background, 0, 0, paneDim.width, paneDim.height, null);
    }

    @Override
    public void update () {
    }

    @Override
    public void processMouseInput (int mousePressed, Point mousePosition, int mouseButton) {     
    }

}
