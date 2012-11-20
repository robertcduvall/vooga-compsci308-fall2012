package util.xml;

import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
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


/**
 * A general-use xml utility class.
 * 
 * IMPORTANT: There are other xml utilities but this is the one that everyone
 * should use. The 5-6 of us got together to make one Utility class that has 
 * the best from all the others and is more robust. All other xml utilities
 * have been deprecated.
 * 
 * TODO: Include more robust error checking and throw an XmlException when
 * appropriate.
 * 
 * If you have any suggestions/changes email stephenalexbrowne@gmail.com.
 * 
 * 行行行行行行行行行行行行行行行行行行行行行行行行行行行行行行行
 * 
 * Example of the semantics used here:
 * 
 * <tag attribute="value"> content </tag>
 * ~or~
 * <tag attributeName="attributeValue"> content </tag>
 * 
 * <parent>
 * <child></child>
 * </parent>
 * 
 * 行行行行行行行行行行行行行行行行行行行行行行行行行行行行行行行
 * 
 * @author Seon Kang, Alex Browne, Grant Oakley, Zach Michaelov,
 *      Difan Zhao, Mark Hoffman
 */

public class XmlUtilities {
    
    /**
     * Creates a new (empty) Document.
     * 
     * @return a new (empty) Document
     */

    public static Document makeDocument () {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            doc = dbFactory.newDocumentBuilder().newDocument();
        }
        catch (Exception e) {
            System.err
                    .println("ERROR: Could not instantiate a Document element! "
                            + e.getMessage());
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * Creates a new Document and loads it with data from an existing
     * xml file.
     * 
     * @param file the file that will load xml data into the document.
     * @return a new Document with data loaded from the file.
     */

    public static Document makeDocument (File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            doc = dbFactory.newDocumentBuilder().parse(file);
        }
        catch (IOException e) {
            System.err.println("ERROR: Could not open the file! "
                    + e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e) {
            System.err
                    .println("ERROR: Could not instantiate a Document element! "
                            + e.getMessage());
            e.printStackTrace();
        }

        return doc;
    }

    /**
     * Creates a new Document and loads it with data from an existing
     * xml file.
     * 
     * @param filepath the full pathname to a file that will load xml
     *        data into the document.
     * @return a new Document with data loaded from the file.
     */

    public static Document makeDocument (String filepath) {
        return makeDocument(new File(filepath));
    }

    /**
     * Create a new (empty) element.
     * 
     * @param doc the Document which will create the element.
     * @param tag the tag of the newly created element.
     * @return the newly created (empty) element.
     */

    public static Element makeElement (Document doc, String tag) {
        return doc.createElement(tag);
    }

    /**
     * Returns a newly created element with content.
     * 
     * @param doc the Document which will create the element.
     * @param tag the tag of the newly created element.
     * @param content the content of the newly created element.
     * @return the newly created element.
     */

    public static Element makeElement (Document doc, String tag, String content) {
        Element e = doc.createElement(tag);
        return setContent(e, content);
    }

    /**
     * Returns a newly created element with an attribute.
     * 
     * @param doc the Document which will create the element.
     * @param tag the tag of the newly created element.
     * @param attributeName the attribute name of the attribute of the new
     *        element.
     * @param attributeValue the value of the attribute of the new element.
     * @return the newly created element.
     */

    public static Element makeElement (Document doc, String tag,
            String attributeName, String attributeValue) {
        Element e = doc.createElement(tag);
        return addAttribute(e, attributeName, attributeValue);
    }

    /**
     * Returns a newly created element with content and a single attribute.
     * 
     * @param doc the Document which will create the element.
     * @param tag the tag of the newly created element.
     * @param content the content of the newly created element.
     * @param attributeName the attribute name of the attribute of the new
     *        element.
     * @param attributeValue the value of the attribute of the new element.
     * @return the newly created element.
     */

    public static Element makeElement (Document doc, String tag,
            String content, String attributeName, String attributeValue) {
        Element e = doc.createElement(tag);
        setContent(e, content);
        return addAttribute(e, attributeName, attributeValue);
    }

    /**
     * Returns a newly created element with more than one attribute.
     * 
     * @param doc the Document which will create the element.
     * @param tag the tag of the newly created element.
     * @param attributeMap a Map of attributeName to attributeValue.
     * @return the newly created element.
     */

    public static Element makeElement (Document doc, String tag,
            Map<String, String> attributesMap) {
        Element e = doc.createElement(tag);
        return addAttributes(e, attributesMap);
    }

    /**
     * Returns a newly created element with content and more than one attribute.
     * 
     * @param doc the Document which will create the element.
     * @param tag the tag of the newly created element.
     * @param content the content of the newly created element.
     * @param attributeMap a Map of attributeName to attributeValue.
     * @return the newly created element.
     */

    public static Element makeElement (Document doc, String tag,
            String content, Map<String, String> attributesMap) {
        Element e = doc.createElement(tag);
        setContent(e, content);
        return addAttributes(e, attributesMap);
    }

    /**
     * Appends an empty element to a parent element.
     * 
     * @param parent the parent element to which the new element
     *        will be appended.
     * @param child the child element which will be appended to parent.
     * @return the child element that was appended.
     */

    public static Element appendElement (Element parent, Element child) {
        parent.appendChild(child);
        return child;
    }

    /**
     * Appends an empty element to a parent element.
     * 
     * @param doc a Document object that will create the Element
     * @param parent the parent element to which the new element
     *        will be appended.
     * @param tag the tag name of the new element.
     * @return the new element that was appended.
     */

    public static Element appendElement (Document doc, Element parent,
            String tag) {
        Element child = doc.createElement(tag);
        return appendElement(parent, child);
    }

    /**
     * Appends an element with some content to a parent element.
     * 
     * @param doc a Document object that will create the Element
     * @param parent the parent element to which the new element
     *        will be appended.
     * @param tag the tag name of the new element.
     * @param content the content of the new element.
     * @return the new element that was appended.
     */

    public static Element appendElement (Document doc, Element parent,
            String tag, String content) {
        Element child = makeElement(doc, tag, content);
        return appendElement(parent, child);
    }

    /**
     * Appends an element with a single attribute to
     * the parent element.
     * 
     * @param doc a Document which will be used to create the
     *        element.
     * @param parent the element to which the new element will be appended.
     * @param tag the tag (name) of the new element.
     * @param attributeName the name of the attribute of the new element.
     * @param attributeValue the value of the attribute of the new element.
     * @return the new element that was appended to parent.
     */

    public static Element appendElement (Document doc, Element parent,
            String tag, String attributeName, String attributeValue) {
        Element child = makeElement(doc, tag, attributeName, attributeValue);
        return appendElement(parent, child);
    }

    /**
     * Appends an element with content and a single attribute to
     * the parent element.
     * 
     * @param doc a Document which will be used to create the
     *        element.
     * @param parent the element to which the new element will be appended.
     * @param tag the tag (name) of the new element.
     * @param content the content of the new element.
     * @param attributeName the name of the attribute of the new element.
     * @param attributeValue the value of the attribute of the new element.
     * @return the new element that was appended to parent.
     */

    public static Element appendElement (Document doc, Element parent,
            String tag, String content, String attributeName,
            String attributeValue) {
        Element child = makeElement(doc, tag, content, attributeName,
                attributeValue);
        return appendElement(parent, child);
    }

    /**
     * Appends an element with more than one attribute to
     * the parent element.
     * 
     * @param doc a Document which will be used to create the
     *        element.
     * @param parent the element to which the new element will be appended.
     * @param tag the tag (name) of the new element.
     * @param attributeMap a Map of attributeName to attributeValue
     * @return the new element that was appended to parent.
     */

    public static Element appendElement (Document doc, Element parent,
            String tag, Map<String, String> attributeMap) {
        Element child = makeElement(doc, tag, attributeMap);
        return appendElement(parent, child);
    }

    /**
     * Appends an element with content and more than one attribute to
     * the parent element.
     * 
     * @param doc a Document which will be used to create the
     *        element.
     * @param parent the element to which the new element will be appended.
     * @param tag the tag (name) of the new element.
     * @param content the content of the new element.
     * @param attributeMap a Map of attributeName to attributeValue
     * @return the new element that was appended to parent.
     */

    public static Element appendElement (Document doc, Element parent,
            String tag, String content, Map<String, String> attributeMap) {
        Element child = makeElement(doc, tag, content, attributeMap);
        return appendElement(parent, child);
    }

    /**
     * Appends a set of elements with the same tag name and
     * different content to a parent element. That is, each
     * element can have different content.
     * 
     * @param doc a Document object that will create the Element
     * @param parent the parent element to which the new element
     *        will be appended.
     * @param tag the tag name of the new element.
     * @param content a list of Strings which will be the content
     *        of the new elements. The number of elements created
     *        corresponds to the size of this list.
     * @return the new element that was appended.
     */

    public static Collection<Element> appendElements (Document doc,
            Element parent, String tag, List<String> content) {
        ArrayList<Element> list = new ArrayList<Element>();
        for (String s : content) {
            list.add(appendElement(doc, parent, tag, s));
        }
        return list;
    }

    /**
     * Sets the content of an element. If there was already
     * content, it will be overwritten.
     * 
     * @param element the element which will contain the new content.
     * @param newContent the content to add to the element.
     * @return the element to which new content was added.
     */

    public static Element setContent (Element element, String newContent) {
        element.setTextContent(newContent);
        return element;
    }

    /**
     * Adds an attribute to the element. If the attribute already exists,
     * throws a warning and then overwrites it.
     * 
     * @param element the element to which an attribute will be added.
     * @param attributeName the name of the attribute to be set.
     * @param attributeValue the value of the attribute to be set.
     * @return the element to which the new attribute was added.
     */

    public static Element addAttribute (Element element, String attributeName,
            String attributeValue) {
        //TODO warning statement being called inappropriately
        System.out.println(element.getAttribute(attributeName));
        if (element.getAttribute(attributeName) != null) {
            System.err.println("WARNING: The attribute '" + attributeName + "' for "
                    + "this element already exists. It will be overwritten!");
        }
        element.setAttribute(attributeName, attributeValue);
        return element;
    }

    /**
     * Adds a set of attributes to the element. If an attribute
     * already exists, throws a warning and then overwrites it.
     * The attributes are passed in as a Map.
     * 
     * @param element the element to which an attribute will be added.
     * @param attributeMap a Map of attributeName to attributeValue.
     * @return the element to which the new attributes were added.
     */

    public static Element addAttributes (Element element,
            Map<String, String> attributeMap) {
        for (String name : attributeMap.keySet()) {
            String value = attributeMap.get(name);
            addAttribute(element, name, value);
        }
        return element;
    }

    /**
     * Replaces all occurrences of a given tag name that fall within
     * the parent element.
     * 
     * @param parent the parent element in which we will start our search.
     * @param oldTag the old tag name to be replaced.
     * @param newTag the new tag name which will take the place of the old.
     * @return a collection of the elements whose names were changed.
     */

    public static Collection<Element> replaceAllTagNames (Element parent,
            String oldTag, String newTag) {
        NodeList nodeList = parent.getElementsByTagName(oldTag);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            node.setNodeValue(newTag);
        }
        return convertNodeListToCollection(nodeList);
    }

    /**
     * Sets an existing attribute of an element. If the attribute did not
     * previously exist, throws a warning and creates it.
     * 
     * @param element the element which contains an attribute to be set.
     * @param attributeName the name of the attribute to be set.
     * @param newAttributeContent the content of the attribute to be set.
     * @return the element which contained the set attribute.
     */

    public static Element setAttribute (Element element, String attributeName,
            String newAttributeContent) {
        if (element.getAttribute(attributeName) == null) {
            System.err.println("WARNING: Tried to set an attribute " +
            		"that doesn't yet exist! Added it as a new attribute.");
        }
        element.setAttribute(attributeName, newAttributeContent);
        return element;
    }

    /**
     * Helper function for extracting the Image specified in a config file. It
     * assumes imageFileName is in the same directory as the config file.
     * 
     * @param configFileName the config file
     * @param imageFileName the filename specified in the config file e.g.
     *        brick.jpg
     * @return the Image imageFileName refers to
     */

    public static Image fileNameToImage (File configFileName,
            String imageFileName) {

        File imageFile = new File(configFileName.getParentFile(), imageFileName);

        ImageIcon ii = new ImageIcon(imageFile.getAbsolutePath());
        return ii.getImage();
    }

    /**
     * Helper function for creating an image from an image filename.
     * 
     * @param dir the directory where the image is stored
     * @param imageFileName the filename specified in the config file e.g.
     *        brick.jpg
     * @return the Image imageFileName refers to
     */

    public static Image fileNameToImage (String dir, String imageFileName) {
        File imageFile = new File(dir, imageFileName);

        ImageIcon ii = new ImageIcon(imageFile.getAbsolutePath());
        return ii.getImage();
    }

    /**
     * Helper function for creating an image from an image filename.
     * 
     * @param imageFileName the full path to an image file.
     * @return the Image imageFileName refers to
     */

    public static Image fileNameToImage (String imageFileName) {
        File imageFile = new File(imageFileName);

        ImageIcon ii = new ImageIcon(imageFile.getAbsolutePath());
        return ii.getImage();
    }

    /**
     * helper for retrieving a list of elements by tag
     * 
     * @param tag name of the elements we want to retrieve
     * @param parent the parent element from which we start our search
     * @return a NodeList which contains all the elements specified by tag
     */

    public static Collection<Element> getElements (Element parent, String tag) {
        NodeList list = parent.getElementsByTagName(tag);
        return convertNodeListToCollection(list);
    }

    /**
     * Searches by tag for a single element of parent.
     * 
     * @param tag the name of the Node we want to extract.
     * @param parent the parent element from which we start our search.
     * @return the first element with the given tag.
     */

    public static Element getElement (Element parent, String tag) {
        return (Element) parent.getElementsByTagName(tag).item(0);
    }

    /**
     * Gets the content of the given element in a xml tree
     * e.g. <file>brick.jpg</file> yields "brick.jpg"
     * 
     * @param element the Element we want to extract the value from
     * @return the value enclosed in the tag
     */

    public static String getContent (Element element) {
        return element.getTextContent();
    }

    /**
     * Gets the content of an element and casts it to int.
     * 
     * @param element an element that has content which
     *        can be casted to int.
     * @return the integer value of the element.
     */

    public static int getContentAsInt (Element element) {
        return Integer.parseInt(getContent(element));
    }

    /**
     * Gets the content of an element and casts it to double.
     * 
     * @param element an element that has content which
     *        can be casted to double.
     * @return the double value of the element.
     */
    public static double getContentAsDouble (Element element) {
        return Double.parseDouble(getContent(element));
    }

    /**
     * Gets the content of an element, treats it as the full path to
     * an image file, and instantiates an Image from that file.
     * 
     * @param element an element that has content which
     *        is a valid full path to an image file.
     * @return the Image created from the file.
     */
    public static Image getContentAsImage (Element element) {
        return fileNameToImage(getContent(element));
    }

    /**
     * Gets the content of the child beneath a parent element.
     * 
     * @param tag the name of the tag of a child element that
     *        we want the content of.
     * @param parent the element from which we start our search
     * @return the content of the first Element named tag
     */
    public static String getChildContent (Element parent, String tag) {
        Node child = getElement(parent, tag);
        return child.getTextContent();
    }

    /**
     * Gets the content of the child beneath a parent element
     * and casts it to an int.
     * 
     * @param tag the name of the tag of a child element that
     *        we want the content of.
     * @param parent the element from which we start our search
     * @return the int content of the first Element named tag
     */
    public static int getChildContentAsInt (Element parent, String tag) {
        return Integer.parseInt(getChildContent(parent, tag));
    }

    /**
     * Gets the content of the child beneath a parent element
     * and casts it to a double.
     * 
     * @param tag the name of the tag of a child element that
     *        we want the content of.
     * @param parent the element from which we start our search
     * @return the double content of the first Element named tag
     */
    public static double getChildContentAsDouble (Element parent, String tag) {
        return Double.parseDouble(getChildContent(parent, tag));
    }

    /**
     * Gets the content of a child element beneath parent, treats
     * it as the full path to an image file, and instantiates an
     * Image from that file.
     * 
     * @param tag the name of the tag of a child element that
     *        we want the content of.
     * @param parent the element from which we start our search
     * @return the Image created from the file.
     */
    public static Image getChildContentAsImage (Element parent, String tag) {
        return fileNameToImage(getChildContent(parent, tag));
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
    public static Map<String, String> extractMapFromXml (Element parent) {
        NodeList paramNodeList = parent.getChildNodes();
        Map<String, String> map = new HashMap<String, String>();

        for (int k = 0; k < paramNodeList.getLength(); k++) {
            Node paramNode = paramNodeList.item(k);
            if (paramNode.getNodeType() == Node.ELEMENT_NODE) {
                Element paramElement = (Element) paramNode;
                map.put(paramElement.getTagName(), getContent(paramElement));
            }
        }
        return map;
    }

    /**
     * Creates a new Xml Element using the contents of a Map<String, String>.
     * 
     * @param doc Document in which the Element is being created
     * @param elementName value of the new Element's tag
     * @param map map of Strings onto Strings representing the tags and values
     *        to be written as child Elements of the new Element
     * @return a new Element built using the Map parameter
     */
    public static Element generateElementFromMap (Document doc,
            String elementName, Map<String, String> map) {
        Element mapElement = doc.createElement(elementName);
        for (String key : map.keySet()) {
            appendElement(doc, mapElement, key, map.get(key));
        }
        return mapElement;
    }

    /**
     * Adds the contents of a Map<String, String> as a child element to an
     * existing Xml Element. This child element is map up of tags, which are the
     * keys of the Map, and values, which are the values of the Map.
     * 
     * @param doc Document in which the Element is being created
     * @param parentElement Xml Element in which to place the contents of the
     *        map as tags and values
     * @param childElementName name of child element, under which the map
     *        entries will appear
     * @param map map of Strings onto Strings representing the tags and values
     *        to be written as child Elements of the new Element
     */
    public static void appendMapContents (Document doc, Element parent,
            String childElementName, Map<String, String> map) {
        Element childElement = generateElementFromMap(doc, childElementName,
                map);
        parent.appendChild(childElement);
    }

    /**
     * Returns a String created from an Xml Document. Useful for debugging in
     * the console.
     * 
     * @param doc Document that has been generated.
     * @return Xml Document as a String
     * @throws TransformerException thrown if Document cannot be converted to a
     *         String
     */
    public static String getXmlAsString (Document doc)
            throws TransformerException {
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
    
    /**
     * Returns a String created from an Xml Element. Useful for debugging in
     * the console.
     * 
     * @param element Element that will be printed.
     * @return Xml Element as a String
     * @throws TransformerException thrown if Document cannot be converted to a
     *         String
     */
    public static String getXmlAsString (Element element)
            throws TransformerException {
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        // Make a document and add the element to it
        // (This is necessary for setting it as a DOMSource).
        Document doc = makeDocument();
        doc.appendChild(element);
        
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        String xmlString = sw.toString();
        return xmlString;
    }

    /**
     * Writes a document to an xml file
     * 
     * @param doc the xml document to write
     * @param filePath the full path to the file, including filename.xml
     */

    public static void write (Document doc, String filePath) {

        FileWriter writer = null;
        String xmlString = null;
        try {
            xmlString = getXmlAsString(doc);
        }
        catch (TransformerException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            writer = new FileWriter(filePath);
            writer.write(xmlString);
            writer.close();
        }
        catch (IOException e) {
            System.err.println("ERROR: could not open file! " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Writes an element to an xml file
     * 
     * @param element the xml element to write
     * @param filePath the full path to the file, including filename.xml
     */

    public static void write (Element element, String filePath) {
        
        FileWriter writer = null;
        String xmlString = null;
        try {
            xmlString = getXmlAsString(element);
        }
        catch (TransformerException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            writer = new FileWriter(filePath);
            writer.write(xmlString);
            writer.close();
        }
        catch (IOException e) {
            System.err.println("ERROR: could not open file! " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Converts a NodeList to a Collection of Elements.
     * 
     * @param nodeList the NodeList to convert.
     * @return a collection of elements.
     */
    
    public static Collection<Element> convertNodeListToCollection (
            NodeList nodeList) {
        ArrayList<Element> list = new ArrayList<Element>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                list.add((Element) node);
            }
            else {
                System.err
                        .println("WARNING: Node could not be converted to element!");
            }
        }
        return list;
    }
}
