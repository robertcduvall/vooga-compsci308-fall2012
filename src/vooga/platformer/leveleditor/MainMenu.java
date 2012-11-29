package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class MainMenu extends JFrame{
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private static final int ONE_SECOND = 1000;
    private static final int FRAMES_PER_SECOND = 20;
    private Timer myTimer;
    private ActionListener myActionListener;
    private JPanel myContent;
    public static void main(String[] args) {
        new MainMenu();
    }
    public MainMenu () {
        frameBuild();
        createListener();
        myContent = createMenu();
        add(createMenu());
        pack();
        setVisible(true);
        myTimer = new Timer(ONE_SECOND / FRAMES_PER_SECOND, 
                new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent e) {
                        repaint();
                    }
                });
    }

    private void createListener () {
        myActionListener = new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                if ("Level Editor".equals(e.getActionCommand())) {
                    goLevelEditor();
                }
                else if ("New Game".equals(e.getActionCommand())) {
                    System.out.println("new Game");
                }
                else if ("Quit".equals(e.getActionCommand())) {
                    System.out.println("quit");
                }
            }
        };
    }
    protected void goLevelEditor () {
        removeAll();
        myContent = new LevelEditor(this);
        add(myContent);
        repaint();
        myTimer.start();
    }
    private void frameBuild() {
        setPreferredSize(DEFAULT_FRAME_SIZE);
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
    }
    private JPanel createMenu() {
        JPanel menu = new JPanel()
        {
            @Override
            public void paint(Graphics g) {
                MainMenu.super.paint(g);
                super.paint(g);
            }
        };
        menu.setLayout(new GridLayout(3, 1));
        JButton b = new JButton("Level Editor");
        b.addActionListener(myActionListener);
        menu.add(b);
        b = new JButton("New Game");
        b.addActionListener(myActionListener);
        menu.add(b);
        b = new JButton("Quit");
        b.addActionListener(myActionListener);
        menu.add(b);
        return menu;
    }
    @Override
    public void paint (Graphics g) {
        g.clearRect(0, 0, DEFAULT_FRAME_SIZE.width, DEFAULT_FRAME_SIZE.height);
        myContent.paint(g);
    }
}
