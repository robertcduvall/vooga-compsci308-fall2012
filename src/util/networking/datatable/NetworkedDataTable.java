package util.networking.datatable;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import util.datatable.ModifiableRowElement;
import util.datatable.UnmodifiableRowElement;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;
import util.networking.Client;

public class NetworkedDataTable {
    
    //Client -- Accessor Client
    //Client -- Modifier Client
    
    public NetworkedDataTable (DataProtocol d, String host) throws IOException {
        
    }

    public void addNewColumns (String strKey){}
    
    public void addNewColumns (String[] strArray){}
    
    public void addNewRow (Map<String , Object> mapEntry) {}
    
    public void addNewRow (ModifiableRowElement re){}
    
    public void addNewRow (UnmodifiableRowElement re){}
    
    public void deleteRowEntry (String strKey, Object value){} 
    
    public Collection<String> getColumnNames (){}
    
    public void clear(){}
    
    public void editRowEntry (String strKeyRef, Object valueRef,
                              String strKeyNew, Object valueNew){}
    

    public void editRowEntry (String strKeyRef, Object valueRef,
            Map<String, Object> map) {}
    
   
    public Collection <UnmodifiableRowElement> getDataRows(){}
    
    
    
    public UnmodifiableRowElement find (String strKey, Object value){}
    
 
    public void save (String location){}

    public void load (String location) throws
        RepeatedColumnNameException, InvalidXMLTagException{}

}
