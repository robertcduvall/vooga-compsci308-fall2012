package vooga.platformer.util.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;

/**
 * @author Zach Michaelov
 */
public class XMLUtils {
    /**
     * Helper function for extracting the Image specified in a config file. It assumes
     * imageFileName is in the same directory as the config file.
     * @param configFileName the config file
     * @param imageFileName the filename specified in the config file e.g. brick.jpg
     * @return the Image imageFileName refers to
     */
    public static Image fileNameToImage(File configFileName, String imageFileName) {
        // creates a new File by appending the imageFileName to the path of config file
        File imageFile = new File(configFileName.getParentFile(), imageFileName);

        ImageIcon ii = new ImageIcon(imageFile.getAbsolutePath());
        return ii.getImage();
    }

    /**
     * convenience method for initializing a Document based on an XML file
     *
     * @param xmlFile the XML file we want to parse
     * @return Document representing the XML file or null if xmlFile is not a valid XML file
     */
    public static Document initializeDocument(File xmlFile) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * retrieves the value of the tag beneath element
     *
     * @param tag the name of the tag we want to extract
     * @param element the element from which we start our search
     * @return the value of the Node designated by tag
     */
    public static String getTagValue(String tag, Element element) {
        Node value = getNode(tag, element);
        return value.getNodeValue();
    }

    /**
     * helper for retrieving a node by its tag
     * @param tag name of Node we want to retrieve
     * @param element the element from which we start our search
     * @return the Node specified by tag
     */
    private static Node getNode(String tag, Element element) {
        NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
        return (Node) list.item(0);
    }

    /**
     * Searches for a single Node beneath element
     * @param tag the name of the Node we want to extract
     * @param element  the element from which we start our search
     * @return
     */
    public static Node getSingleNode(String tag, Element element) {
        return element.getElementsByTagName(tag).item(0);
    }
    /**
     * retrieves the value of the given node in a xml tree
     * e.g. <file>brick.jpg</file> yields "brick.jpg"
     *
     * @param node the Node we want to extract the value from
     * @return the value enclosed in the tag
     */
    public static String getTagValue(Node node) {
        return node.getFirstChild().getNodeValue();

    }

    /**
     * gets the integer value of Node specified by tag
     * @param tag
     * @param element
     * @return
     */
    public static int getTagInt(String tag, Element element) {
        return Integer.parseInt(getTagValue(tag, element));
    }

    /**
     * gets the double value of Node specified by tag
     * @param tag
     * @param element
     * @return
     */
    public static double getTagDouble(String tag, Element element) {
        return Double.parseDouble(getTagValue(tag, element));
    }
}
