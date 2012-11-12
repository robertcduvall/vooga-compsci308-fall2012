package arcade.utility;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * helper function in creating elements in xml
 * @author difan
 *
 */

public class XMLWriter {
    
    
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

}
