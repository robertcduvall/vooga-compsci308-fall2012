package util.xml;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
      
    /**
     * This method lets you set your default root element.
     * 
     * @param element The element you want as your default
     */
    private void setDefault(Element element) {
    	//myDefaultElement = element;
    }
    
    private Element getDefaultElement() {
        //TODO: fix this
    	//return myDefaultElement;
        return null;
    }
    
    public void addElement(Element parent, String tag, String content) {
//    	Element child = myXmlDocument.createElement(tag);
//    	child.setTextContent(content);
//    	parent.appendChild(child);
    }
    
    public void addElement(Element parent, String tag, List<String> content) {
    	for (String s: content) {
    		addElement(parent, tag, s);
    	}
    }
    
    public void addElement(String tag, String content) {
    	addElement(getDefaultElement(), tag, content);
    }
    
    public void addAttribute(Element element, String attributeName, String attributeContent) {
    	element.setAttribute(attributeName, attributeContent);
    }
    
    public void addAttribute(Element element, String attributeName, 
    		List<String> attributeContent) {
    	for (String s: attributeContent) {
    		addAttribute(element, attributeName, s);
    	}
    }
    
    public void addElementAndAttribute(Element parent, String tag, String content,
   		String attributeName, String attributeContent) {
//    	Element child = myXmlDocument.createElement(tag);
//    	child.setTextContent(content);
//    	parent.appendChild(child);
//    	addAttribute(child, attributeName, attributeContent);
    }
    
    public void addElementAndAttribute(String tag, String content,
    		String attributeName, String attributeContent) {
//    	addElementAndAttribute(getDefaultElement(), tag, content, attributeName, 
//    			attributeContent);
    }
    
    public void modifyElementContent(Element element, String newContent) {
    	element.setTextContent(newContent);
    }
    
    public void modifyElementContent(String newContent) {
    	modifyElementContent(getDefaultElement(), newContent);
    }

    public void replaceAllTagNames(Element element, String oldTag, String newTag) {
    	NodeList nodeList = element.getElementsByTagName(oldTag);
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList.item(i).setNodeValue(newTag);
		}
    }
    
    public void replaceAllTagNames(String oldTag, String newTag) {
    	replaceAllTagNames(getDefaultElement(), oldTag, newTag);
    }
    
    public void modifyAttribute(Element element, String attributeName,
    		String newAttributeContent) {
    	element.setAttribute(attributeName, newAttributeContent);
    }
    
    public void modifyAttribute(String attributeName,
    		String newAttributeContent) {
    	modifyAttribute(getDefaultElement(), attributeName, newAttributeContent);
    }
}
