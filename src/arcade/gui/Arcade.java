package arcade.gui;

import arcade.gui.frame.AbstractFrame;
import arcade.gui.panel.AbstractPanel;

/**
 * 
 * This is the top-level container manager for the arcade. It 
 * builds and maintains the top-level AbstractFrame (extends JFrame) for the arcade.
 * 
 * It is important to note that this class is solely responsible for 
 * managing and altering the base AbstractFrame for the arcade. In other 
 * words, it will NOT support getter and setter functionality for the frame.
 * 
 * @author Michael Deng
 *
 */
public class Arcade {

    /**
     * username (unique key) of the user who is logged in
     */
    private static String myUser = "";
    
    private static AbstractFrame myFrame = null; 
    
    
    public Arcade(){
        System.out.println("got it!");
    }
    
    // this is the jframe manager
    
    
    // creates a jframe
    
    // methods that manage the various panels in the arcade
    
    // at first, the jframe must set up default panels
    
    
    
    // responsible for setting up the frame.. and keeping it running
    
    
    // DOES NOT SUPPORT THE GETFRAME FUNCTION. this class is responsible 
    // for managing the frame.. no one else touches it.
    
    /**
     * This method returns the panel specified by the panel number.
     * 
     * @param panelNumber the int representing the panel
     * @return this returns the panel specified
     */
    public AbstractPanel getPanel (String panelNumber) {
        return myFrame.getPanel(panelNumber);
    }

    /**
     * This method replaces and old panel with a new panel.
     * 
     * @param panelNumber the int representing the panel to be replaced
     * @param newPanel the replacement panel
     * @return this returns the old panel
     */
    public AbstractPanel replacePanel (String panelNumber, AbstractPanel newPanel) {
        return myFrame.replacePanel(panelNumber, newPanel);
    }

    /**
     * This method refreshes (reloads all the panels into the content pane)
     * the frame. It also causes each panel to refresh (panel will reload data
     * and components).
     */
    public void refreshAll () {
        myFrame.refreshAll();
    }

    public String getUsername () {
        return myUser;
    }
    
    
    // user properties file to list the default components.
    
    
}
