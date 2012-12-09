package games.halo;

import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;

import javax.swing.*;

/**
 * @author Zach Michaelov
 */
public class Main {

    public static void main (String[] args) {
        JFrame frame = new JFrame("Halo: Combat Devolved");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller = new PlatformerController("src/games/halo/level0.xml",
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }
}
