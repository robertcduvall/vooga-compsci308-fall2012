package arcade.gamemanager;

import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * interface that determines how a document should be handled in game model and
 * saved to appropriate files
 * 
 * @author Jei Min Yoo
 * 
 */
public interface DocumentManager {

    /**
     * returns child elements from specified parent element
     * 
     * @param parent parent element to get child elements from
     * @return child elements
     */
    Collection<Element> getElements (Element parent);

    /**
     * returns child elements with the tag from specified parent element
     * 
     * @param parent parent element to get child elements from
     * @param tag tag used to choose child elements
     * @return child elements with the tag
     */
    Collection<Element> getElements (Element parent, String tag);

    /**
     * appends an element to the document object under parent element
     * 
     * @param doc document of interest
     * @param parent element under which the new element will be inserted
     * @param tag tag of the new element
     * @param content content of the new element
     */
    void appendElement (Document doc, Element parent, String tag, String content);

    /**
     * saves document in an appropriate file format.
     * 
     * @param doc document of interest
     * @param filePath location of the save file
     */
    void save (Document doc, String filePath);
}
