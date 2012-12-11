package util.networking.datatable;
import java.util.EventObject;
import util.datatable.UnmodifiableRowElement;

/**
 * 
 * @author Connor Gordon
 *
 */
public class FoundRowElementEvent extends EventObject {

    private UnmodifiableRowElement myElement; 
    
    public FoundRowElementEvent (Object source, UnmodifiableRowElement e) {
        super(source);
        myElement = e;
    }
    
    public UnmodifiableRowElement getElement(){
        return myElement;
    }

}
