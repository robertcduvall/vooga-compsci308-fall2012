package util.networking.datatable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import util.networking.chat.ChatCommand;
import util.networking.chat.protocol.GordonBukspanProtocol;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getStringKey (String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getObjValue (String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<String> getColumnNames (String input) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Collection<UnmodifiableRowElement> getDataRows (String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createAddColumns (String[] strArray) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String createEdit (String strKey, Object value, Map<String, Object> mapEntry) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "Edit");
        Element key = XmlUtilities.makeElement(doc, "key", strKey);
        Element curValue = XmlUtilities.makeElement(doc, "value", (String) value);
        Element current = XmlUtilities.makeElement(doc, "toEdit");
        for (String s : mapEntry.keySet()) {
            key = XmlUtilities.makeElement(doc, "key", s);
            curValue = XmlUtilities.makeElement(doc, "value", (String) mapEntry.get(s));
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

