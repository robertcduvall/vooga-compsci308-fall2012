package util.networking.datatable;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import util.datatable.ModifiableRowElement;
import util.datatable.UnmodifiableRowElement;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;
import util.networking.Client;

public class NetworkedDataTable {
    
    Client myClient;
    //Client -- Modifier Client
    private DataProtocol myProtocol;
    
    public NetworkedDataTable (DataProtocol d, String host) throws IOException {
        
    }

    public void addNewColumns (String strKey) {
        String[] strArray = {strKey};
        addNewColumns(strArray);
       
        
    }
    
    public void addNewColumns (String[] strArray){
        myClient.send(myProtocol.createAddColumns(strArray));
    }
    
    public void addNewRow (Map<String , Object> mapEntry) {
        myClient.send(myProtocol.createAddRow(mapEntry));
    }
    
    public void addNewRow (ModifiableRowElement re){
        addNewRow(re.getAllEntries());
    }
    
    public void addNewRow (UnmodifiableRowElement re){
        addNewRow(re.getAllEntries());
    }
    
    public void deleteRowEntry (String strKey, Object value){
        myClient.send(myProtocol.createRowEntry(strKey, value));
    } 
    
    public Collection<String> getColumnNames (){
        myClient.send(myProtocol.createGetColumnNames());
        return null; 
    }
    
    public void clear(){
        myClient.send(myProtocol.createClear());
    }
    
    public void editRowEntry (String strKeyRef, Object valueRef,
                              String strKeyNew, Object valueNew){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(strKeyNew,  valueNew);
        editRowEntry(strKeyRef, valueRef, map);
    }
    

    public void editRowEntry (String strKeyRef, Object valueRef,
        Map<String, Object> map) {
        myClient.send(myProtocol.createEdit(strKeyRef, valueRef, map));
    }
    
   
    public Collection <UnmodifiableRowElement> getDataRows(){
        myClient.send(myProtocol.createGetDataRows());
        return null;
    }
    
    
    
    public UnmodifiableRowElement find (String strKey, Object value){
        myClient.send(myProtocol.createFind(strKey, value));
        return null;
    }
    
 
    public void save (String location){
        myClient.send(myProtocol.createSave());
    }

    public void load (String location) throws
        RepeatedColumnNameException, InvalidXMLTagException{
        myClient.send(myProtocol.createLoad(location));
    }

}
