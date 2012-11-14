package arcade.gui;

import java.util.ResourceBundle;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * This is the factory for framecreators and panelcreators.
 * It will use reflection to instantiate the components necessary.
 * 
 * 
 * @author Michael Deng
 * 
 */
public class GUIFactory {

    private Arcade myArcade;
    private static ResourceBundle myResources;

    public GUIFactory (Arcade a) {
        myArcade = a;
        myResources = ResourceBundle.getBundle("arcade.gui.resources.Factory");

    }

    // public MainFrameCreator createFrameCreator(String frameCreatorName){
    //
    // return null;
    // }

    public AbstractPanelCreator createPanelCreator (String panelCreatorName) {

        // TODO
        // use reflection w/ constructor to create.. then save reference to the
        // panelCreatorName

        return null;
    }

    // reflection will require one constructor . pass in reference to Arcade

}
