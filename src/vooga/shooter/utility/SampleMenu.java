package src.vooga.shooter.utility;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


/**
 * This class demos how to insert a menu into a given game canvas.
 * Press M to bring up the menu
 * 
 * @author Yaqi
 * 
 */

public class SampleMenu {
    private static JPanel myPanel;
    private static JFrame myFrame;
    public static void main (String[] args) {
        // ---Assume these exist for original game.---
        JFrame frame = new JFrame();
        myFrame = frame;
        // jp is the Component to paint game
        JPanel jp = new JPanel();
        myPanel = jp;
        jp.setPreferredSize(new Dimension(1000, 800));
        frame.getContentPane().add(jp);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // ---This is what you need to do to add this menu---
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    final Menu menu = new Menu(myPanel);
                    GameButton gb1 = new GameButton("greenbutton", "Back");
                    GameListener gl = new GameListener() {
                        @Override
                        public void actionPerformed () {
                            myPanel.remove(menu);
                            myFrame.repaint();
                        }
                    };
                    gb1.setGameListener(gl);
                    GameButton gb2 = new GameButton("button", "Do nothing");
                    gb2.setSize(new Dimension(130,130));
                    menu.addButtons(gb1);
                    menu.addButtons(gb2);
                    myFrame.pack();
                }
            }
        });
    }
}
