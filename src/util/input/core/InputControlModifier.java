package util.input.core;

import util.datatable.DataTable;
import util.datatable.UnmodifiableRowElement;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;
import util.input.inputhelpers.FlagPair;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Amay
 *
 */
public class InputControlModifier {
    
    private final static String BUTTON_DESCRIPTION = "Button Description";
    private final static String KEYCODE = "KeyCode";
    private static final String ACTION_DESCRIPTION = "Action Description";
    private static final String TUPLE = "Tuple";
    
    private DataTable myTable;
    @SuppressWarnings("rawtypes")
    private Controller myGameController;
    
    
    @SuppressWarnings("rawtypes")
    public InputControlModifier(Controller gameController) throws RepeatedColumnNameException, InvalidXMLTagException {
        myGameController = gameController;
        myTable = myGameController.getUnmodifiableDataTable();
    }
    
    public UnmodifiableRowElement getControlData(String buttonDescription) {
        return myTable.find(BUTTON_DESCRIPTION, buttonDescription);
    }
    
    @SuppressWarnings("unchecked")
    public void swap(String oldButton, String newButton) throws UnrecognizedColumnNameException {
        UnmodifiableRowElement oldRowElem = getControlData(oldButton);
        UnmodifiableRowElement newRowElem = getControlData(newButton);
        if(oldRowElem!= null && newRowElem != null) {
            myTable.editRowEntry(KEYCODE, oldRowElem.getEntry(KEYCODE), BUTTON_DESCRIPTION, newButton);
            myTable.editRowEntry(KEYCODE, newRowElem.getEntry(KEYCODE), BUTTON_DESCRIPTION, oldButton);
            myTable.editRowEntry(BUTTON_DESCRIPTION, newButton, KEYCODE, newRowElem.getEntry(KEYCODE));
            myTable.editRowEntry(BUTTON_DESCRIPTION, oldButton, KEYCODE, oldRowElem.getEntry(KEYCODE));
            UnmodifiableRowElement updatedOldRowElem = getControlData(oldButton);
            UnmodifiableRowElement updatedNewRowElem = getControlData(newButton);
            myGameController.addControlToTable((Integer) updatedOldRowElem.getEntry(KEYCODE), (FlagPair<Object, Method>) updatedOldRowElem.getEntry(TUPLE), (String) updatedOldRowElem.getEntry(BUTTON_DESCRIPTION), (String) updatedOldRowElem.getEntry(ACTION_DESCRIPTION));
            myGameController.addControlToTable((Integer) updatedNewRowElem.getEntry(KEYCODE), (FlagPair<Object, Method>) updatedNewRowElem.getEntry(TUPLE), (String) updatedNewRowElem.getEntry(BUTTON_DESCRIPTION), (String) updatedNewRowElem.getEntry(ACTION_DESCRIPTION));
        }
    }
    
    public List<UnmodifiableRowElement> getControlDetails() {
        return (List<UnmodifiableRowElement>) myTable.getDataRows();
    }

}