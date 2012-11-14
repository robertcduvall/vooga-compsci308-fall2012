package arcade.gui;

import arcade.gui.frame.MainFrameCreator;
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
    
    public GUIFactory (Arcade a){
        myArcade = a;
    }
    
    
//    public MainFrameCreator createFrameCreator(String frameCreatorName){
//        
//        return null;
//    }
    
    public AbstractPanelCreator createPanelCreator(String panelType, String panelCreatorName){
        
        // TODO
        // use reflection w/ constructor to create.. then save reference to the panelCreatorName
        
        return null;
    }
    
    
    
    
    
    //  reflection will require one constructor . pass in reference to Arcade
    

}
