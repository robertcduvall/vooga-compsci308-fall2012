package games.halo;

import javax.swing.JFrame;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.KeyControllerOnePlayerInputInitializer;


/**
 * 
 * @author Sam Rang AND Zach Michealov
 * This games demonstrates how easy it is to create a game using our Level Editor,
 * and how little code is required to get the game running
 */
public class Main {
    public static void main (String[] args) {
        JFrame frame = new JFrame ("Halo: Combat Devolved");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // needed for proper handling by Arcade
        PlatformerController controller = new PlatformerController("src/games/halo/level0.xml", // point to the first level
                new KeyControllerOnePlayerInputInitializer());
        frame.getContentPane().add(controller);
        frame.pack();
        frame.setVisible(true);
    }

}
