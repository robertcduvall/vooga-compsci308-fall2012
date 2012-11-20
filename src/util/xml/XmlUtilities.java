package util.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A general-use xml writer that gives users tools to easily create and modify xml files.
 * This is the one that we're going to use.
 * 
 * @author Seon Kang
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
    
    public static Collection<Element> addElement(Document doc, Element parent, String tag, List<String> content) {
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
        ArrayList<Element> list = new ArrayList<Element>();
        NodeList nodeList = element.getElementsByTagName(oldTag);
		for (int i = 0; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
			node.setNodeValue(newTag);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
			    list.add((Element) node);
			} else {
			    System.err.println("WARNING: Node could not be converted to element!");
			}
		}
	return list;
    }
    
    public static Element setAttribute(Element element, String attributeName,
    		String newAttributeContent) {
        if (element.getAttribute(attributeName) == null) {
            System.err.println("WARNING: Tried to set an attribute that doesn't yet exist! Added it as a new attribute.");
        }
    	element.setAttribute(attributeName, newAttributeContent);
    	return element;
    }
}
