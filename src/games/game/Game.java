package games.game;

import games.game.core.GameBoard;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.Timer;
import arcade.IArcadeGame;
import arcade.gamemanager.GameSaver;


/**
 * This game is a first person shooter - something none of the game
 * engines supported. I started off ambitious and...well... I realize
 * I probably should have picked a game genre supported by the engine
 * but I was much too excited about this. Nevertheless, I did use
 * several utility classes from the repository, namely the particle
 * engine, the sound utility and the input team's controllers.
 * 
 * @author Paul Dannenberg
 * @author Robert C. Duvall
 * 
 */

public class Game extends JFrame implements IArcadeGame {

    private static final String GAME_TITLE = "Paul's Game";
    private static final long serialVersionUID = -3179467003801103750L;
    private static final Dimension GAME_SIZE = new Dimension(800, 500);
    public static final int ONE_SECOND = 1000;
    public static final int FRAMES_PER_SECOND = 20;

    private Timer myTimer;
    private GameBoard myBoard;

    /**
     * Creates a new first person shooter game.
     */
    public Game() {
        super(GAME_TITLE);
        setSize(GAME_SIZE);
        myBoard = createBoard(GAME_SIZE);
    }

    private GameBoard createBoard(Dimension boardSize) {
        GameBoard board = new GameBoard(boardSize);
        add(board);
        return board;
    }

    /**
     * Starts playing the game!
     */
    public void play() {

        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        repaint();
                        myBoard.update();
                    }
                });
        myTimer.start();
        setVisible(true);
    }

    @Override
    public void runGame(String userPreferences, GameSaver s) {
        play();
    }

    @Override
    public List<Image> getScreenshots() {
        // If I had any, they would only spoil the arcade. :'(
        return null;
    }

    @Override
    public Image getMainImage() {
        return null;
    }

    @Override
    public String getDescription() {
        return "This game isn't very fun.";
    }

}
