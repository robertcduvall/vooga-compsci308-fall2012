package arcade.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import util.reflection.Reflection;
import arcade.gui.frame.AbstractFrameCreator;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * This is the factory for panelcreators and framecreators.
 * It will use reflection to instantiate the components necessary.
 * 
 * 
 * @author Michael Deng
 * 
 */
public class CreatorFactory {

    private static ResourceBundle myResources;

    private Arcade myArcade;
    private Map<String, AbstractPanelCreator> myPanelCreatorMap;

    /**
     * Constructor for factory
     * 
     * @param a arcade
     */
    public CreatorFactory (Arcade a) {
        myArcade = a;
        myResources = ResourceBundle.getBundle("arcade.gui.resources.Factory");
        myPanelCreatorMap = new HashMap<String, AbstractPanelCreator>();
    }

    /**
     * method to create a panelcreator
     * 
     * @param panelCreatorName name of the panelcreator
     * @return returns the requested panelcreator
     */
    public AbstractPanelCreator createPanelCreator (String panelCreatorName) {

        if (!myPanelCreatorMap.containsKey(panelCreatorName)) {
            myPanelCreatorMap.put(panelCreatorName, createPanelCreatorInstance(panelCreatorName));
        }

        AbstractPanelCreator myCreator = myPanelCreatorMap.get(panelCreatorName);
        return myCreator;
    }

    /**
     * method to create a framecreator
     * 
     * @param frameCreatorName name of the framecreator
     * @return returns the requested framecreator
     */
    public AbstractFrameCreator createFrameCreator (String frameCreatorName) {
        return createFrameCreatorInstance(frameCreatorName);
    }

    /**
     * this method will look up the class in the Factory.properties file
     * 
     * @param creatorName
     * @return the full path to the specified class
     */
    private String retrieveFullClassPath (String creatorName) {
        return myResources.getString(creatorName) + "." + creatorName;
    }

    /**
     * 
     * @param panelCreatorName name of the panelcreator
     * @return a panelcreator
     */
    private AbstractPanelCreator createPanelCreatorInstance (String panelCreatorName) {
        return (AbstractPanelCreator) Reflection
                .createInstance(retrieveFullClassPath(panelCreatorName), myArcade);
    }

    /**
     * 
     * @param frameCreatorName name of the framecreator
     * @return a framecreator
     */
    private AbstractFrameCreator createFrameCreatorInstance (String frameCreatorName) {
        return (AbstractFrameCreator) Reflection
                .createInstance(retrieveFullClassPath(frameCreatorName), myArcade);
    }

}
