package vooga.shooter.level_editor;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.io.*;

/**
 * LevelEditor.java
 * GUI for making and editing Levels for a 2D Shooter game.
 * @author Zachary Hopping
 *
 */
public class LevelEditor implements ActionListener, ChangeListener, KeyListener {
    
    private static final Dimension FRAME_SIZE = new Dimension(1000, 800);
    boolean compactToolbars = true;
    boolean borderedButtons = true;
    
    private JFrame       mainFrame; // The main window   
    private JFileChooser chooser; // For saving and loading levels
    private JSplitPane   split;  // provides the movable divider next to Sprite chooser
    private JPanel       chooserPanel; //panel for the Sprite chooser 
    private JPanel      leftPanel;
    private JPanel      rightPanel;
    private Canvas      myCanvas; //drawing canvas
    
    private File openFile; //currently open file
    private Level myLevel; //level object for editing
    
    /* Toolbar buttons, self-explanatory */
    private JToolBar myToolBar;
    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private JButton undoBtn;
    private JButton redoBtn;
    private JButton clearBtn;
    
    public LevelEditor() {
        //TODO initialize all variables and load a level to edit/make a new level
        mainFrame = new JFrame("Level Editor");
        mainFrame.setPreferredSize(FRAME_SIZE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setBackground(Color.WHITE);
        
        makeMenu();       
        
        split = new JSplitPane();
        split.setDividerLocation(200);
        
        //myCanvas = new Canvas();
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new TitledBorder("Left Panel"));
        
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new TitledBorder("Right Panel"));
        
        split.setLeftComponent(leftPanel);
        split.setRightComponent(rightPanel);

        
        mainFrame.add(split, BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }
    
    @Override
    public void keyPressed (KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased (KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped (KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void actionPerformed (ActionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stateChanged (ChangeEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Makes a JButton with the given icon and tooltop.
     * If the icon cannot be loaded, then the text will be used instead.
     *
     * Adds this LevelEditor as an actionListener.
     *
     * @return the new JButton
     **/
    private JButton makeBtn(String text, String icon, String tooltip) {
            JButton newBtn;
            try {
                    newBtn = new JButton(new ImageIcon(getClass().getResource(icon)));
            } catch (Exception e) {
                    newBtn = new JButton(text);
            }
            newBtn.setToolTipText(tooltip);
            newBtn.addActionListener(this);
            if(borderedButtons) {
                    newBtn.setBorder(new LineBorder(Color.gray, 1, false));
            } else if (compactToolbars) {
                    newBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
            }
            //newBtn.setBorderPainted(false);
            return newBtn;
    }
    
    private JMenuBar makeMenu() {
        JMenuBar bar = new JMenuBar();
        bar.add(makeFileMenu());
        mainFrame.setJMenuBar(bar);
        return bar;
    }

    @SuppressWarnings("serial")
    private JMenu makeFileMenu () {
        JMenu result = new JMenu("File");
        result.add(new AbstractAction("New") { //this needs to come from language resources folder
            @Override
            public void actionPerformed (ActionEvent e) {
                // go to start page
            }
        });
        result.add(new JSeparator());
        result.add(new AbstractAction("Quit") {
            @Override
            public void actionPerformed (ActionEvent e) {
                // end program
                // System.exit(0);
            }
        });
        return result;
    }
   
}
