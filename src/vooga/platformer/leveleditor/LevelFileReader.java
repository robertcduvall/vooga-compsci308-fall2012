package vooga.platformer.leveleditor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import vooga.platformer.util.xml.XMLUtils;

import java.awt.*;
import java.io.File;
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

    public LevelFileReader(String levelFilePath) {
        levelFile = new File(levelFilePath);
        document = XMLUtils.initializeDocument(levelFile);
        root = document.getElementById("level");
    }

    public String getLevelID() {
        return XMLUtils.getTagValue("id", root);
    }

    public int getWidth() {
        return XMLUtils.getTagInt("width", root);
    }

    public int getHeight() {
        return XMLUtils.getTagInt("height", root);
    }

    public Image getBackgroundImage() {
        return XMLUtils.fileNameToImage(levelFile, XMLUtils.getTagValue("backgroundImage", root));
    }

    public Collection<Sprite> getSprites() {
        // TODO
        return null;
    }

}
