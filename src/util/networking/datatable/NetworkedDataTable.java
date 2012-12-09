package util.networking.datatable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.datatable.ModifiableRowElement;
import util.datatable.UnmodifiableRowElement;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;
import util.networking.Client;
import util.networking.chat.ChatCommand;
import util.networking.chat.ChatListener;
import util.networking.chat.ErrorEvent;
import util.networking.chat.MessageReceivedEvent;
import util.networking.chat.UsersUpdateEvent;

public class NetworkedDataTable extends Client {
    
    List<DataListener> myDataListeners;
    
    //Client -- Modifier Client
    private DataProtocol myProtocol;
    
    public NetworkedDataTable (DataProtocol d, String host) throws IOException {
        super(host, d.getPort());
        myDataListeners = new ArrayList<DataListener>();
    }

    public void addNewColumns (String strKey) {
        String[] strArray = {strKey};
        addNewColumns(strArray);
       
        
    }
    
    public void addListener(DataListener l){
        myDataListeners.add(l);
    }
    
    public void removeListener(DataListener l){
        myDataListeners.remove(l);
    }
    
    public void addNewColumns (String[] strArray){
        send(myProtocol.createAddColumns(strArray));
    }
    
    public void addNewRow (Map<String , Object> mapEntry) {
        send(myProtocol.createAddRow(mapEntry));
    }
    
    public void addNewRow (ModifiableRowElement re){
        addNewRow(re.getAllEntries());
    }
    
    public void addNewRow (UnmodifiableRowElement re){
        addNewRow(re.getAllEntries());
    }
    
    public void deleteRowEntry (String strKey, Object value){
        send(myProtocol.createDeleteRowEntry(strKey, value));
    } 
    
    public Collection<String> getColumnNames (){
        send(myProtocol.createGetColumnNames());
        return null; 
    }
    
    public void clear(){
        send(myProtocol.createClear());
    }
    
    public void editRowEntry (String strKeyRef, Object valueRef,
                              String strKeyNew, Object valueNew){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(strKeyNew,  valueNew);
        editRowEntry(strKeyRef, valueRef, map);
    }
    

    public void editRowEntry (String strKeyRef, Object valueRef,
        Map<String, Object> map) {
        send(myProtocol.createEdit(strKeyRef, valueRef, map));
    }
    
   
    public Collection <UnmodifiableRowElement> getDataRows(){
        send(myProtocol.createGetDataRows());
        return null;
    }
    
    
    
    public UnmodifiableRowElement find (String strKey, Object value){
        send(myProtocol.createFind(strKey, value));
        return null;
    }
    
 
    public void save (String location){
        send(myProtocol.createSave());
    }

    public void load (String location) throws
        RepeatedColumnNameException, InvalidXMLTagException{
        send(myProtocol.createLoad(location));
    }

    @Override
    public void processInputFromServer (String input) {
        if (input == null || "".equals(input.trim())) return;
        System.out.println("client received: " + input);
        DataCommand type = myProtocol.getType(input);
        Method m;
        try {
            m = this.getClass().getDeclaredMethod("process" + type.toString(), String.class);
            m.setAccessible(true);
            m.invoke(this, input);
        }
        catch (SecurityException e) {
        }
        catch (NoSuchMethodException e) {
        }
        catch (IllegalArgumentException e) {
        }
        catch (IllegalAccessException e) {
        }
        catch (InvocationTargetException e) {
        }
    }
    
    private void processFound(String input){
        fireFoundRowElementEvent(new UnmodifiableRowElement(myProtocol.getRowElement(input)));
    }
    private void processGetColumnNames(String input){
        fireColumnNamesEvent(myProtocol.getColumnNames(input));
    }
    private void processGetDataRows(String input){
        fireDataRowsEvent(myProtocol.getDataRows(input));
    }
    
    private void processUnknown(String input){}
    
    
    private synchronized void fireColumnNamesEvent (Collection<String> names) {
        ColumnNamesEvent e = new ColumnNamesEvent(this, names);
        for (DataListener dl : myDataListeners) {
            dl.handleColumnNamesEvent(e);
        }
    }

    private synchronized void fireDataRowsEvent (Collection<UnmodifiableRowElement> dataRows) {
        DataRowsEvent e = new DataRowsEvent(this, dataRows);
        for (DataListener dl : myDataListeners) {
            dl.handleDataRowsEvent(e);
        }
    }

    private void fireFoundRowElementEvent (UnmodifiableRowElement element) {
        FoundRowElementEvent e = new FoundRowElementEvent(this, element);
        for (DataListener dl : myDataListeners) {
            dl.handleFoundRowElementEvent(e);
        }
    }
}
