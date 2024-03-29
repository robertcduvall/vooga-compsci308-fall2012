package util.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * @deprecated Please use XmlUtilities instead.
 * 
 * A set of utility methods for creating, editing, and
 * appending to an xml Document object.
 * 
 * There is much work to do here. We need to consolidate
 * all the xml code and this is only part of it.
 * 
 * This code effects all of us. Please email me with any
 * changes or proposals: stephenalexbrowne@gmail.com
 * 
 * @author Alex Browne
 */

public class XmlBuilder {

    private File myXmlFile;
    private Document myXmlDocument;

    /**
     * @deprecated Use static methods in XmlUtilities instead.
     * 
     * Added constructors for the createDocument() methods
     * 
     * @author Seon Kang
     *
     */
	public XmlBuilder(File f) {
		myXmlDocument = createDocument(f);
	}
	
	public XmlBuilder(String filepath) {
		myXmlDocument = createDocument(filepath);
	}
	
	public XmlBuilder() {
		myXmlDocument = createDocument();
	}
	
    /**
     * @deprecated Use the makeDocument method in XmlUtilities instead.
     * 
     * Creates a Document Object and handles any exceptions
     * that might be thrown. The Document object is brand new
     * and empty.
     * 
     * @return a new (empty) Document Object
     */
    public static Document createDocument () {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            return doc;
        }
        catch (Exception e) {
            System.err.println("Could not instantiate a Document Object: "
                    + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @deprecated Use the makeDocument method in XmlUtilities instead.
     * 
     * Creates a Document Object and loads in the data from
     * an existing xml file. Also handles any exceptions
     * that might be thrown.
     * 
     * @param file an xml file.
     * @return a Document Object with data loaded from file.
     */
    public static Document createDocument (File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        }
        catch (Exception e) {
            System.err
                    .println("Could not instantiate a DocumentBuilder Object: "
                            + e.getMessage());
            e.printStackTrace();
            return null;
        }

        try {
            Document doc = dBuilder.parse(file);
            return doc;
        }
        catch (SAXException e) {
            System.err.println("Could not parse the xml file! "
                    + e.getMessage());
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            System.err.println("There was a problem loading the xml File!"
                    + "Does the file exist? Is it valid? " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @deprecated Use the makeDocument method in XmlUtilities instead.
     * 
     * Creates a Document Object and loads in the data from
     * an existing xml file. Also handles any exceptions
     * that might be thrown.
     * 
     * @param filepath the full path to an xml file.
     * @return a Document Object with data loaded from file.
     */
    public static Document createDocument (String filepath) {

        File file = new File(filepath);
        return createDocument(file);

    }

    /**
     * @deprecated Use the static methods in XmlUtilities instead.
     * 
     * @author Seon Kang
     * @return
     */
    public Document getDocument() {
    	return myXmlDocument;
    }

    /**
     * @deprecated Use the method in XmlUtilities instead.
     * 
     * Used Difan's appendElement() method but made it default to using this
     * XmlBuilder's document.
     * 
     * @author Seon Kang
     * @param root
     * @param child
     * @param value
     */
    public void appendElement(Element root, String child, String content) {
    	appendElement(getDocument(), root, child, content);
    }
    
    /**
     * @deprecated Use the method in XmlUtilities instead.
     * 
     * create an element in xml
     * 
     * @param document
     * @param root element
     * @param name of the element to be created
     * @param content of the element to be created
     * @return element created
     * @author difan zhao
     */
    public static Element appendElement (Document doc, Element root,
            String childElementName, String content) {
        Element childElement = doc.createElement(childElementName);
        childElement.appendChild(doc.createTextNode(content));
        root.appendChild(childElement);
        return childElement;

    }


    /**
     * @deprecated Use the method in XmlUtilities instead.
     * 
     * Used Difan's appendElementWithAttribute() method but made it default to using this
     * XmlBuilder's document.
     * 
     * @author Seon Kang
     * @param root
     * @param child
     * @param content
     * @param attrName
     * @param attrContent
     */
    public void appendElementWithAttribute(Element root, String child, String content,
    		String attrName, String attrContent) {
    	appendElementWithAttribute(getDocument(), root, child, content, 
    			attrName, attrContent);
    }
    
    
    /**
     * @deprecated Use the method in XmlUtilities instead.
     * 
     * create an element with attribute in xml
     * 
     * @param document
     * @param root element
     * @param name of the element to be created
     * @param content of the element to be created
     * @param attribute name
     * @param attribute content
     * @return element created
     * @author difan zhao
     */

    public static Element appendElementWithAttribute (Document doc,
            Element root, String childElementName, String content,
            String attrName, String attrContent) {
        Element childElement = doc.createElement(childElementName);
        childElement.appendChild(doc.createTextNode(content));
        root.appendChild(childElement);
        Attr attr = doc.createAttribute(attrName);
        attr.setValue(attrContent);
        childElement.setAttributeNode(attr);
        return childElement;

    }

    /**
     * @deprecated Use the replaceAllTagNames method in XmlUtilities instead.
     * 
     * @param el :root element
     * @param tagName :element whoes content should be modified
     * @param content
     */

    public static void modifyTag (Element el, String tagName, String content) {

        NodeList children = el.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Element child = (Element) children.item(i);
            if (child.getNodeName().equals(tagName)) {
                child.setNodeValue(content);
            }

        }
    }

}
