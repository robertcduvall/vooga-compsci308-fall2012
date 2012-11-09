package vooga.platformer.leveleditor;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class LevelEditor extends JFrame{
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    public LevelEditor () {
        super("LevelEditor");
        setPreferredSize(DEFAULT_FRAME_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        createListeners();
        createEditPane();
        createButtonPanel();
        setVisible(true);
        pack();
    }
    private void createListeners () {
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed (KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_M) {
//                    final Menu menu = new Menu(myPanel);
//                    GameButton gb1 = new GameButton("greenbutton", "Back");
//                    GameListener gl = new GameListener() {
//                        @Override
//                        public void actionPerformed () {
//                            myPanel.remove(menu);
//                            myFrame.repaint();
//                        }
//                    };
//                    gb1.setGameListener(gl);
//                    GameButton gb2 = new GameButton("button", "Do nothing");
//                    gb2.setSize(new Dimension(130, 130));
//                    menu.addButtons(gb1);
//                    menu.addButtons(gb2);
//                    myFrame.pack();
//                }
//            }
//        });
    }
    private void createButtonPanel () {
        
    }
    private void createEditPane () {
        
    }

}
