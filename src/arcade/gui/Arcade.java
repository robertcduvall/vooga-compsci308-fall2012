package arcade.gui;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import arcade.datatransfer.ModelInterface;
import arcade.gui.frame.ArcadeFrame;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.SocialCenter;
import arcade.usermanager.User;
import arcade.usermanager.UserProfile;


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
    private String myUsername = "";
    private Map<String, Serializable> mySharedVariables;

    private ArcadeFrame myFrame;
    private ModelInterface myModelInterface;
    private CreatorFactory myFactory;
    private ResourceBundle myResources;

    public Arcade () {
        System.out.println("got it!");

        // initialize things
        myFactory = new CreatorFactory(this);
        myModelInterface = new ModelInterface(this);
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
        myFrame = myFactory.createFrameCreator(myResources.getString("Frame")).createFrame();

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
     * @param panelCreatorName
     */
    public void replacePanel (String panelCreatorName) {
        String panelRealName = myResources.getString(panelCreatorName);
        ArcadePanel newPanel = createPanel(panelRealName);
        updatePanelinFrame(newPanel);
        // myFrame.pack();
        myFrame.validate();
    }

    public String getUsername () {
        return myUsername;
    }

    /**
     * Sets the username
     * 
     * @param u
     */
    public void setUsername (String u) {
        myUsername = u;
    }

    /**
     * 
     * @param varName
     * @param var
     */
    public void saveVariable (String varName, Serializable var) {
        mySharedVariables.put(varName, var);
    }

    /**
     * 
     * @param varName
     * @return
     */
    public Serializable getVariable (String varName) {
        return mySharedVariables.get(varName);
    }

    /**
     * 
     * @return reference to the modelinterface instance
     */
    public ModelInterface getModelInterface () {
        return myModelInterface;
    }

    /**
     * @deprecated why does this method exist? just call:
     *  ModelInterface.getUser(Arcade.getUsername);
     * 
     * @return The User that is currently logged in.
     */
    public UserProfile getCurrentUser () {
        return myModelInterface.getUser(myUsername);
    }

}
