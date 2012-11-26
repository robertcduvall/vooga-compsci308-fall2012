package arcade.gui;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import arcade.datatransfer.GameLink;
import arcade.datatransfer.UserLink;
import arcade.gamemanager.GameCenter;
import arcade.gui.frame.ArcadeFrame;
import arcade.gui.frame.MainFrameCreator;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.SocialCenter;


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
    private static Map<String, Serializable> mySharedVariables;

    private static ArcadeFrame myFrame;

    private static GameCenter myGameCenter;
    private static SocialCenter mySocialCenter;
    private static GameLink myGameManager;
    private static UserLink myUserManager;
    private static PanelCreatorFactory myFactory;
    private static ResourceBundle myResources;

    public Arcade () {
        System.out.println("got it!");

        // initialize things
        myFactory = new PanelCreatorFactory(this);
        // myGameCenter = new GameCenter(); // GameCenter is currently broken.
        mySocialCenter = SocialCenter.getInstance();
        myGameManager = new GameLink();
        myUserManager = new UserLink();
        myResources = ResourceBundle.getBundle("arcade.gui.resources.Arcade");
        mySharedVariables = new HashMap<String, Serializable>();

        // set up frame
        frameSetup();

    }

    /**
     * Creates the frame and populates it with the default panels
     */
    private void frameSetup () {
        // create the frame
        // MainFrameCreatorOld frameCreator = new MainFrameCreatorOld(this);
        MainFrameCreator frameCreator = new MainFrameCreator(this);
        myFrame = frameCreator.createFrame();

        // fill it with default panels
        replacePanel("FootDefault");
        replacePanel("MainDefault");
        replacePanel("LogoDefault");
        replacePanel("NavDefault");
        replacePanel("SearchDefault");
        replacePanel("UserDefault");
    }

    private ArcadePanel createPanel (String panelCreatorName) {
        return myFactory.createPanelCreator(panelCreatorName).createPanel();
    }

    private void updatePanelinFrame (ArcadePanel newPanel) {
        ArcadePanel panelHolder = myFrame.getPanel(newPanel.getPanelType());

        panelHolder.removeAll();
        panelHolder.setLayout(new BorderLayout());
        panelHolder.add(newPanel, BorderLayout.CENTER);
    }

    /**
     * This method replaces and old panel with a new panel.
     * 
     * @param panelNumber the int representing the panel to be replaced
     * @param newPanel the replacement panel
     * @return this returns the old panel
     */
    public void replacePanel (String panelCreatorName) {
        String panelRealName = myResources.getString(panelCreatorName);
        // myFrame.setVisible(false);
        ArcadePanel newPanel = createPanel(panelRealName);
        updatePanelinFrame(newPanel);
        // myFrame.setVisible(true);
        // myFrame.pack();
        myFrame.validate();
    }

    public String getUsername () {
        return myUser;
    }

    /**
     * Sets the username
     * 
     * @param u
     */
    public void setUsername (String u) {
        myUser = u;
    }

    public GameLink getGameManager () {
        return myGameManager;
    }

    public UserLink getUserManager () {
        return myUserManager;
    }

    public void saveVariable (String varName, Serializable var) {
        mySharedVariables.put(varName, var);
    }

    public Serializable getVariable (String varName) {
        return mySharedVariables.get(varName);
    }

    public GameCenter getGameCenter () {
        return myGameCenter;
    }

    /**
     * @return the mySocialCenter
     */
    public SocialCenter getSocialCenter () {
        return mySocialCenter;
    }
}
