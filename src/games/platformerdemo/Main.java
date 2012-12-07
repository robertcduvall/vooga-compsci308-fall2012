package games.platformerdemo;

import java.awt.Dimension;
import javax.swing.JFrame;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.SimpleOnePlayerInputInitializer;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;


/**
 * A class that creates a JFrame to hold the Game object
 * and launches the game.
 * 
 * @author Niel
 * @author Yaqi
 */

public class Main {

    public static void main (String[] args) {
        JFrame frame = new JFrame("Demo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller = new PlatformerController("src/vooga/platformer/data/Playerinzonetest.xml",
                new KeyControllerOnePlayerInputInitializer());

        frame.getContentPane().add(controller);
        frame.pack();
        paintString(controller);
        frame.setVisible(true);
    }

    public static void paintString (PlatformerController controller) {
        Dimension canvasSize = controller.getSize();
        controller.paintString("M - Menu", canvasSize.width * 3 / 5,canvasSize.height / 4);
        controller.paintString("<- -> - Move left and right", canvasSize.width * 3 / 5,
                canvasSize.height / 4 + 15);
        controller.paintString("UP - Jump", canvasSize.width * 3 / 5,
                canvasSize.height / 4 + 30);
        controller.paintString("Space - Shoot", canvasSize.width * 3 / 5,
                               canvasSize.height / 4 + 45);
    }
}
