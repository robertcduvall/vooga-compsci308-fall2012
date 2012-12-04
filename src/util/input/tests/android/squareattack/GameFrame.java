package util.input.tests.android.squareattack;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;


/**
 * 
 * @author rwb392
 * 
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame {

    private int myWidth = 600;
    private int myHeight = 600;

    /**
     * Constructor
     * 
     * @throws SecurityException Exception.
     * @throws NoSuchMethodException Exception.
     */
    public GameFrame () throws SecurityException, NoSuchMethodException {

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        setSize(myWidth, myHeight);
        setTitle("SampleGame");

        // swap the following two lines two switch between the two demos.

        AndroidDrawGame testGame = new AndroidDrawGame();
        // TwoController testGame = new TwoController();
        add(testGame, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        testGame.startGame();

    }

}
