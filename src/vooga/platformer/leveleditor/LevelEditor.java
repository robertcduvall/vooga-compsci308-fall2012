package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vooga.platformer.gui.menu.*;


public class LevelEditor extends JFrame{
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private static final String[] mySprites = {"Bowser", "Mario"};
    private JFrame myContainer;
    private JPanel myViewPane;
    private BufferedImage backbuffer;
    private Graphics2D g2d;
    public static void main (String[] args) {
        new LevelEditor();
    }
    public LevelEditor () {
        super("LevelEditor");
        myContainer = this;
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
        createEditPane();
        createButtonPanel();
        setVisible(true);
        pack();
    }
    private GameButton createButton (final String spritename) {
        GameButton gb = new GameButton(spritename);
        GameListener gl = new GameListener() {
            @Override 
            public void actionPerformed() {
                System.out.println(spritename);
                //getClass().forName(spritename);
            }
        };
        gb.setGameListener(gl);
        return gb;
    }
    private void createButtonPanel () {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(mySprites.length,1));
        for(String  sprite : mySprites) {
            panel.add(createButton(sprite));
        }
        myViewPane.add(panel, BorderLayout.WEST);
    }
    private void createEditPane () {
        backbuffer = new BufferedImage(DEFAULT_FRAME_SIZE.width, DEFAULT_FRAME_SIZE.height, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();
        JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(backbuffer, 0, 0, this);
                super.paint(g);
            }
        };
        panel.setLayout(new BorderLayout());
        myViewPane = panel;
        myContainer.add(panel);
    }
}
