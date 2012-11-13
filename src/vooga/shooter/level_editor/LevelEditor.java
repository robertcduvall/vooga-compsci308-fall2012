package vooga.shooter.level_editor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class LevelEditor implements ActionListener, ChangeListener, KeyListener {
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

}
