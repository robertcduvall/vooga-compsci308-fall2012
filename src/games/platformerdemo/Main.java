package games.platformerdemo;

import javax.swing.JFrame;
import vooga.platformer.core.Controller;
import vooga.platformer.core.GameInitializer;

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
        f.add(new Controller(new DemoLevelFactory(), new DemoInitializer()));
        f.pack();
        f.setVisible(true);
    }
}
