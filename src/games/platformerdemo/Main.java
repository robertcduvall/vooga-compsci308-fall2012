package games.platformerdemo;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import util.ingamemenu.GameButton;
import util.ingamemenu.Menu;
import vooga.platformer.core.PlatformerController;

/**
 * A class that creates a JFrame to hold the Game object
 * and launches the game.
 * @author Niel
 * @author Yaqi
 */

public class Main {
    
    public static void main (String[] args) {
        JFrame frame = new JFrame("Demo Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlatformerController controller = new PlatformerController(new DemoLevelFactory(), new DemoInitializer());
        //TODO:Should use follow eventually
        //KeyboardController testController = new KeyboardController(pc);
        //pc.setInputController(testController);
        frame.getContentPane().add(controller);
        frame.addKeyListener(controller.setTemporaryInputListener());
        frame.addKeyListener(controller.setMenuKeyListener());
        frame.pack();
        frame.setVisible(true);
        
    }
}
