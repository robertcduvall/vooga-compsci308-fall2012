package util.networking.datatable;

import java.util.List;
import java.util.Map;
import util.datatable.ModifiableRowElement;
import util.networking.datatable.DataCommand;
import util.datatable.UnmodifiableRowElement;
import util.networking.chat.ChatCommand;

public interface DataProtocol {
    DataCommand getType (String input);
    
    ModifiableRowElement getRowElement(String input);
    String getStringKey(String input);
    String getObjValue(String input);
    List<String> getColumnNames(String input);
    
    String createAddColumns(String[] strArray);
    String createEdit(String strKey, Object value, Map<String, Object> mapEntry);
    String createDeleteRowEntry(String strKey, Object value);
    String createGetDataRows();
    String createGetColumnNames();
    String createClear();
    String createSave();
    String createLoad(String location);
    String createAddRow (Map<String , Object> mapEntry);
    String createFind(String strKey, Object value);
    
    int getPort();
    
}
