package util.datatable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;

/**
 * A modifiable row element that allows users
 * to do standard store, retrieve, edit and delete.
 * @author Lance
 */
public class ModifiableRowElement extends RowElement{

    /**
     * Instantiating an empty row element.
     */
    public ModifiableRowElement() {
        super();
    }
    
    
    /**
     * Instantiating a row element with initial values from a row element.
     * @param re
     */
    public ModifiableRowElement(RowElement re) {
        super(re);
    }
    
    /**
     * Instantiating a row element with initial values from a map.
     * @param map - initial data values
     */
    public ModifiableRowElement(Map<String , Object> map) {
        this();
        getUnderlyingData().putAll(map);
    }

    /**
     * Write to specific column of the row element.
     * @param  col - Column key
     * @param value - Specific Value
     * @throws UnrecognizedColumnNameException - column name doesnt exist
     */
    public void setEntry(String col , Object value) throws
        UnrecognizedColumnNameException {
        if (getUnderlyingData().containsKey(col)) {
            getUnderlyingData().put(col, value);
        }
        else {
            throw new UnrecognizedColumnNameException(col);
        }
    }

    /**
     * Write to specific column of the row element.
     * @param  map - map of values to be written
     * @throws UnrecognizedColumnNameException - column name does not exist
     */
    public void setEntry (Map<String, Object> map) throws
        UnrecognizedColumnNameException {
        Set<String> keySet = map.keySet();
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String pKey = (String) it.next();
            if (getUnderlyingData().containsKey(pKey)) {
                getUnderlyingData().put(pKey , map.get(pKey));
            }
            else {
                throw new UnrecognizedColumnNameException(pKey);
            }
        }
    }

    /**
     * Adds a new column.
     * @param col - new column to be added
     * @throws RepeatedColumnNameException
     * @throws InvalidXMLTagException 
     */
    public void addNewColumn(String col) throws
        RepeatedColumnNameException, InvalidXMLTagException  {
        if(getUnderlyingData().containsKey(col)){
            throw new RepeatedColumnNameException(col);
        }
        else if(col.split(" ").length != 1){
            throw new InvalidXMLTagException(col);
        }
        else{
            getUnderlyingData().put(col, null);
        }
    }

}