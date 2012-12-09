package arcade.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import util.reflection.Reflection;
import arcade.gui.frame.AbstractFrameCreator;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * This is the factory used to create panelcreators and framecreators. The
 * factory uses a resource file to know where the panelcreators and
 * framecreators are saved. The factory uses reflection to instantiate the
 * requested components. Using the resources file together with reflection
 * we can ensure that the factory is fully extensible and that this code
 * does not need to be edited to support new panelcreators and framecreators.
 * 
 * It is also important to note that because some panelcreators may be
 * used over and over again to create the same panel, the factory always
 * maintains a reference to the panelcreators its has already instantiated;
 * therefore, the factory never instantiates the same panelcreator more
 * than once.
 * 
 * 
 * @author Michael Deng
 * 
 */
public class CreatorFactory {

    private ResourceBundle myResources;
    private final String myResourcesPath = "arcade.gui.resources.Factory";

    private Arcade myArcade;
    private Map<String, AbstractPanelCreator> myPanelCreatorMap;

    /**
     * Constructor for factory
     * 
     * @param a reference to the arcade
     */
    public CreatorFactory (Arcade a) {
        myArcade = a;
        myResources = ResourceBundle.getBundle(myResourcesPath);
        myPanelCreatorMap = new HashMap<String, AbstractPanelCreator>();
    }

    /**
     * Public method used to get the requested panelcreator.
     * 
     * @param panelCreatorName actual name of the requested panelcreator
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
     * Public method used to get the requested framecreator
     * 
     * @param frameCreatorName actual name of the requested framecreator
     * @return returns the requested framecreator
     */
    public AbstractFrameCreator createFrameCreator (String frameCreatorName) {
        return createFrameCreatorInstance(frameCreatorName);
    }

    /**
     * This method will look up the class path in the Factory.properties file
     * 
     * @param creatorName actual name of the panelcreator or framecreator
     * @return the relative path to the specified class
     */
    private String retrieveFullClassPath (String creatorName) {
        return myResources.getString(creatorName) + "." + creatorName;
    }

    /**
     * This method instantiates a new panelcreator using the reflection utility.
     * 
     * @param panelCreatorName actual name of the panelcreator
     * @return a panelcreator
     */
    private AbstractPanelCreator createPanelCreatorInstance (String panelCreatorName) {
        return (AbstractPanelCreator) Reflection
                .createInstance(retrieveFullClassPath(panelCreatorName), myArcade);
    }

    /**
     * This method instantiates a new framecreator using the reflection utility.
     * 
     * @param frameCreatorName actual name of the framecreator
     * @return a framecreator
     */
    private AbstractFrameCreator createFrameCreatorInstance (String frameCreatorName) {
        return (AbstractFrameCreator) Reflection
                .createInstance(retrieveFullClassPath(frameCreatorName), myArcade);
    }

}
