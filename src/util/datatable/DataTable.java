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

    private List<ModifiableRowElement> myDataRows;
    private List<String> myColumnNames;

    private static final String XMLPARENTTAG= "DataTable";
    private static final String XMLROWTAG= "Row";
    private static final String XMLROWTAGATTR = "number";


    /**
     * Instantiating an empty new data table.
     */
    public DataTable () {
        myColumnNames = new ArrayList<String>();
        myDataRows = new ArrayList<ModifiableRowElement>();
    }

    /**
     * Instantiating a copied data table.
     * @param dTable new Data Table is a copy of this data table
     */
    public DataTable (DataTable dTable) {
        myColumnNames = new ArrayList<String>(dTable.getColumnNames());
        myDataRows = new ArrayList<ModifiableRowElement>(
                RowElement.modifiableRowElement(dTable.getDataRows()));
    }

    /**
     * Adds new columns to the table. Accepts a comma separated
     * string and adds these as columns.
     * Warning: An exception will be thrown if column names
     * have spaces.
     * @param strKey - string of column names separated with commas
     * @throws RepeatedColumnNameException - thrown
     * if a repeated column name is detected
     * @throws InvalidXMLTagException - thrown if column name is
     * an invalid XML tag
     */
    public void addNewColumns (String strKey) throws
        RepeatedColumnNameException, InvalidXMLTagException {
        String[] strArray = strKey.split(",");
        addNewColumns(strArray);
    }

    /**
     * Adds new columns to the table. This method
     * accepts an array of strings.
     * @param strArray - String array of column names
     * @throws RepeatedColumnNameException - thrown
     * if a repeated column name is detected
     * @throws InvalidXMLTagException - thrown if column name is
     * an invalid XML tag
     */
    public void addNewColumns (String[] strArray) throws
        RepeatedColumnNameException, InvalidXMLTagException  {
        for (String strName : strArray) {
            for  (ModifiableRowElement rowE: myDataRows) {
                rowE.addNewColumn(strName);
            }
            if (myColumnNames.contains(strName)) {
                throw new RepeatedColumnNameException(strName);
            }
            myColumnNames.add(strName);
        }
    }

    /**
     * Adds a new row entry to the table via passing in
     * a map of column names to values.
     * @param mapEntry - map of column names to data values
     */
    public void addNewRow (Map<String , Object> mapEntry) {
        for (String key:getColumnNames()) {
            if (!mapEntry.containsKey(key)) {
                mapEntry.put(key, null);
            }
        }
        ModifiableRowElement rowE = new ModifiableRowElement(mapEntry);
        myDataRows.add(rowE);
    }


    /**
     * Adds a new row entry to the table via passing in
     * a RowElement.
     * Note: a copy of the row element is added not
     * the row element itself.
     * @param re
     */
    public void addNewRow (RowElement re) {
        myDataRows.add(new ModifiableRowElement(re));
    }

    /**
     * Deletes a row element.
     * @param strKey - column name of key reference to the row to be deleted
     * @param value - specific value of the row element to be deleted
     */
    public void deleteRowEntry (String strKey, Object value) {
        Iterator<ModifiableRowElement> it= myDataRows.iterator();
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
    public Collection<String> getColumnNames () {
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
        Map<String, Object> incomingData = new HashMap <String, Object> ();
        incomingData.put(strKeyNew, valueNew);
        editRowEntry (strKeyRef , valueRef , incomingData);
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
        Iterator<ModifiableRowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            ModifiableRowElement re = it.next();
            if (valueRef.equals(re.getEntry(strKeyRef))) {
                re.setEntry(map);
            }
        }
    }

    /**
     * Returns an unmodifiable list of unmodifiable row elements.
     * @return - unmodifiable list of row elements
     */
    public Collection <UnmodifiableRowElement> getDataRows() {
        List <UnmodifiableRowElement> unmodList = new ArrayList <UnmodifiableRowElement>();
        for (ModifiableRowElement re : myDataRows) {
            UnmodifiableRowElement ure = RowElement.unmodifiableRowElement(re);
            unmodList.add(ure);
        }
        return Collections.unmodifiableList(unmodList);
    }


    /**
     * Returns an unmodifiableRowElement with
     * the column name and value specified.
     * @param strKey - key to reference row element to be returned
     * @param value - specific value that identifies the row element
     * @return
     */
    public UnmodifiableRowElement find (String strKey, Object value) {
        Iterator<ModifiableRowElement> it = myDataRows.iterator();
        while (it.hasNext()) {
            RowElement re = it.next();
            if (value.equals(re.getEntry(strKey))) {
                return new UnmodifiableRowElement(re);
            }
        }
        return null;
    }


    /**
     * Returns the contents of the data table in a String.
     */
    public String toString () {
        Iterator<ModifiableRowElement> it = myDataRows.iterator();
        String aggregateData = "";
        while (it.hasNext()) {
            RowElement re = it.next();
            aggregateData = aggregateData + "\n" + re.toString();
        }
        return aggregateData;
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
                    XmlUtilities.makeElement(
                            doc, XMLROWTAG, XMLROWTAGATTR, String.valueOf(i));
            header.appendChild(parentRow);
            i++;
            for (String colName: getColumnNames()) {
                String writeElement = (String) re.getEntry(colName);
                if (re.getEntry(colName)==null) {
                    writeElement = "";
                }
                XmlUtilities.appendElement(
                        doc, parentRow, colName, writeElement);
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
        Document doc = XmlUtilities.makeDocument(location);
        Element topDT = doc.getDocumentElement();
        Collection <Element> dataC = XmlUtilities.getElements(topDT, XMLROWTAG);
        for (Element rowEl : dataC) {
            Collection <Element> colTags = XmlUtilities.getElements(rowEl);
            Map <String, Object> colValueMap  =
                    new HashMap <String , Object>();
            for (Element colVal: colTags) {
                String colName = XmlUtilities.getTagName(colVal);
                Object value = XmlUtilities.getContent(colVal);
                colValueMap.put(colName, value);
                addNewColumns(colName);
            }
            addNewRow(colValueMap);
        }
    }
}