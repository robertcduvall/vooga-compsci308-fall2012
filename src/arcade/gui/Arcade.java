package arcade.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import arcade.datatransfer.GameLink;
import arcade.datatransfer.UserLink;
import arcade.gui.frame.ArcadeFrame;
import arcade.gui.frame.MainFrameCreator;
import arcade.gui.panel.ArcadePanel;


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

    // username (unique key) of the user who is logged in
    private static String myUser = "";

    private static ArcadeFrame myFrame;
    private static Map<String, ArcadePanel> myPanels;

    private static GameLink myGameManager;
    private static UserLink myUserManager;
    private static PanelCreatorFactory myFactory;
    private static ResourceBundle myResources;

    public Arcade () {
         System.out.println("got it!");

        // initialize things
        myFactory = new PanelCreatorFactory(this);
        myGameManager = new GameLink();
        myUserManager = new UserLink();
        myPanels = new HashMap<String, ArcadePanel>();
        myResources = ResourceBundle.getBundle("arcade.gui.resources.Arcade");

        // set up frame
        frameSetup();

    }

    private void frameSetup () {
        // create the frame
        MainFrameCreator frameCreator = new MainFrameCreator(this);
        myFrame = frameCreator.createFrame();

        // fill it with default panels
        replacePanel(myResources.getString("DefaultFoot"));
        replacePanel(myResources.getString("DefaultMain"));
        replacePanel(myResources.getString("DefaultLogo"));
        replacePanel(myResources.getString("DefaultNav"));
        replacePanel(myResources.getString("DefaultSearch"));
        replacePanel(myResources.getString("DefaultUser"));
    }

    private ArcadePanel createPanel (String panelCreatorName) {
        return myFactory.createPanelCreator(panelCreatorName).createPanel();
    }

    private void updateFrame () {
        // clear frame
        myFrame.removeAll();

        // reload everything in into the frame from the map
        // based on the panel's location
        // TODO

    }

    /**
     * This method replaces and old panel with a new panel.
     * 
     * @param panelNumber the int representing the panel to be replaced
     * @param newPanel the replacement panel
     * @return this returns the old panel
     */
    public void replacePanel (String panelCreatorName) {
        myFrame.setVisible(false);
        ArcadePanel newPanel = createPanel(panelCreatorName);
        myPanels.put(newPanel.getPanelType(), newPanel);
        updateFrame();
        myFrame.setVisible(true);
    }

    public String getUsername () {
        return myUser;
    }

    public GameLink getGameManager () {
        return myGameManager;
    }

    public UserLink getUserManager () {
        return myUserManager;
    }

}
