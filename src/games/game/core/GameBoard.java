package games.game.core;

import games.game.levels.LevelManager;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/*
 * This class doesn't really pull its weight but its essential,
 * since it overrides JPanel's paint method. There are good
 * reasons for not combining it with the LevelManager class.
 */

/**
 * This class represents the game board.
 * 
 * @author Paul Dannenberg
 * 
 */
public class GameBoard extends JPanel {

    private static final long serialVersionUID = -3528519211577278934L;
    private LevelManager myLevelManager;

    /**
     * Creates a new JPanel that will act as a container
     * for all other game items.
     * 
     * @param gameSize The size of the panel on which the
     *        game will be played.
     */
    public GameBoard(Dimension gameSize) {
        setSize(gameSize);
        myLevelManager = new LevelManager(this);
        setCursor();
    }

    /**
     * Paints the game.
     */
    @Override
    public void paint(Graphics pen) {
        super.paint(pen);
        myLevelManager.paint((Graphics2D) pen);

    }

    /**
     * This method is called every frame. It allows all
     * game objects to react based on game updates rather
     * than user input.
     * 
     * @param timeElapsed
     */
    public void update() {
        myLevelManager.update();
    }

    /**
     * Swaps the cursor to a cross hair. Essential for a
     * first person shooter!
     */
    private void setCursor() {
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

}
