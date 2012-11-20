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
 * A general-use xml writer that gives users tools to easily create and modify xml files.
 * This is the one that we're going to use.
 * 
 * @author Seon Kang, Alex Browne, Grant Oakley, Zach Michaelov, Difan Zhao, Mark Hoffman
 *
 */
public class XmlUtilities {

    /**
     * Creates a document from file and sets the document element
     * as the default. Might need to check if return statement is null
     * if you use this.
     * 
     * @param file the file to load xml data into the document from
     */
    
    public static Document makeDocument(File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
        	doc = dbFactory.newDocumentBuilder().parse(file);
        }
        catch (Exception e) {
            System.err.println("An error occurred while trying to make document: "
                    + e.getMessage());
            e.printStackTrace();
        }
        return doc;
    }
    
    public static Document makeDocument(String filepath) {
           return makeDocument(new File(filepath));
    }
    
    public static Document makeDocument() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
                doc = dbFactory.newDocumentBuilder().newDocument();
        }
        catch (Exception e) {
            System.err.println("An error occurred while trying to make document: "
                    + e.getMessage());
            e.printStackTrace();
        }
        return doc;
    }
      
    public static Element addElement(Document doc, Element parent, String tag, String content) {
    	Element child = doc.createElement(tag);
    	child.setTextContent(content);
    	parent.appendChild(child);
    	return child;
    }
    
    public static Collection<Element> appendElement(Document doc, Element parent, String tag, List<String> content) {
    	ArrayList<Element> list = new ArrayList<Element>();
        for (String s: content) {
    		list.add(addElement(doc, parent, tag, s));
    	}
    	return list;
    }
    
    public static Element addAttribute(Element element, String attributeName, String attributeContent) {
    	element.setAttribute(attributeName, attributeContent);
    	return element;
    }
    
    public static Collection<Element> addAttribute(Element element, String attributeName, 
    		List<String> attributeContent) {
        ArrayList<Element> list = new ArrayList<Element>();
    	for (String s: attributeContent) {
    		list.add(addAttribute(element, attributeName, s));
    	}
    	return list;
    }
    
    public static Element addElementAndAttribute(Document doc, Element parent, String tag, String content,
   		String attributeName, String attributeContent) {
    	Element child = doc.createElement(tag);
    	child.setTextContent(content);
    	parent.appendChild(child);
    	addAttribute(child, attributeName, attributeContent);
    	return child;
    }
    
    public static Element setElementContent(Element element, String newContent) {
    	element.setTextContent(newContent);
    	return element;
    }
    
    public static Collection<Element> replaceAllTagNames(Element element, String oldTag, String newTag) {
        NodeList nodeList = element.getElementsByTagName(oldTag);
		for (int i = 0; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
			node.setNodeValue(newTag);
		}
	return convertNodeListToCollection(nodeList);
    }
    
    public static Element setAttribute(Element element, String attributeName,
    		String newAttributeContent) {
        if (element.getAttribute(attributeName) == null) {
            System.err.println("WARNING: Tried to set an attribute that doesn't yet exist! Added it as a new attribute.");
        }
    	element.setAttribute(attributeName, newAttributeContent);
    	return element;
    }
    
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
     * Helper function for creating an image from an image file.
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
     * Helper function for creating an image from an image file.
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
     * helper for retrieving a node by its tag
     * 
     * @param tag name of Node we want to retrieve
     * @param element the element from which we start our search
     * @return a NodeList which contains all the elements specified by tag
     */
    public static Collection<Element> getElements (String tag, Element element) {
        NodeList list = element.getElementsByTagName(tag);
        return convertNodeListToCollection(list);
    }
    
    /**
     * Searches for a single Node beneath element
     * 
     * @param tag the name of the Node we want to extract
     * @param parent the parent element from which we start our search
     * @return
     */
    public static Element getElement (String tag, Element parent) {
        return (Element) parent.getElementsByTagName(tag).item(0);
    }
    
    /**
     * retrieves the content of the given element in a xml tree
     * e.g. <file>brick.jpg</file> yields "brick.jpg"
     * 
     * @param node the Node we want to extract the value from
     * @return the value enclosed in the tag
     */
    public static String getContent (Element element) {
        return element.getTextContent();
    }
    
    /**
     * gets the integer value of Node specified by tag
     * 
     * @param tag
     * @param element
     * @return
     */
    public static int getContentAsInt (String tag, Element element) {
        return Integer.parseInt(getContent(element));
    }

    /**
     * gets the double value of Node specified by tag
     * 
     * @param tag
     * @param element
     * @return
     */
    public static double getContentAsDouble (String tag, Element element) {
        return Double.parseDouble(getContent(element));
    }
    
    /**
     * gets the double value of Node specified by tag
     * 
     * @param tag
     * @param element
     * @return
     */
    public static Image getContentAsImage (String tag, Element element) {
        return fileNameToImage(getContent(element));
    }
    
    /**
     * retrieves the value of the child beneath a parent element
     * 
     * @param tag the name of the tag of a child element that
     *          we want the value of.
     * @param parent the element from which we start our search
     * @return the value of the Node designated by tag
     */
    public static String getChildContent (String tag, Element parent) {
        Node child = getElement(tag, parent);
        return child.getTextContent();
    }
    
    /**
     * gets the integer value of Node specified by tag
     * 
     * @param tag
     * @param element
     * @return
     */
    public static int getChildContentAsInt (String tag, Element element) {
        return Integer.parseInt(getChildContent(tag, element));
    }

    /**
     * gets the double value of Node specified by tag
     * 
     * @param tag
     * @param element
     * @return
     */
    public static double getChildContentAsDouble (String tag, Element element) {
        return Double.parseDouble(getChildContent(tag, element));
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
                map.put(paramElement.getTagName(), getContent(paramElement));
            }
        }
        return map;
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
        Element mapElement = doc.createElement(elementName);
        for (String key : map.keySet()) {
            addElement(doc, mapElement, key, map.get(key));
        }
        return mapElement;
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
        Element childElement = generateElementFromMap(doc, childElementName, map);
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
    
    /**
     * write document into a xml file
     * 
     * @param doc the xml document to write
     * @param filePath(including filename.xml)
     */

    public static void writeXML (Document doc, String filePath) {
        
        FileWriter writer = null;
        String xmlString = null;
        try {
            xmlString = getXMLAsString(doc);
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            writer = new FileWriter(filePath);
            writer.write(xmlString);
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("ERROR: could not open file!" + e.getMessage());
            e.printStackTrace();
        } 
    }
    
    
    public static Collection<Element> convertNodeListToCollection(NodeList nodeList) {
        ArrayList<Element> list = new ArrayList<Element>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    list.add((Element) node);
                } else {
                    System.err.println("WARNING: Node could not be converted to element!");
                }
        }
        return list;
    }
}
