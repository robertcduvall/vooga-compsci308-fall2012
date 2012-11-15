package games.platformerdemo;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import util.input.core.Controller;
import util.input.core.KeyboardController;
import vooga.platformer.core.PlatformerController;

/**
 * A class that creates a JFrame to hold the Game object
 * and launches the game.
 * @author Niel
 *
 */

public class Main {
    public static void main (String[] args) {
        JFrame f = new JFrame("Demo Game");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController pc = new PlatformerController(new DemoLevelFactory(), new DemoInitializer());
        KeyboardController testController = new KeyboardController(pc);
        pc.setInputController(testController);
        f.add(pc);
        f.pack();
        f.setVisible(true);
    }
}
