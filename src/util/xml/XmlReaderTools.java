package util.xml;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A general-use xml reader that gives users tools to easily read xml files. 
 * 
 * @author Seon Kang
 *
 */
public class XmlReaderTools {
    private File myXmlFile;
    private Document myXmlDocument;
    private Element myDefaultElement;
    
    public XmlReaderTools(File file) {
    	myXmlFile = file;
    	makeDocument(myXmlFile);
    }

    /**
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
    
    protected Document getXmlDocument() {
    	return myXmlDocument;
    }
    
    protected File getXmlFile() {
    	return myXmlFile;
    }
    
    /**
     * This method lets you set your default root element.
     * 
     * @param element The element you want as your default
     */
    private void setDefault(Element element) {
    	myDefaultElement = element;
    }
    
    public Element getDefaultElement() {
    	return myDefaultElement;
    }
    
    
    public List<String> getContentListByTag(Element parent, String tag) {
		List<String> stringListByTag = new ArrayList<String>();
		NodeList nodeList = parent.getElementsByTagName(tag);
		for (int i = 0; i < nodeList.getLength(); i++) {
			stringListByTag.add(nodeList.item(i).getTextContent());
		}
		return stringListByTag;
    }
}
