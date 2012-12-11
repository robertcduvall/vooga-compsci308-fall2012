package arcade.gui;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import arcade.datatransfer.ModelInterface;
import arcade.gui.frame.ArcadeFrame;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * This is the top-level container manager for the arcade. It
 * builds and maintains the top-level AbstractFrame (extends JFrame) for the
 * arcade.
 * 
 * It is important to note that this class is solely responsible for
 * managing and altering the base AbstractFrame for the arcade. In other
 * words, the panels do not interact directly with the frame. This
 * class abstracts away all details involved in replacing and positioning
 * new panels.
 * 
 * @author Michael Deng
 * 
 */
public class Arcade {

    // username of the user who is logged in
    private String myUsername = "";
    private Map<String, Serializable> mySharedVariables;

    private ArcadeFrame myFrame;
    private ModelInterface myModelInterface;
    private CreatorFactory myFactory;
    private ResourceBundle myResources;
    private String myResourcesPath = "arcade.gui.resources.Arcade";

    /**
     * Constructor for the top-level Arcade class.
     */
    public Arcade () {

        // initialize
        myFactory = new CreatorFactory(this);
        myModelInterface = new ModelInterface(this);
        myResources = ResourceBundle.getBundle(myResourcesPath);
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

        // initialize frame with default panels (default panels (panelcreators)
        // specified by Arcade.properties nickname)
        replacePanel("FootDefault");
        replacePanel("MainDefault");
        replacePanel("LogoDefault");
        replacePanel("NavDefault");
        replacePanel("SearchDefault");
        replacePanel("UserDefault");
    }

    /**
     * Private method that uses the factory to create
     * the panelcreator, and then uses the panelcreator
     * to create the panel.
     * 
     * @param panelCreatorName real name of the panelcreator
     * @return a new panel (created using the specified panelcreator)
     */
    private ArcadePanel createPanel (String panelCreatorName) {
        return myFactory.createPanelCreator(panelCreatorName).createPanel();
    }

    /**
     * Private method that executes the replacement of the old
     * panel with the new specifed panel
     * 
     * @param newPanel the new panel (used to replace old panel)
     */
    private void updatePanelinFrame (ArcadePanel newPanel) {
        ArcadePanel panelHolder = myFrame.getPanel(newPanel.getPanelType());

        panelHolder.removeAll();
        panelHolder.setLayout(new BorderLayout());
        panelHolder.add(newPanel, BorderLayout.CENTER);
    }

    /**
     * This method replaces an old panel with a new panel. The panels
     * are "typed" so there is no need to specifiy the location of
     * the new panel (or which old panel is being replaced). When you specify
     * the incoming panel, the framework already knows which location
     * in the frame to place the new panel.
     * 
     * @param panelCreatorName the nickname (as specified in the
     *        Arcade.properties file) of the new panelcreator
     */
    public void replacePanel (String panelCreatorName) {
        String panelRealName = myResources.getString(panelCreatorName);
        ArcadePanel newPanel = createPanel(panelRealName);
        updatePanelinFrame(newPanel);

        myFrame.validate();
    }

    /**
     * Used to get the username of the current logged-in user.
     * 
     * @return the username as a string
     */
    public String getUsername () {
        return myUsername;
    }

    /**
     * Used to set the username of the current logged-in user.
     * 
     * @param u the new username
     */
    public void setUsername (String u) {
        myUsername = u;
    }

    /**
     * Allows panels to store (save) variables across panel
     * replacements. This method allows panels to interact with each other.
     * 
     * @param varName the name of the variable to be stored
     * @param var the contents of the variable
     */
    public void saveVariable (String varName, Serializable var) {
        mySharedVariables.put(varName, var);
    }

    /**
     * Allows panels to retrieve data that was stored
     * earlier by other panels. This method allows panels to interact with each
     * other.
     * 
     * @param varName the name of the variable to retrieve
     * @return the contents of the variable specified
     */
    public Serializable getVariable (String varName) {
        return mySharedVariables.get(varName);
    }

    /**
     * The ModelInterface is the bridge between the Arcade GUI (view)
     * and the Arcade Game/User (models). This method returns
     * a reference to the ModelInterface which allows
     * arcade panels to access the methods in the arcade models.
     * 
     * @return reference to the ModelInterface instance
     */
    public ModelInterface getModelInterface () {
        return myModelInterface;
    }

}
