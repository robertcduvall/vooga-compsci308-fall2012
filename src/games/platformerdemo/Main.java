package games.platformerdemo;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import vooga.platformer.core.GameInitializer;
import util.ingamemenu.GameButton;
import util.ingamemenu.Menu;
import vooga.platformer.core.PlatformerController;
import vooga.platformer.core.inputinitializer.SimpleOnePlayerInputInitializer;

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
        PlatformerController controller = new PlatformerController(new DemoLevelFactory(), "demoLevel",
                new SimpleOnePlayerInputInitializer());
        //TODO:Should use follow eventually
        //KeyboardController testController = new KeyboardController(pc);
        //pc.setInputController(testController);
        frame.getContentPane().add(controller);
        frame.addKeyListener(controller.setMenuKeyListener());
        frame.pack();
        paintString(controller);
        frame.setVisible(true);
    }

    public static void paintString (PlatformerController controller) {
        Dimension canvasSize = controller.getSize();
        controller.paintString("M - Menu", canvasSize.width * 3 / 5,canvasSize.height / 4);
        controller.paintString("¡û ¡ú - Move left and right", canvasSize.width * 3 / 5,
                canvasSize.height / 4 + 15);
        controller.paintString("¡ü - Jump", canvasSize.width * 3 / 5,
                canvasSize.height / 4 + 30);
        controller.paintString("Space - Shoot", canvasSize.width * 3 / 5,
                canvasSize.height / 4 + 45);
    }

}
