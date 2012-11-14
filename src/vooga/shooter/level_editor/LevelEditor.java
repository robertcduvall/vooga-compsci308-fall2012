package vooga.shooter.level_editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.io.*;

/**
 * LevelEditor.java
 * GUI for making and editing Levels for a 2D Shooter game.
 * @author Zachary Hopping
 *
 */
public class LevelEditor implements ActionListener, ChangeListener, KeyListener {
    boolean compactToolbars = true;
    boolean borderedButtons = true;
    
    JFrame       mainFrame; // The main window
    JFileChooser chooser; // For saving and loading levels
    JSplitPane   split;  // provides the movable divider next to Sprite chooser
    JPanel       chooserPanel; //panel for the Sprite chooser 
    
    File openFile; //currently open file
    Level myLevel; //level object for editing
    
    /* Toolbar buttons, self-explanatory */
    JToolBar myToolBar;
    JButton newBtn;
    JButton openBtn;
    JButton saveBtn;
    JButton undoBtn;
    JButton redoBtn;
    JButton clearBtn;
    
    public LevelEditor() {
        //TODO initialize all variables and load a level to edit/make a new level
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

}
