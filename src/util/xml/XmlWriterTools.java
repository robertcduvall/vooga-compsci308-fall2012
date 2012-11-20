package util.xml;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @deprecated Use XmlUtilities instead.
 * 
 * A general-use xml writer that gives users tools to easily create and modify xml files. 
 * 
 * @author Seon Kang
 *
 */
public class XmlWriterTools {
    private File myXmlFile;
    private Document myXmlDocument;
    private Element myDefaultElement;
    
    /**
     * @deprecated Use the static methods in XmlUtilities instead.
     * 
     * @param file
     */
    public XmlWriterTools(File file) {
    	myXmlFile = file;
    	makeDocument(myXmlFile);
    }

    /**
     * @deprecated Use the method in XmlUtilities instead.
     * 
     * Creates a document from file and sets the document element
     * as the default.
     * 
     * @param file
     */
    
    private void makeDocument(File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
        	myXmlDocument = dbFactory.newDocumentBuilder().parse(myXmlFile);
        	setDefault(myXmlDocument.getDocumentElement());
        }
        catch (Exception e) {
            System.err.println("An error occurred while trying to make document: "
                    + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    /**
     * @deprecated Use the static methods in XmlUtilities instead.
     */
    protected Document getXmlDocument() {
    	return myXmlDocument;
    }
    
    /**
     * @deprecated Use the static methods in XmlUtilities instead.
     */
    protected File getXmlFile() {
    	return myXmlFile;
    }
    
    /**
     * @deprecated Use the static methods in XmlUtilities instead.
     * 
     * This method lets you set your default root element.
     * 
     * @param element The element you want as your default
     */
    private void setDefault(Element element) {
    	myDefaultElement = element;
    }
    
    /**
     * @deprecated Use the static methods in XmlUtilities instead.
     */
    private Element getDefaultElement() {
    	return myDefaultElement;
    }
    
    /**
     * @deprecated Use the appendElement method in XmlUtilities instead.
     */
    public void addElement(Element parent, String tag, String content) {
    	Element child = myXmlDocument.createElement(tag);
    	child.setTextContent(content);
    	parent.appendChild(child);
    }
    
    /**
     * @deprecated Use the appendElement method in XmlUtilities instead.
     */
    public void addElement(Element parent, String tag, List<String> content) {
    	for (String s: content) {
    		addElement(parent, tag, s);
    	}
    }
    
    /**
     * @deprecated Use the appendElement method in XmlUtilities instead.
     */
    public void addElement(String tag, String content) {
    	addElement(getDefaultElement(), tag, content);
    }
    
    /**
     * @deprecated Use the method in XmlUtilities instead.
     */
    public void addAttribute(Element element, String attributeName, String attributeContent) {
    	element.setAttribute(attributeName, attributeContent);
    }
    
    /**
     * @deprecated Use the method in XmlUtilities instead.
     */
    public void addAttribute(Element element, String attributeName, 
    		List<String> attributeContent) {
    	for (String s: attributeContent) {
    		addAttribute(element, attributeName, s);
    	}
    }
    
    /**
     * @deprecated Use the method in XmlUtilities instead.
     */
    public void addElementAndAttribute(Element parent, String tag, String content,
    		String attributeName, String attributeContent) {
    	Element child = myXmlDocument.createElement(tag);
    	child.setTextContent(content);
    	parent.appendChild(child);
    	addAttribute(child, attributeName, attributeContent);
    }
    
    /**
     * @deprecated Use the method in XmlUtilities instead.
     */
    public void addElementAndAttribute(String tag, String content,
    		String attributeName, String attributeContent) {
    	addElementAndAttribute(getDefaultElement(), tag, content, attributeName, 
    			attributeContent);
    }
    
    /**
     * @deprecated Use the setElementContent method in XmlUtilities instead.
     */
    public void modifyElementContent(Element element, String newContent) {
    	element.setTextContent(newContent);
    }
    
    /**
     * @deprecated Use the setElementContent method in XmlUtilities instead.
     */
    public void modifyElementContent(String newContent) {
    	modifyElementContent(getDefaultElement(), newContent);
    }

    /**
     * @deprecated Use the method in XmlUtilities instead.
     */
    public void replaceAllTagNames(Element element, String oldTag, String newTag) {
    	NodeList nodeList = element.getElementsByTagName(oldTag);
		for (int i = 0; i < nodeList.getLength(); i++) {
			nodeList.item(i).setNodeValue(newTag);
		}
    }
    
    /**
     * @deprecated Use the method in XmlUtilities instead.
     */
    public void replaceAllTagNames(String oldTag, String newTag) {
    	replaceAllTagNames(getDefaultElement(), oldTag, newTag);
    }
    
    /**
     * @deprecated Use the setAttribute method in XmlUtilities instead.
     */
    public void modifyAttribute(Element element, String attributeName,
    		String newAttributeContent) {
    	element.setAttribute(attributeName, newAttributeContent);
    }
    
    /**
     * @deprecated Use the setAttribute method in XmlUtilities instead.
     */
    public void modifyAttribute(String attributeName,
    		String newAttributeContent) {
    	modifyAttribute(getDefaultElement(), attributeName, newAttributeContent);
    }
}
