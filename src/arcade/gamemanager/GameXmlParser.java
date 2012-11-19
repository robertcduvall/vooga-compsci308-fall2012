package arcade.gamemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.xml.XmlBuilder;
import util.xml.XmlParser;

/**
 * 
 * @author Kang
 *
 */
public class GameXmlParser {

    private XmlParser myXmlParser;
    private XmlBuilder myXmlBuilder;
    private Node myGameNode;
	
	public GameXmlParser(String gameName) {
		File f = new File(
				"../vooga-compsci308-fall2012/src/arcade/database/game.xml");
		myXmlBuilder = new XmlBuilder(f);
		myXmlParser = new XmlParser(f);
		setGameNode(gameName);
	}
	
	public void setGameNode(String gameName) {
		NodeList allGames = myXmlParser.getElementsByName(myXmlParser.getDocumentElement(), "game");
		for (int i = 0; i < allGames.getLength(); i++) {
			if (allGames.item(i).getTextContent().equals(gameName))
				myGameNode = allGames.item(i);
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
	
	public String getProperty(String tag, Element element) {
		NodeList attributeList = element.getChildNodes();
		for (int j = 0; j < attributeList.getLength(); j++) {
			return myXmlParser.getTextContent(
					(Element) attributeList.item(j), tag);
		}
		return null;
	}

	public String getProperty(String tag) {
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
	public List<String> getListByTagName(String tag, Element element) {
		List<String> stringListByTag = new ArrayList<String>();
		NodeList nodeList = myXmlParser.getElementsByName(element, tag);
		for (int i = 0; i < nodeList.getLength(); i++) {
			stringListByTag.add(nodeList.item(i).getTextContent());
		}
		return stringListByTag;
	}
	
	public List<String> getListByTagName(String tag) {
		return getListByTagName(tag, (Element) myGameNode);
	}
}
