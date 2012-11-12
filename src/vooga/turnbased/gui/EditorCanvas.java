package vooga.turnbased.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class EditorCanvas extends Canvas implements KeyListener {

    private GameWindow myGameWindow;

    public EditorCanvas (GameWindow gameWindow) {
        myGameWindow = gameWindow;
    }

    @Override
    public void initialize () {
        addButtons();
        addKeyListener(this);
        repaint();
        setFocusable(true);
        requestFocusInWindow();
        myGameWindow.refreshScreen();
    }
    
    private void addButtons () {
        JButton menuButton = new JButton("Back to menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                myGameWindow.changeCurrentCanvas(GameWindow.MENU);
            }
        });
        add(menuButton);
    }

    /**
     * paint components of the Canvas
     */
    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        Image background = GameWindow.importImage("EditorBackgroundImage");
        g.drawImage(background, 0, 0, background.getWidth(null), background.getHeight(null), this);
    }

    /**
     * event handling when user types anything
     */
    @Override
    public void keyTyped (KeyEvent e) {
        System.out.println("Typed " + e.getKeyCode());
    }

    /**
     * event handling when any key is pressed
     */
    @Override
    public void keyPressed (KeyEvent e) {
        System.out.println("Pressed " + e.getKeyCode());
    }

    /**
     * event handling when any key is released
     */
    @Override
    public void keyReleased (KeyEvent e) {
        System.out.println("Released " + e.getKeyCode());
    }

}
