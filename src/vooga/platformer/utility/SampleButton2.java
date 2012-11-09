package src.vooga.platformer.utility;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * @author Yaqi Zhang
 *
 */
public class SampleButton2 {
    public static void main (String[] args) {
        JFrame frame = new JFrame();
        GameButton gb = new GameButton();
        gb.setImage("button");
        frame.getContentPane().add(gb);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
