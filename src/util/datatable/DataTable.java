package util.datatable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.datatable.exceptions.UnrecognizedColumnNameException;
import util.xml.XmlUtilities;

/**
 * Data Table that can do standard store, retrieve, edit and delete.
 * Additionally it can load and save data in an XML file.
 * @author Lance
 *
 */
public class DataTable {

    private List<RowElement> myDataRows;
    private List<String> myColumnNames;
    
    private final String XMLPARENTTAG= "DataTable";
    private final String XMLROWTAG= "Row";
    private final String XMLROWTAGATTR = "number";


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
     * @throws InvalidXMLTagException - thrown if column name is
     * an invalid XML tag
     */
    public void addNewColumn (String strKey) throws
        RepeatedColumnNameException, InvalidXMLTagException {
        String[] strArray = strKey.split(",");
        addNewColumn(strArray);
    }

    /**
     * Adds new columns to the table.
     * @param strArray - String array of column names
     * @throws RepeatedColumnNameException - thrown
     * if a repeated column name is detected
     * @throws InvalidXMLTagException - thrown if column name is
     * an invalid XML tag
     */
    public void addNewColumn (String[] strArray) throws
        RepeatedColumnNameException, InvalidXMLTagException  {
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
    public void addNewRowEntry(Map<String , Object> mapEntry) {
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
     * @param strKey - column name of key reference to the row to be deleted
     * @param value - specific value of the row element to be deleted
     */
    public void deleteRowEntry (String strKey, Object value) {
        Iterator<RowElement> it= myDataRows.iterator();
        while (it.hasNext()) {
            if (value.equals(it.next().getEntry(strKey))) {
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
     * Returns an unmodifiable list of column names.
     * @return - List of column names
     */
    public List<String> getColumnNames () {
        return Collections.unmodifiableList(myColumnNames);
    }


    /**
     * Edits a row entry.
     * @param strKeyRef - key to reference the row element to be edited
     * @param valueRef - specific value of the row element to be edited
     * @param strKeyNew - key to reference the column to be edited
     * @param valueNew - specific value to write to the table
     * @throws UnrecognizedColumnNameException - unrecognized column name
     */
    public void editRowEntry (String strKeyRef, Object valueRef, 
            String strKeyNew, Object valueNew) throws 
            UnrecognizedColumnNameException {
        Iterator<RowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            RowElement re = it.next();
            if (valueRef.equals(re.getEntry(strKeyRef))) {
                re.setEntry(strKeyNew, valueNew);
            }
        }
    }

    /**
     * Edits a row entry.
     * @param strKeyRef - key to reference the row element to be edited
     * @param valueRef - specific value of the row element to be edited
     * @param map - map of values to be written to the table
     * @throws UnrecognizedColumnNameException - unrecognized column name
     */
    public void editRowEntry (String strKeyRef, Object valueRef,
            Map<String, Object> map) throws UnrecognizedColumnNameException {
        Iterator<RowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            RowElement re = it.next();
            if (valueRef.equals(re.getEntry(strKeyRef))) {
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
     * @param value - specific value that identifies the row element
     * @return
     */
    public UnmodifiableRowElement find (String strKey, Object value) {
        Iterator<RowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            RowElement re = it.next();
            if (value.equals(re.getEntry(strKey))) {
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
    
    /**
     * Saves DataTable on an XML file for later use.
     * @param location - location where file is saved
     */
    public void save (String location) {
        Document doc = XmlUtilities.makeDocument();
        Element header = XmlUtilities.makeElement(doc, XMLPARENTTAG);
        doc.appendChild(header);
        int i = 0;
        for (RowElement re : getDataRows()) {
            Element parentRow = 
                    XmlUtilities.makeElement(doc, XMLROWTAG, XMLROWTAGATTR, String.valueOf(i));
            header.appendChild(parentRow);
            i++;
            for (String colName: getColumnNames()){
                String writeElement=(String) re.getEntry(colName);
                if (re.getEntry(colName)==null){
                    writeElement="";
                }
                XmlUtilities.appendElement(doc, parentRow, colName, writeElement);
            }
        }
        XmlUtilities.write(doc , location);
    }

    
    
    /**
     * Loads DataTable from a previously saved XML file.
     * @param location - location of file name
     * @throws RepeatedColumnNameException - thrown if a column name is repeated
     * @throws InvalidXMLTagException - invalid column name tag
     */
    public void load (String location) throws
        RepeatedColumnNameException, InvalidXMLTagException {
        Map <String, Object> colValueMap = null; 
        Document doc = XmlUtilities.makeDocument(location);
        Element topDT = doc.getDocumentElement();
        Collection <Element> dataC = XmlUtilities.getElements(topDT, XMLROWTAG);
        for (Element rowEl : dataC){
            Collection <Element> colTags = XmlUtilities.getElements(rowEl);
            for (Element colVal: colTags){
                colValueMap = new HashMap <String , Object>();
                String colName = XmlUtilities.getTagName(colVal);
                Object value = XmlUtilities.getContent(colVal);
                colValueMap.put(colName, value);
                addNewColumn(colName);
            }
            addNewRowEntry(colValueMap);
        }
    }

}