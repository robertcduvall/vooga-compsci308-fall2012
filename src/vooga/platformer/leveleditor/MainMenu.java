package vooga.platformer.leveleditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainMenu extends JFrame{
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(640, 480);
    private ActionListener myActionListener;
    private JPanel myMenu;
    private JPanel myContentPane;
    private LevelEditor myEditor;
    public static void main(String[] args) {
        new MainMenu();
    }
    public MainMenu () {
        frameBuild();
        createListener();
        myContentPane = new JPanel();
        myContentPane.add(addGameMenu());
        add(myContentPane);
        pack();
        setVisible(true);
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
        myEditor = new LevelEditor(this);
        //        myContentPane.remove(myMenu);
//        myEditor = new LevelEditor(this);
        myContentPane.add(myEditor, BorderLayout.CENTER);
        repaint();
        myEditor.paint(getGraphics());
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
    private JPanel addGameMenu() {
        myMenu = new JPanel();
        myMenu.setLayout(new GridLayout(3, 1));
        JButton b = new JButton("Level Editor");
        b.addActionListener(myActionListener);
        myMenu.add(b);
        b = new JButton("New Game");
        b.addActionListener(myActionListener);
        myMenu.add(b);
        b = new JButton("Quit");
        b.addActionListener(myActionListener);
        myMenu.add(b);
        return myMenu;
    }
}
