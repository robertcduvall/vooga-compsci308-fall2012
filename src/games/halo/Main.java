package games.halo;

import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;

import javax.swing.*;

/**
 * @author Zach Michaelov
 */
public class Main {

    public static void main (String[] args) {
        JFrame frame = new JFrame("Demo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller = new PlatformerController("src/games/halo/autumn1.xml",
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }
}
