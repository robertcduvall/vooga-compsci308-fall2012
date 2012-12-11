package util.networking.datatable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import util.datatable.ModifiableRowElement;
import util.datatable.UnmodifiableRowElement;
import util.xml.XmlUtilities;


/**
 * 
 * @author Oren Bukspan
 * @author Connor Gordon
 */
public class GordonBukspanDataProtocol implements DataProtocol {

    private static final int PORT = 5679;

    @Override
    public DataCommand getType (String input) {
        String commandName;
        try {
            Document doc = loadXMLFrom(input);
            Element el = doc.getDocumentElement();
            commandName = el.getTagName();
            DataCommand c = DataCommand.getFromString(commandName);
            if (c != null) { return c; }
        }
        catch (SAXException e) {
            return DataCommand.UNKNOWN;
        }
        catch (IOException e) {
        }
        return DataCommand.UNKNOWN;
    }

    @Override
    public ModifiableRowElement getRowElement (String input) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Document doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Collection<Element> elements = XmlUtilities.getElements(root, "entry");
            for (Element e : elements) {
                Element key = XmlUtilities.getElement(e, "key");
                Element value = XmlUtilities.getElement(e, "value");
                map.put(XmlUtilities.getContent(key), XmlUtilities.getContent(value));
            }
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return new ModifiableRowElement(map);
    }

    @Override
    public String getStringKey (String input) {
        if (getType(input) != DataCommand.EDIT) return getValue(input, "key");
        String result = null;
        try {
            Document doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Element el = XmlUtilities.getElement(root, "toEdit");
            Element toFind = XmlUtilities.getElement(el, "key");
            result = XmlUtilities.getContent(toFind);
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return result;
    }

    @Override
    public String getObjValue (String input) {
        if (getType(input) != DataCommand.EDIT) return getValue(input, "value");
        String result = null;
        try {
            Document doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Element el = XmlUtilities.getElement(root, "toEdit");
            Element toFind = XmlUtilities.getElement(el, "value");
            result = XmlUtilities.getContent(toFind);
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return result;
    }

    @Override
    public Collection<String> getColumnNames (String input) {
        return getCollectionValues(input, "name");
    }

    @Override
    public Collection<UnmodifiableRowElement> getDataRows (String input) {
        Collection<UnmodifiableRowElement> result = new ArrayList<UnmodifiableRowElement>();

        Document doc;
        try {
            doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Collection<Element> rows = XmlUtilities.getElements(root, "row");
            for (Element row : rows) {
                Collection<Element> entries = XmlUtilities.getElements(row, "entry");
                Map<String, Object> map = new HashMap<String, Object>();
                for (Element entry : entries) {
                    Element key = XmlUtilities.getElement(entry, "key");
                    Element value = XmlUtilities.getElement(entry, "value");
                    map.put(XmlUtilities.getContent(key), XmlUtilities.getContent(value));
                }
                ModifiableRowElement el = new ModifiableRowElement(map);
                result.add(new UnmodifiableRowElement(el));
            }
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return result;
    }

    @Override
    public String[] getColumns (String input) {
        Collection<String> c = getCollectionValues(input, "column");
        return c.toArray(new String[0]);
    }

    @Override
    public Map<String, Object> getMapEntry (String input) {
        Document doc;
        Map<String, Object> mapEntry = new HashMap<String, Object>();
        try {
            doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Collection<Element> entries = XmlUtilities.getElements(root, "entry");
            for (Element entry : entries) {
                Element key = XmlUtilities.getElement(entry, "keyNew");
                Element value = XmlUtilities.getElement(entry, "valueNew");
                mapEntry.put(XmlUtilities.getContent(key), XmlUtilities.getContent(value));
            }
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return mapEntry;
    }

    @Override
    public String createAddColumns (String[] strArray) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        for (String s : strArray) {
            xmlTagMap.put("column", s);
        }
        return xmlFromMap("AddColumns", xmlTagMap);
    }

    @Override
    public String createEdit (String strKey, Object value, Map<String, Object> mapEntry) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "Edit");
        Element key = XmlUtilities.makeElement(doc, "key", strKey);
        Element curValue = XmlUtilities.makeElement(doc, "value", (String) value);
        Element current = XmlUtilities.makeElement(doc, "toEdit");
        current.appendChild(key);
        current.appendChild(curValue);
        root.appendChild(current);
        for (String s : mapEntry.keySet()) {
            key = XmlUtilities.makeElement(doc, "keyNew", s);
            curValue = XmlUtilities.makeElement(doc, "valueNew", (String) mapEntry.get(s));
            current = XmlUtilities.makeElement(doc, "entry");
            current.appendChild(key);
            current.appendChild(curValue);
            root.appendChild(current);
        }
        doc.appendChild(root);
        try {
            xmlString = XmlUtilities.getXmlAsString(doc).replace("\r\n", "").replace("\n", "");
        }
        catch (TransformerException e) {
        }
        return xmlString;
    }

    @Override
    public String createDeleteRowEntry (String strKey, Object value) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("key", strKey);
        xmlTagMap.put("value", (String) value);
        return xmlFromMap("DeleteRowEntry", xmlTagMap);
    }

    @Override
    public String createDataRows (Collection<UnmodifiableRowElement> rows) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "DataRows");
        for (UnmodifiableRowElement row : rows) {
            Element r = XmlUtilities.makeElement(doc, "row");
            Map<String, Object> map = row.getAllEntries();
            for (String s : map.keySet()) {
                Element key = XmlUtilities.makeElement(doc, "key", s);
                Element value = XmlUtilities.makeElement(doc, "value", (String) map.get(s));
                Element entry = XmlUtilities.makeElement(doc, "entry");
                entry.appendChild(key);
                entry.appendChild(value);
                r.appendChild(entry);
            }
            root.appendChild(r);
        }
        doc.appendChild(root);
        try {
            xmlString = XmlUtilities.getXmlAsString(doc).replace("\r\n", "").replace("\n", "");
        }
        catch (TransformerException e) {
        }
        return xmlString;
    }

    @Override
    public String createColumnNames (Collection<String> names) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "ColumnNames");
        for (String s : names) {
            Element name = XmlUtilities.makeElement(doc, "name", s);
            root.appendChild(name);
        }
        doc.appendChild(root);
        try {
            xmlString = XmlUtilities.getXmlAsString(doc).replace("\r\n", "").replace("\n", "");
        }
        catch (TransformerException e) {
        }
        return xmlString;
    }

    @Override
    public String createGetDataRows () {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        return xmlFromMap("GetDataRows", xmlTagMap);
    }

    @Override
    public String createGetColumnNames () {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        return xmlFromMap("GetColumnNames", xmlTagMap);
    }

    @Override
    public String createClear () {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        return xmlFromMap("Clear", xmlTagMap);
    }

    @Override
    public String createSave (String location) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("location", location);
        return xmlFromMap("Save", xmlTagMap);
    }

    @Override
    public String createLoad (String location) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("location", location);
        return xmlFromMap("Load", xmlTagMap);
    }

    @Override
    public String createAddRow (Map<String, Object> mapEntry) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "AddRow");
        for (String s : mapEntry.keySet()) {
            Element key = XmlUtilities.makeElement(doc, "key", s);
            Element value = XmlUtilities.makeElement(doc, "value", (String) mapEntry.get(s));
            Element current = XmlUtilities.makeElement(doc, "entry");
            current.appendChild(key);
            current.appendChild(value);
            root.appendChild(current);
        }
        doc.appendChild(root);
        try {
            xmlString = XmlUtilities.getXmlAsString(doc).replace("\r\n", "").replace("\n", "");
        }
        catch (TransformerException e) {
        }
        return xmlString;
    }

    @Override
    public String createFind (String strKey, Object value) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("key", strKey);
        xmlTagMap.put("value", (String) value);
        return xmlFromMap("Find", xmlTagMap);
    }

    @Override
    public String createFound (UnmodifiableRowElement row) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "Found");
        Map<String, Object> mapEntry = row.getAllEntries();
        for (String s : mapEntry.keySet()) {
            Element key = XmlUtilities.makeElement(doc, "key", s);
            Element value = XmlUtilities.makeElement(doc, "value", (String) mapEntry.get(s));
            Element current = XmlUtilities.makeElement(doc, "entry");
            current.appendChild(key);
            current.appendChild(value);
            root.appendChild(current);
        }
        doc.appendChild(root);
        try {
            xmlString = XmlUtilities.getXmlAsString(doc).replace("\r\n", "").replace("\n", "");
        }
        catch (TransformerException e) {
        }
        return xmlString;
    }

    @Override
    public int getPort () {
        return PORT;
    }

    // Protocol helper functions below.

    private static Document loadXMLFrom (String xml) throws SAXException, IOException {
        return loadXMLFrom(new ByteArrayInputStream(xml.getBytes()));
    }

    private static Document loadXMLFrom (InputStream is) throws SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {
        }
        Document doc = builder.parse(is);
        is.close();
        return doc;
    }

    private static String getValue (String input, String tagName) {
        String result = null;
        try {
            Document doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Element el = XmlUtilities.getElement(root, tagName);
            result = XmlUtilities.getContent(el);
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return result;
    }

    private static Collection<String> getCollectionValues (String input, String tagName) {
        Collection<String> result = new ArrayList<String>();
        try {
            Document doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Collection<Element> elements = XmlUtilities.getElements(root, tagName);
            for (Element e : elements) {
                result.add(e.getTextContent());
            }
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return result;
    }

    private static String xmlFromMap (String parent, Map<String, String> xmlMap) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, parent);
        for (String tag : xmlMap.keySet()) {
            Element current = XmlUtilities.makeElement(doc, tag, xmlMap.get(tag));
            root.appendChild(current);
        }
        doc.appendChild(root);
        try {
            xmlString = XmlUtilities.getXmlAsString(doc).replace("\r\n", "").replace("\n", "");
        }
        catch (TransformerException e) {
        }
        return xmlString;
    }
}
