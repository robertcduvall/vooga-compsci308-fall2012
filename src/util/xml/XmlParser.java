package util.xml;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Parses Xml to get data from the file.
 * 
 * @author Mark Hoffman
 * 
 */
public class XmlParser {

    private File myXmlFile;
    private Document myXmlDocument;

    /**
     * Initiates the process for parsing an Xml file.
     * 
     * @param file The Xml file that will be parsed.
     */
    public XmlParser (File file) {
        myXmlFile = file;
        makeDocument();
    }

    /**
     * Enables parsing of the Xml file. myXmlDocument is then the basis
     * for the rest of the parsing.
     */
    private void makeDocument () {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            myXmlDocument = dbFactory.newDocumentBuilder().parse(myXmlFile);
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        myXmlDocument.normalize();
    }

    /**
     * 
     * @return Document (Root) Element
     */
    public Element getDocumentElement () {
        return myXmlDocument.getDocumentElement();
    }

    /**
     * 
     * @param element The tag that contains a section of XML to look through
     * @param name The string name of the desired elements
     * @return A node list containing the desired elements and their
     *         associated section of XML
     */
    public NodeList getElementsByName (Element element, String name) {
        NodeList list = element.getElementsByTagName(name);
        return list;
    }

    /**
     * 
     * @param element The tag that contains a section of XML to look through
     * @param tagName The string name of the element containing desired text
     * @return The text contained in these desired tag
     */
    public String getTextContent (Element element, String tagName) {
        NodeList list = element.getElementsByTagName(tagName);
        return list.item(0).getTextContent();
    }

    /**
     * 
     * @param element The tag that contains a section of XML to look through
     * @param tagName The string name of the element containing desired image
     * @return The image in the desired tag
     */
    public Image getImageContent (Element element, String tagName) {
        String pathName = getTextContent(element, tagName);
        Image image = null;
        try {
            image = ImageIO.read(new File(pathName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 
     * @param element The tag that contains a section of XML to look through
     * @param tagName The string name of the element containing desired int
     * @return The int value of the desired tag
     */
    public int getIntContent (Element element, String tagName) {
        NodeList list = element.getElementsByTagName(tagName);
        return Integer.parseInt(list.item(0).getTextContent());
    }

    /**
     * 
     * @param element The tag that contains a section of XML to look through
     * @param tagName The string name of the element containing desired double
     * @return the double value of the desired tag
     */
    public double getDoubleContent (Element element, String tagName) {
        NodeList list = element.getElementsByTagName(tagName);
        return Double.parseDouble(list.item(0).getTextContent());
    }

    /**
     * 
     * @return document
     * @author difan
     */
    public Document getDocument () {
        return myXmlDocument;
    }

}
