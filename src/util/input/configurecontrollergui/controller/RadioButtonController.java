package util.input.configurecontrollergui.controller;

import java.util.ArrayList;
import java.util.List;
import util.datatable.UnmodifiableRowElement;
import util.input.core.InputControlModifier;

/**
 * @author Amay
 *
 */
public class RadioButtonController {

    private InputControlModifier myModifier;
    private List<String> myButtonDescriptions;
    
    public RadioButtonController(InputControlModifier controllerModifier) {
        myModifier = controllerModifier;
        myButtonDescriptions = new ArrayList<String>();
    }
    
    public List<String> getData() {
        List<UnmodifiableRowElement> controllerData = myModifier.getControlDetails();
        
        ArrayList<String> buttonMethods = new ArrayList<String>();
        for(UnmodifiableRowElement re: controllerData) {
            String buttonDescription = (String) re.getEntry("Button Description");
            String actionDescription = (String) re.getEntry("Action Description");
            buttonMethods.add(actionDescription + " -----> " + buttonDescription);
            myButtonDescriptions.add(buttonDescription);
        }
       
        return buttonMethods;
    }
    
    public List<String> getButtonDescriptions() {
        return myButtonDescriptions;
    }
}
