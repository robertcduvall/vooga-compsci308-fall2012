package vooga.turnbased.gamecreation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.XmlUtilities;

/**
 * 
 * @author Mark Hoffman
 *
 */
public abstract class Editor {

    private Document myXmlDocument;
    private String myFileName;
    private Element myRootElement;

    /**
     * Instantiates a LevelEditor for modifying an Xml document.
     *
     * @param xmlDocument Already formed Xml document for modifying
     * @param fileName File name (with path) of Xml document
     */
    public Editor(Document xmlDocument, String fileName) {
        myXmlDocument = xmlDocument;
        myFileName = fileName;
    }

    /**
     * Creates a LevelEditor for a new Xml document.
     * 
     * @param fileName File name (with path) of Xml document
     */
    public Editor(String fileName) {
        myXmlDocument = XmlUtilities.makeDocument();
        myRootElement = myXmlDocument.createElement("level");
        myXmlDocument.appendChild(myRootElement);
        myFileName = fileName;
    }

    /**
     * Saves the xml document.
     */
    public abstract void saveXmlDocument();

    /**
     * 
     * @return Xml Document
     */
    public Document getXmlDocument () {
        return myXmlDocument;
    }
}
