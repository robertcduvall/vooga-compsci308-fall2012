package util.networking.datatable;

import java.util.Collection;
import java.util.Map;
import util.datatable.ModifiableRowElement;
import util.datatable.UnmodifiableRowElement;
import util.networking.datatable.DataCommand;

/**
 * 
 * @author Connor Gordon
 * @author Oren Bukspan
 */
public interface DataProtocol {
    DataCommand getType (String input);
    
    ModifiableRowElement getRowElement(String input);
    String getStringKey(String input);
    String getObjValue(String input);
    Collection<String> getColumnNames(String input);
    Collection<UnmodifiableRowElement> getDataRows(String input);
    
    String createAddColumns(String[] strArray);
    String createEdit(String strKey, Object value, Map<String, Object> mapEntry);
    String createDeleteRowEntry(String strKey, Object value);
    String createGetDataRows();
    String createGetColumnNames();
    String createClear();
    String createSave(String location);
    String createLoad(String location);
    String createAddRow (Map<String , Object> mapEntry);
    String createFind(String strKey, Object value);
    
    int getPort();
    
}
