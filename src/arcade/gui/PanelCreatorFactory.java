package arcade.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import util.reflection.Reflection;
import arcade.gui.panel.AbstractPanelCreator;


/**
 * This is the factory for panelcreators.
 * It will use reflection to instantiate the components necessary.
 * 
 * 
 * @author Michael Deng
 * 
 */
public class PanelCreatorFactory {

    private Arcade myArcade;
    private static ResourceBundle myResources;
    private Map<String, AbstractPanelCreator> myPanelCreatorMap;

    public PanelCreatorFactory (Arcade a) {
        myArcade = a;
        myResources = ResourceBundle.getBundle("arcade.gui.resources.Factory");
        myPanelCreatorMap = new HashMap<String, AbstractPanelCreator>();
    }

    public AbstractPanelCreator createPanelCreator (String panelCreatorName) {

        System.out.println(panelCreatorName);
        if (!myPanelCreatorMap.containsKey(panelCreatorName)) {
            myPanelCreatorMap.put(panelCreatorName, createInstance(panelCreatorName));
        }

        AbstractPanelCreator myCreator = myPanelCreatorMap.get(panelCreatorName);
//        myCreator.creatorSetup(myArcade);

        return myCreator;
    }

    private String retrieveFullClassPath (String panelCreatorName) {
        return myResources.getString(panelCreatorName) + "." + panelCreatorName;
    }

    /**
     * creates a new class according to the string given; if it does not
     * exist in the class map.
     * 
     * @param className name of the class to create
     * @return
     */
    private AbstractPanelCreator createInstance (String panelCreatorName) {
        AbstractPanelCreator newPanelCreator = null;

        newPanelCreator = (AbstractPanelCreator) Reflection.createInstance(retrieveFullClassPath(panelCreatorName), myArcade);
        
//        try {
//            newPanelCreator =
//                    (AbstractPanelCreator) Class.forName(retrieveFullClassPath(panelCreatorName))
//                            .newInstance();
//        }
//        catch (InstantiationException e) {
//            throwRuntimeException();
//        }
//        catch (IllegalAccessException e) {
//            throwRuntimeException();
//        }
//        catch (ClassNotFoundException e) {
//            throwRuntimeException();
//        }
//        catch (IllegalArgumentException e) {
//            throwRuntimeException();
//        }
//        catch (SecurityException e) {
//            throwRuntimeException();
//        }
        return newPanelCreator;
    }

//    /**
//     * This method will execute to handle all the exceptions thrown during class
//     * creation.
//     */
//    private void throwRuntimeException () {
//        throw new RuntimeException("Error in Factory");
//    }
}