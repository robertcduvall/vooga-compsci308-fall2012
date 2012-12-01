package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PlatformerFrame extends JFrame{
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private static final int ONE_SECOND = 1000;
    private static final int FRAMES_PER_SECOND = 20;
    private Timer myTimer;
    private ActionListener myActionListener;
    private JPanel myContent;
    public static void main(String[] args) {
        new PlatformerFrame();
    }
    public PlatformerFrame () {
        frameBuild();
        setContentPane(myContent);
        myContent.add(new PlatformerMenu(this), BorderLayout.CENTER);
        pack();
        setVisible(true);
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND, 
                new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent e) {
                        repaint();
                    }
                });
        myTimer.start();
    }
    private void frameBuild() {
        setSize(DEFAULT_FRAME_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        myContent = new JPanel();
//        {
//            @Override
//            public void addKeyListener(KeyListener kl) {
//                MainMenu.this.addKeyListener(kl);
//            }
//        };
        myContent.setLayout(new BorderLayout());
    }

    public void levelEdit () {
        myContent.removeAll();
        LevelEditor le = new LevelEditor(DEFAULT_FRAME_SIZE);
        setTitle("Level Editor");
        myContent.add(le);
//        setJMenuBar(new EditorMenuBar(le));
        validate();
        repaint();
    }

    public void newGame () {
        System.out.println("make a new game or something");
    }

}
