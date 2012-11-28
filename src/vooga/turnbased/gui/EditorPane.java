package vooga.turnbased.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class EditorPane extends DisplayPane {

    public EditorPane (GameWindow gameWindow) {
        super(gameWindow);
        addButtons();
        addMouseListener(new GameMouseListener());
    }

    private void addButtons () {
        JButton menuButton = new JButton("Back to menu");
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                getGameWindow().changeActivePane(GameWindow.MENU);
            }
        });
        add(menuButton);
        JButton newLevelButton = new JButton("Create New Level");
        newLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                System.out.println("Action performed to create a new Level");
            }
        });
        add(newLevelButton);
        JButton modifyLevelButton = new JButton("Modify Existing Level");
        modifyLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                System.out.println("Action performed to modify existing level");
            }
        });
        add(modifyLevelButton);
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
    
    private class GameMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked (MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseEntered (MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseExited (MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mousePressed (MouseEvent e) {
            System.out.println(e);
        }

        @Override
        public void mouseReleased (MouseEvent e) {
            System.out.println(e);
        }
    }
}
