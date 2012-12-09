package util.networking.datatable;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

public class ColumnNamesEvent extends EventObject{
    
    Collection<String> myColNames;

    public ColumnNamesEvent (Object source, Collection<String> colNames) {
        super(source);
        myColNames = colNames;
    }
    
    public Collection<String> getColumnNames(){
        return myColNames;
    }

}
