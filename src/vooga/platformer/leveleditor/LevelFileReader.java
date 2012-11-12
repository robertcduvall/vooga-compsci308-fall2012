package vooga.platformer.leveleditor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vooga.platformer.util.xml.XMLUtils;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Instances of this class are created using the path to an XML level data file.
 * Once the instance is created, the getter methods can be used to get the
 * contents of this data file as java types (ints and Sprites, rather than
 * string values).
 * 
 * @author Grant Oakley
 * @author Zach Michaelov (modified)
 */
public class LevelFileReader {

    private final Document document;
    private Element root;
    private File levelFile;

    public LevelFileReader (String levelFilePath) {
        levelFile = new File(levelFilePath);
        document = XMLUtils.initializeDocument(levelFile);
        root = document.getElementById("level");
    }

    public String getLevelID () {
        return XMLUtils.getTagValue("id", root);
    }

    public int getWidth () {
        return XMLUtils.getTagInt("width", root);
    }

    public int getHeight () {
        return XMLUtils.getTagInt("height", root);
    }

    public Image getBackgroundImage () {
        return XMLUtils.fileNameToImage(levelFile, XMLUtils.getTagValue("backgroundImage", root));
    }

    public Collection<Sprite> getSprites () {
        NodeList spritesNode = document.getElementsByTagName("gameobject");
        Collection<Sprite> spritesList = new ArrayList<Sprite>(spritesNode.getLength());

        for (int i = 0; i < spritesNode.getLength(); i++) {
            Node spriteNode = spritesNode.item(i);
            if (spriteNode.getNodeType() == Node.ELEMENT_NODE) {
                Element spriteElement = (Element) spriteNode;
                String tag = spriteElement.getAttribute("type");
                int x = XMLUtils.getTagInt("x", spriteElement);
                int y = XMLUtils.getTagInt("y", spriteElement);
                int width = XMLUtils.getTagInt("width", spriteElement);
                int height = XMLUtils.getTagInt("height", spriteElement);
                String imagePath = XMLUtils.getTagValue("imagePath", spriteElement);
                spritesList.add(new Sprite(tag, x, y, width, height, imagePath));
            }
        }

        return spritesList;
    }

}
