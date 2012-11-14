package arcade.gui;

import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import arcade.datatransfer.GameManager;
import arcade.datatransfer.UserManager;
import arcade.gui.frame.MainFrameCreator;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * 
 * This is the top-level container manager for the arcade. It
 * builds and maintains the top-level AbstractFrame (extends JFrame) for the
 * arcade.
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
    private static JFrame myFrame;
    private static Map<String, JPanel> myPanels;

    private static GameManager myGameManager;
    private static UserManager myUserManager;
    private static GUIFactory myFactory;

    public Arcade () {
        System.out.println("got it!");

        // initialize things
        myFactory = new GUIFactory(this);
        myGameManager = new GameManager();
        myUserManager = new UserManager();

        // set up frame
        frameSetup();

    }

    private void frameSetup () {
        // create the frame
        MainFrameCreator frameCreator = new MainFrameCreator(this);
        myFrame = frameCreator.createFrame();
        
        // fill it with default panels - read from properties file, create panel, add panel to map
        // TODO
        // read in the properties file
        
        
        // use GUIFactory (give it panel type, and panel name) -> it will return the panel
            // or use the replacePanel() method... this method should be implemented to replace a panel
    }

    private JPanel createPanel(String panelType, String panelCreatorName){
        return myFactory.createPanelCreator(panelType, panelCreatorName).createPanel();
    }
    
    
    private void updateFrame(){
        // clear frame
        myFrame.removeAll();
        
        // reload everything in into the frame from the map
        // based on the panel's location
        // TODO
        
    }
    
    // this is the jframe manager

    // creates a jframe

    // methods that manage the various panels in the arcade

    // at first, the jframe must set up default panels

    // responsible for setting up the frame.. and keeping it running

    // DOES NOT SUPPORT THE GETFRAME FUNCTION. this class is responsible
    // for managing the frame.. no one else touches it.

    /**
     * This method replaces and old panel with a new panel.
     * 
     * @param panelNumber the int representing the panel to be replaced
     * @param newPanel the replacement panel
     * @return this returns the old panel
     */
    public void replacePanel (String panelType, String panelCreatorName) {
        
        // do i need to set frame visibility on off?
        myPanels.put(panelType, createPanel(panelType, panelCreatorName));
        updateFrame();
    }

    public String getUsername () {
        return myUser;
    }

    public GameManager getGameManager () {
        return myGameManager;
    }

    public UserManager getUserManager () {
        return myUserManager;
    }

    // user properties file to list the default components.

}
