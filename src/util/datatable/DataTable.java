package util.datatable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;

public class DataTable {

    private List<String> myColumnNames;
    protected List<RowElement> myDataRows;

    public DataTable () {
        myColumnNames = new ArrayList<String>();
        myDataRows = new ArrayList<RowElement>();
    }

    public DataTable (DataTable dataT) {
        myColumnNames = new ArrayList<String>(dataT.getColumnNames());
        myDataRows = new ArrayList<RowElement>(dataT.getDataRows());
    }

    public void addNewColumn (String strKey) throws RepeatedColumnNameException {
        String[] strArray = strKey.split(",");
        addNewColumn(strArray);
    }
    
    public void addNewColumn (String[] strArray) throws RepeatedColumnNameException  {
        for (String strName : strArray) {
            for  (RowElement rowE: myDataRows){
                rowE.addNewColumn(strName);
            }
            
            if (myColumnNames.contains(strName)){
                throw new RepeatedColumnNameException(strName);
            }
            
            myColumnNames.add(strName);
        }
    }

    public void addNewRowEntry(Map<String,String> mapEntry){
        for (String key:getColumnNames()){
            if(!mapEntry.containsKey(key)){
                mapEntry.put(key, null);
            }
        }
        RowElement rowE = new RowElement(mapEntry);
        myDataRows.add(rowE);
    }
    
    public void deleteRowEntry (String strKey, String strValue) {
        Iterator<RowElement> it= myDataRows.iterator();
        while(it.hasNext()){
            if (strValue.equals((it.next().getEntry(strKey)))){
                it.remove();
            }
        }
    }
    
    public void clear () {
        myDataRows.clear();
    }
    
    public List<String> getColumnNames () {
        return Collections.unmodifiableList(myColumnNames);
    }
    
    

    public void editRowEntry (String strKey, String strValue, String strKeyNew,
            String strValueNew) throws UnrecognizedColumnNameException {
        Iterator<RowElement> it= myDataRows.iterator();
        while(it.hasNext()){
            RowElement re=it.next();
            if (strValue.equals(re.getEntry(strKey))){
                re.setEntry(strKeyNew, strValueNew);
            }
        }
    }
    
    public void editRowEntry (String strKey, String strValue,
            Map<String, String> map) throws UnrecognizedColumnNameException {
        Iterator<RowElement> it= myDataRows.iterator();
        while(it.hasNext()){
            RowElement re=it.next();
            if (strValue.equals(re.getEntry(strKey))){
                re.setEntry(map);
            }
        }
    }
    
    public List<RowElement> getDataRows(){
        return Collections.unmodifiableList(myDataRows);
    }

    public UnmodifiableRowElement find (String strKey, String strValue) {
        Iterator<RowElement> it= myDataRows.iterator();
        while(it.hasNext()){
            RowElement re=it.next();
            if (strValue.equals(re.getEntry(strKey))){
                return new UnmodifiableRowElement(re);
            }
        }
        return null;
    }
    
    public void viewContents(){
        System.out.println("Table Contents: ");
        Iterator<RowElement> it= myDataRows.iterator();
        while(it.hasNext()){
            RowElement re=it.next();
            re.printData();
        }
    }
   
    public void save (String string) {
        // TODO Auto-generated method stub
        
    }

    public void load (String string) {
        // TODO Auto-generated method stub
        
    }

}