package arcade.usermanager;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class XmlParser {
    /**
     * tempotary
     * 
     * @param element
     * @param tagName
     * @return
     */
    public static String getTextContent (Element element, String tagName) {
        NodeList list = element.getElementsByTagName(tagName);
        return list.item(0).getTextContent();
    }

}
