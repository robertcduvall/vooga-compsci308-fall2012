package util.networking.datatable;
import java.util.Collection;

/**
 * @author Connor Gordon
 * 
 */
import java.util.EventObject;
import util.datatable.UnmodifiableRowElement;
public class DataRowsEvent extends EventObject {

    private Collection<UnmodifiableRowElement > myDataRows;
    
    public DataRowsEvent (Object source, Collection <UnmodifiableRowElement> c ){
        super(source);
        myDataRows = c;
    }
    
    public Collection<UnmodifiableRowElement> getDataRows(){
        return myDataRows;
    }

}
