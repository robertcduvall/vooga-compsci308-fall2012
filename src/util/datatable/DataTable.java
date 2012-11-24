package util.datatable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;

/**
 * Data Table that can do standard store, retrieve, edit and delete.
 * Additionally it can load and save data in an XML file.
 * @author Lance
 *
 */
public class DataTable {

    private List<RowElement> myDataRows;
    private List<String> myColumnNames;


    /**
     * Instantiating an empty new data table.
     */
    public DataTable () {
        myColumnNames = new ArrayList<String>();
        myDataRows = new ArrayList<RowElement>();
    }

    /**
     * Used for copying a data table.
     * @param dataT new Data Table is a copy of this data table
     */
    public DataTable (DataTable dataT) {
        myColumnNames = new ArrayList<String>(dataT.getColumnNames());
        myDataRows = new ArrayList<RowElement>(dataT.getDataRows());
    }

    /**
     * Adds new columns to the table.
     * @param strKey - string of column names separated with commas
     * @throws RepeatedColumnNameException - thrown
     * if a repeated column name is detected
     */
    public void addNewColumn (String strKey) throws
        RepeatedColumnNameException {
        String[] strArray = strKey.split(",");
        addNewColumn(strArray);
    }

    /**
     * Adds new columns to the table.
     * @param strArray - String array of column names
     * @throws RepeatedColumnNameException - thrown
     * if a repeated column name is detected
     */
    public void addNewColumn (String[] strArray) throws
        RepeatedColumnNameException  {
        for (String strName : strArray) {
            for  (RowElement rowE: myDataRows) {
                rowE.addNewColumn(strName);
            }
            if (myColumnNames.contains(strName)) {
                throw new RepeatedColumnNameException(strName);
            }
            myColumnNames.add(strName);
        }
    }

    /**
     * Adds a new row entry to the table.
     * @param mapEntry - map of column names to data values
     */
    public void addNewRowEntry(Map<String , String> mapEntry) {
        for (String key:getColumnNames()) {
            if (!mapEntry.containsKey(key)) {
                mapEntry.put(key, null);
            }
        }
        RowElement rowE = new RowElement(mapEntry);
        myDataRows.add(rowE);
    }

    /**
     * Deletes a row element.
     * @param strKey - column name of key reference
     * @param strValue - specific value of the row element to be deleted
     */
    public void deleteRowEntry (String strKey, String strValue) {
        Iterator<RowElement> it= myDataRows.iterator();
        while (it.hasNext()) {
            if (strValue.equals(it.next().getEntry(strKey))) {
                it.remove();
            }
        }
    }

    /**
     * Clears data table.
     */
    public void clear () {
        myDataRows.clear();
    }

    /**
     * Returns a list of column names.
     * @return - List of column names
     */
    public List<String> getColumnNames () {
        return Collections.unmodifiableList(myColumnNames);
    }


    /**
     * Edits a row entry.
     * @param strKey - key to reference the row element to be edited
     * @param strValue - specific value of the row element to be edited
     * @param strKeyNew - key to reference the column to be edited
     * @param strValueNew - specific value to write to the table
     * @throws UnrecognizedColumnNameException - unrecognized column name
     */
    public void editRowEntry (String strKey, String strValue, String strKeyNew,
            String strValueNew) throws UnrecognizedColumnNameException {
        Iterator<RowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            RowElement re = it.next();
            if (strValue.equals(re.getEntry(strKey))) {
                re.setEntry(strKeyNew, strValueNew);
            }
        }
    }

    /**
     * Edits a row entry.
     * @param strKey - key to reference the row element to be edited
     * @param strValue - specific value of the row element to be edited
     * @param map - map of values to be written to the table
     * @throws UnrecognizedColumnNameException - unrecognized column name
     */
    public void editRowEntry (String strKey, String strValue,
            Map<String, String> map) throws UnrecognizedColumnNameException {
        Iterator<RowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            RowElement re = it.next();
            if (strValue.equals(re.getEntry(strKey))) {
                re.setEntry(map);
            }
        }
    }

    /**
     * Returns an unmodifiable list of all the row elements.
     * @return - unmodifiable list of row elements
     */
    public List<RowElement> getDataRows(){
        return Collections.unmodifiableList(myDataRows);
    }


    /**
     * Returns an unmodifiableRowElement with the column name and value
     * @param strKey - key to reference row element to be returned
     * @param strValue - specific value that identifies the row element
     * @return
     */
    public UnmodifiableRowElement find (String strKey, String strValue) {
        Iterator<RowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            RowElement re = it.next();
            if (strValue.equals(re.getEntry(strKey))) {
                return new UnmodifiableRowElement(re);
            }
        }
        return null;
    }


    /**
     * Prints contents of the data table.
     */
    public void viewContents() {
        System.out.println("Table Contents: ");
        Iterator<RowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            RowElement re = it.next();
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