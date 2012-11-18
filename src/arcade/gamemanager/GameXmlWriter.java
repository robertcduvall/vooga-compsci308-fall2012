package arcade.gamemanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.xml.XmlBuilder;
import util.xml.XmlParser;

public class GameXmlWriter {
	
    private XmlParser myXmlParser;
    private XmlBuilder myXmlBuilder;
    private Node myGameNode;
	
	public void GameXmlWriter(String gameName) {
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
	 * @author Seon Kang
	 * @param tag
	 * @param value
	 * @param element
	 */
	protected void addProperty(String tag, String value, Element element) {
		myXmlBuilder.appendElement(element, tag, value);
	}
	
	protected void addProperty(String tag, String value) {
		myXmlBuilder.appendElement((Element) myGameNode, tag, value);
	}
	
	/**
	 * @author Seon Kang
	 * @param tag 
	 * @param value 
	 * @param element
	 */
	protected void setProperty(String tag, String value, Element element) {
		myXmlBuilder.modifyTag(element, tag, value);
	}
	
	protected void setProperty(String tag, String value) {
		myXmlBuilder.modifyTag((Element) myGameNode, tag, value);
	}
}
