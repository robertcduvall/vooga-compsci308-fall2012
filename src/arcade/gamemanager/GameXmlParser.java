package arcade.gamemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.text.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.xml.XmlUtilities;


/**
 * 
 * @author Kang, Royal
 * 
 */
public class GameXmlParser {

    private org.w3c.dom.Document myXmlParser;
    private Element myGameNode;

    public GameXmlParser (Element gameName) {
        File f = new File(
                "../vooga-compsci308-fall2012/src/arcade/database/game.xml");
        XmlUtilities.makeDocument(f);
        myXmlParser = XmlUtilities.makeDocument(f);
        setGameNode(gameName);
    }

    public void setGameNode (Element gameName) {
        Collection<Element> allGames = XmlUtilities.getElements(gameName,
                "game");
        Element[] arrayGames = (Element[]) allGames.toArray();
        for (int i = 0; i < arrayGames.length; i++) {
            if (arrayGames[i].getTextContent().equals(gameName))
                myGameNode = arrayGames[i];
        }
    }

    /**
     * A more general way to get game properties.
     * 
     * TODO fix this
     * 
     * @author Seon Kang
     * @param gameName
     * @param tag
     * @return
     */

    public String getProperty (String tag, Element element) {
        NodeList attributeList = element.getChildNodes();
        for (int j = 0; j < attributeList.getLength(); j++) {
            return myXmlParser.getTextContent((Element) attributeList.item(j),
                    tag);
        }
        return null;
    }

    public String getProperty (String tag) {
        return getProperty(tag, (Element) myGameNode);
    }

    /**
     * General function for getting String lists by passing a string of the tag.
     * 
     * 
     * @author Seon Kang
     * 
     * @param tag
     * 
     * @return A String ArrayList of elements matching tag
     */
    public List<String> getListByTagName (String tag, Element element) {
        List<String> stringListByTag = new ArrayList<String>();
        NodeList nodeList = myXmlParser.getElementsByTagNameNS(element.toString(), tag);
        for (int i = 0; i < nodeList.getLength(); i++) {
            stringListByTag.add(nodeList.item(i).getTextContent());
        }
        return stringListByTag;
    }

    public List<String> getListByTagName (String tag) {
        return getListByTagName(tag, (Element) myGameNode);
    }
}
