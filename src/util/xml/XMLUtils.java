package util.xml;

import java.awt.Image;
import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * @author Zach Michaelov
 * @author Grant Oakley
 */
public class XMLUtils {
    /**
     * Helper function for extracting the Image specified in a config file. It
     * assumes
     * imageFileName is in the same directory as the config file.
     * 
     * @param configFileName the config file
     * @param imageFileName the filename specified in the config file e.g.
     *        brick.jpg
     * @return the Image imageFileName refers to
     */
    public static Image fileNameToImage (File configFileName, String imageFileName) {
        // creates a new File by appending the imageFileName to the path of
        // config file
        File imageFile = new File(configFileName.getParentFile(), imageFileName);

        ImageIcon ii = new ImageIcon(imageFile.getAbsolutePath());
        return ii.getImage();
    }

    /**
     * convenience method for initializing a Document based on an XML file
     * 
     * @param xmlFile the XML file we want to parse
     * @return Document representing the XML file or null if xmlFile is not a
     *         valid XML file
     */
    public static Document initializeDocument (File xmlFile) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            return doc;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * retrieves the value of the tag beneath element
     * 
     * @param tag the name of the tag we want to extract
     * @param element the element from which we start our search
     * @return the value of the Node designated by tag
     */
    public static String getTagValue (String tag, Element element) {
        Node value = getNode(tag, element);
        return value.getNodeValue();
    }

    /**
     * helper for retrieving a node by its tag
     * 
     * @param tag name of Node we want to retrieve
     * @param element the element from which we start our search
     * @return the Node specified by tag
     */
    private static Node getNode (String tag, Element element) {
        NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
        return list.item(0);
    }

    /**
     * Searches for a single Node beneath element
     * 
     * @param tag the name of the Node we want to extract
     * @param element the element from which we start our search
     * @return
     */
    public static Node getSingleNode (String tag, Element element) {
        return element.getElementsByTagName(tag).item(0);
    }

    /**
     * retrieves the value of the given node in a xml tree
     * e.g. <file>brick.jpg</file> yields "brick.jpg"
     * 
     * @param node the Node we want to extract the value from
     * @return the value enclosed in the tag
     */
    public static String getTagValue (Node node) {
        return node.getFirstChild().getNodeValue();

    }

    /**
     * gets the integer value of Node specified by tag
     * 
     * @param tag
     * @param element
     * @return
     */
    public static int getTagInt (String tag, Element element) {
        return Integer.parseInt(getTagValue(tag, element));
    }

    /**
     * gets the double value of Node specified by tag
     * 
     * @param tag
     * @param element
     * @return
     */
    public static double getTagDouble (String tag, Element element) {
        return Double.parseDouble(getTagValue(tag, element));
    }

    /**
     * Iterates over the child elements of a specified parent element. The tag
     * values are stored as Strings and are the keys of the map. All text in
     * between these tags are stored as String, which the tag String maps to.
     * 
     * @param parentElement element in which to start the map extraction
     * @return a map of String keys which are the tags, which map to String
     *         values that are their contents
     */
    public static Map<String, String> extractMapFromXML (Element parentElement) {
        NodeList paramNodeList = parentElement.getChildNodes();
        Map<String, String> map = new HashMap<String, String>();

        for (int k = 0; k < paramNodeList.getLength(); k++) {
            Node paramNode = paramNodeList.item(k);
            if (paramNode.getNodeType() == Node.ELEMENT_NODE) {
                Element paramElement = (Element) paramNode;
                map.put(paramElement.getTagName(), XMLUtils.getTagValue(paramElement));
            }
        }
        return map;
    }

    /**
     * Adds a simple element containing a single string value and no attributes
     * to a parent Element.
     * 
     * @param doc Document in which the Element is being generated
     * @param parentElement Element in which the Element should be nested
     * @param attributeName tag for the new Element
     * @param value String value to store between the tags
     */
    public static void appendSimpleElement (Document doc, Element parentElement,
                                                 String attributeName, String value) {
        Element element = doc.createElement(attributeName);
        Text text = doc.createTextNode(value);
        element.appendChild(text);
        parentElement.appendChild(element);
    }

    /**
     * Creates a new XML Element using the contents of a Map<String, String>.
     * 
     * @param doc Document in which the Element is being created
     * @param elementName value of the new Element's tag
     * @param map map of Strings onto Strings representing the tags and values
     *        to be written as child Elements of the new Element
     * @return a new Element built using the Map parameter
     */
    public static Element generateElementFromMap (Document doc, String elementName,
                                                  Map<String, String> map) {
        Element childElement = doc.createElement(elementName);
        for (String param : map.keySet()) {
            XMLUtils.appendSimpleElement(doc, childElement, param, map.get(param));
        }
        return childElement;
    }

    /**
     * Adds the contents of a Map<String, String> as a child element to an
     * existing XML Element. This child element is map up of tags, which are the
     * keys of the Map, and values, which are the values of the Map.
     * 
     * @param doc Document in which the Element is being created
     * @param parentElement XML Element in which to place the contents of the
     *        map as tags and values
     * @param childElementName name of child element, under which the map
     *        entries will appear
     * @param map map of Strings onto Strings representing the tags and values
     *        to be written as child Elements of the new Element
     */
    public static void appendMapContents (Document doc, Element parentElement,
                                          String childElementName, Map<String, String> map) {
        Element childElement = XMLUtils.generateElementFromMap(doc, childElementName, map);
        parentElement.appendChild(childElement);
    }

    /**
     * Returns a String created from an XML Document. Useful for debugging in
     * the console.
     * 
     * @param doc Document that has been generated.
     * @return XML Document as a String
     * @throws TransformerException thrown if Document cannot be converted to a
     *         String
     */
    public static String getXMLAsString (Document doc) throws TransformerException {
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        String xmlString = sw.toString();
        return xmlString;
    }
}
