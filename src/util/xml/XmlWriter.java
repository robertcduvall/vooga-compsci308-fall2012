package util.xml;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * helper function in creating elements in xml
 * @author difan
 *
 */

public class XmlWriter {
    
    
    /**
     * create an element in xml
     * @param document
     * @param root element
     * @param name of the element to be created
     * @param content of the element to be created
     * @return element created 
     * @author difan zhao
     */
    public static Element appendElement(Document doc, Element root, String childElementName, String content){
        Element childElement = doc.createElement(childElementName);
        childElement.appendChild(doc.createTextNode(content));
        root.appendChild(childElement);
        return childElement;
        
    }
    
    
    /**
     * create an element with attribute in xml
     * @param document
     * @param root element
     * @param name of the element to be created
     * @param content of the element to be created
     * @param attribute name
     * @param attribute content
     * @return element created 
     * @author difan zhao
     */
    
    public static Element appendElementWithAttribute(Document doc, Element root, String childElementName, String content, String attrName, String attrContent){
        Element childElement = doc.createElement(childElementName);
        childElement.appendChild(doc.createTextNode(content));
        root.appendChild(childElement);
        Attr attr = doc.createAttribute(attrName);
        attr.setValue(attrContent);
        childElement.setAttributeNode(attr);
        return childElement;
        
    }
    
    /**
     * write document into a xml file
     * @param doc 
     * @param filePath(including filename.xml)
     * @author difan zhao
     */
    
    public static void writeXML(Document doc, String filePath) {
        
        try{
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));

        // Output to console for testing
        // StreamResult result = new StreamResult(System.out);

        transformer.transform(source, result);

        
        System.out.println("File saved!");
    }
    
    catch (TransformerException tfe) {
        tfe.printStackTrace();
  }
    }

}
