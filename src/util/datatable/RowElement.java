package util.datatable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;

/**
 * Container for a row in the datatable.
 * @author Lance
 *
 */
public class RowElement {

    private Map<String , String> myData;

    /**
     * Instantiating an empty row element.
     */
    public RowElement() {
        myData = new HashMap<String, String>();
    }

    /**
     * Instantiating a row element with initial values from a map.
     * @param map - initial data values
     */
    public RowElement(Map<String , String> map) {
        this();
        myData.putAll(map);
    }

    /**
     * Write to specific column of the row element.
     * @param  col - Column key
     * @param value - Specific Value
     * @throws UnrecognizedColumnNameException - column name doesnt exist
     */
    public void setEntry(String col , String value) throws
        UnrecognizedColumnNameException {
        if (myData.containsKey(col)) {
            myData.put(col, value);
        }
        else {
            throw new UnrecognizedColumnNameException(col);
        }
    }

    /**
     * Write to specific column of the row element.
     * @param  map - map of values to be written
     * @throws UnrecognizedColumnNameException - column name doesnt exist
     */
    public void setEntry (Map<String, String> map) throws
        UnrecognizedColumnNameException {
        Set<String> keySet = map.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String pKey = (String) it.next();
            if (myData.containsKey(pKey)) {
                myData.put(pKey , map.get(pKey));
            }
            else {
                throw new UnrecognizedColumnNameException(pKey);
            }
        }
    }


    /**
     * Retrieve an entry in row element.
     * @param col - column key
     * @return
     */
    public String getEntry(String col) {
        return myData.get(col);
    }


    /**
     * Retrieve all keys and data.
     * @return - map of all the keys and data
     */
    protected Map<String , String> getAllData() {
        Map<String , String> cpmap = new HashMap<String , String>();
        cpmap.putAll(myData);
        return cpmap;
    }

    /**
     * Adds a new column.
     * @param s
     * @throws RepeatedColumnNameException
     */
    protected void addNewColumn(String col) throws
        RepeatedColumnNameException  {
        if (!myData.containsKey(col)) {
            myData.put(col , null);
        }
        else {
            throw new RepeatedColumnNameException(col);
        }
    }

    /**
     * Prints contents of row element.
     */
    public void printData () {
        System.out.println(myData);
    }
}
