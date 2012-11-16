package games.platformerdemo;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import util.ingamemenu.GameButton;
import util.ingamemenu.Menu;
import vooga.platformer.core.PlatformerController;

/**
 * A class that creates a JFrame to hold the Game object
 * and launches the game.
 * @author Niel
 *
 */

public class Main {
    private static JFrame myFrame;
    private static PlatformerController myController;
    
    public static void main (String[] args) {
        myFrame = new JFrame("Demo Game");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myController = new PlatformerController(new DemoLevelFactory(), new DemoInitializer());
        //TODO:Should use follow eventually
        //KeyboardController testController = new KeyboardController(pc);
        //pc.setInputController(testController);
        myFrame.getContentPane().add(myController);
        myFrame.addKeyListener(myController.setTemporaryInputListener());
        myFrame.pack();
        setUpMenu();
        myFrame.setVisible(true);
        
        
    }
    private static void setUpMenu(){
        
        // ---This is what you need to do to add this menu---
        myFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    final Menu menu = new Menu(myController);
                    GameButton gb1 = new GameButton("greenbutton", "Back");
                    MouseListener gl = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent arg0) {
                            myController.remove(menu);
                            myFrame.repaint();
                        }
                    };
                    gb1.addMouseListener(gl);
                    GameButton gb2 = new GameButton("button", "Do nothing");
                    gb2.setSize(new Dimension(130, 130));
                    menu.addButtons(gb1);
                    menu.addButtons(gb2);
                    myFrame.pack();
                }
            }
        });
    }
}
