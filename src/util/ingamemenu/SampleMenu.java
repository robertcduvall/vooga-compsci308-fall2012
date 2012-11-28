package util.ingamemenu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * This class demos how to insert a menu into a given game canvas.
 * Press M to bring up the menu
 * 
 * @author Yaqi Zhang
 * 
 */

public class SampleMenu extends JComponent {
    private static JComponent myPanel;
    private static JFrame myFrame;

    public static void main (String[] args) {
        // ---Assume these exist for original game.---
        JFrame frame = new JFrame("Menu Demo");
        myFrame = frame;
        // jp is the Component to paint game
        SampleMenu jp = new SampleMenu();
        myPanel = jp;
        jp.setPreferredSize(new Dimension(1000, 800));
        frame.getContentPane().add(jp);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jp.setUpMenu();
    }

    private void setUpMenu () {
        // ---This is what you need to do to add this menu---
        myFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_M) {
                    final Menu menu = new Menu(myPanel);
                    GameButton gb1 = new GameButton("greenbutton", "Back");
                    MouseListener gl = new MouseAdapter() {
                        @Override
                        public void mouseClicked (MouseEvent arg0) {
                            myPanel.remove(menu);
                            myFrame.repaint();
                        }
                    };
                    gb1.addMouseListener(gl);
                    GameButton gb3 = new GameButton("greenbutton", "Exit");
                    MouseListener gl3 = new MouseAdapter() {
                        @Override
                        public void mouseClicked (MouseEvent arg0) {
                            System.exit(0);
                        }
                    };
                    gb3.addMouseListener(gl3);
                    GameButton gb2 = new GameButton("button");
                    gb2.setString("Do nothing", GameButton.BOTTOM);
                    gb2.setSize(new Dimension(130, 130));
                    menu.addButtons(gb1);
                    menu.addButtons(gb3);
                    menu.addButtons(gb2);
                    myFrame.pack();
                }
            }
        });
    }

    @Override
    protected void paintComponent (Graphics pen) {
        String imagePath = "src/util/ingamemenu/background.jpeg";
        Image img = null;
        try {
            img = ImageIO.read(new File(imagePath));
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        pen.drawImage(img.getScaledInstance((int) getSize().width,
                (int) getSize().height, Image.SCALE_DEFAULT), 0, 0, null);
        pen.drawString(
                "Assume this is the game canvas. Press M to bring up the menu.",
                (getSize().width / 4), (getSize().height / 2));

    }
}
