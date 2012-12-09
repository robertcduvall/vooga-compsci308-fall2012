package util.input.configurecontrollergui.controller;

import java.util.ArrayList;
import java.util.List;
import util.datatable.UnmodifiableRowElement;
import util.input.core.InputControlModifier;

public class RadioButtonController {

    private InputControlModifier myModifier;
    
    public RadioButtonController(InputControlModifier controllerModifier) {
        myModifier = controllerModifier;
    }
    
    public List<String> getData() {
        List<UnmodifiableRowElement> controllerData = myModifier.getControlDetails();
        
        ArrayList<String> buttonMethods = new ArrayList<String>();
        for(UnmodifiableRowElement re: controllerData) {
            String buttonDescription = (String) re.getEntry("Button Description");
            String actionDescription = (String) re.getEntry("Action Description");
            buttonMethods.add(actionDescription + " -----> " + buttonDescription);
        }
       
        return buttonMethods;
    }
}
