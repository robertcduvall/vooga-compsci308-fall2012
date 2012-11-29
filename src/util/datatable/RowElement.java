package util.datatable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that describes a generic RowElement
 * containing only the basic functionality of
 * retrieving data.
 *
 * @author Lance
 *
 */
abstract class RowElement {
    private Map<String , Object> myData;
    
    /**
     * Instantiating an empty row element.
     */
    protected RowElement (){
        myData = new HashMap<String , Object>();
    }
    
    /**
     * Instantiating a row element with initial values from a row element.
     * @param map - initial data values
     */
    protected RowElement (RowElement re) {
        this();
        myData.putAll(re.getAllEntries());
    }

    /**
     * Retrieve an entry in row element.
     * @param col - column key
     * @return
     */
    public Object getEntry(String s) {
        return myData.get(s);
    }

    /**
     * Retrieve all keys and data.
     * @return - map of all the keys and data
     */
    public Map <String , Object> getAllEntries() {
        Map<String , Object> cpmap = new HashMap<String, Object>();
        cpmap.putAll(myData);
        return cpmap;
    }
    
    /**
     * Returns contents of row element in a string.
     * @return 
     */
    public String toString () {
        return myData.toString();
    }
    
    
    public static UnmodifiableRowElement unmodifiableRowElement (RowElement re) {
        return new UnmodifiableRowElement (re);       
    }
    
    public static Collection<UnmodifiableRowElement> unmodifiableRowElement (Collection <ModifiableRowElement> colRe) {
        Collection <UnmodifiableRowElement> modifiedColRe = new ArrayList <UnmodifiableRowElement> ();
        for (RowElement re : colRe) {
            modifiedColRe.add(unmodifiableRowElement (re));
        }
        return modifiedColRe;       
    }
    
    public static ModifiableRowElement modifiableRowElement (RowElement re) {
        return new ModifiableRowElement (re);       
    }
    
    public static Collection<ModifiableRowElement> modifiableRowElement (Collection <UnmodifiableRowElement> colRe) {
        Collection <ModifiableRowElement> modifiedColRe = new ArrayList <ModifiableRowElement> ();
        for (RowElement re : colRe) {
            modifiedColRe.add( modifiableRowElement (re));
        }
        return modifiedColRe;       
    }
    protected Map<String , Object > getUnderlyingData() {
        return myData;
    }

}
