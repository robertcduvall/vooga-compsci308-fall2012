package src.vooga.shooter.utility;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * @author Yaqi Zhang
 *
 */
public class SampleButton1 {
    public static void main (String[] args) {
        JFrame frame = new JFrame();
        GameButton gb = new GameButton();
        gb.setImage("ball");
        frame.getContentPane().add(gb);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
