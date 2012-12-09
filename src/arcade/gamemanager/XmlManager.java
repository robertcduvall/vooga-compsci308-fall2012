package arcade.gamemanager;

import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;


/**
 * Document manager that deals with xml files
 * 
 * @author Jei Min Yoo
 * 
 */
public class XmlManager implements DocumentManager {

    @Override
    public Collection<Element> getElements (Element parent) {
        return XmlUtilities.getElements(parent);
    }

    @Override
    public Collection<Element> getElements (Element parent, String tag) {
        return XmlUtilities.getElements(parent, tag);
    }

    @Override
    public void appendElement (Document doc, Element parent, String tag,
            String content) {
        XmlUtilities.appendElement(doc, parent, tag, content);
    }

    @Override
    public void save (Document doc, String filePath) {
        doc.normalizeDocument();
        XmlUtilities.write(doc, filePath);
    }

}
