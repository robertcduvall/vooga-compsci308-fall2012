package vooga.platformer.leveleditor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


/**
 * A static class responsible for writing level data to an XML file that can
 * parsed by the level factory.
 * 
 * @author geo4
 * 
 */
public class LevelFileWriter {

    /**
     * Integer constant that is returned by writeLevel if the file data cannot
     * be written successfully.
     */
    public static final int UNSUCCESSFUL_WRITE = 0;
    /**
     * Integer constant returned by writeLevel if the data file is written
     * successfully.
     */
    public static final int SUCCESSFUL_WRITE = 1;

    // Simple (temporary) test method
    public static void main (String[] args) {
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        writeLevel("src/vooga/platformer/data/test.xml", sprites, 3, 3, "something.jpg");
    }

    /**
     * Method that writes the data describing a platformer level to an XML file
     * so that it can be reconstructed by a level factory.
     * 
     * @param filePath where the XML should be saved
     * @param levelObjects Sprites that populate the level
     * @param width overall width of the level in pixels
     * @param height overall height of the level in pixels
     * @param backgroundImage path to the image that should be painted to the
     *        level's background
     * @return an integer constant representing whether the file was written
     *         successfully or not
     */
    public static int writeLevel (String filePath, Collection<Sprite> levelObjects, int width,
                                  int height, String backgroundImage) {
        try {
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element level = doc.createElement("level");
            doc.appendChild(level);

            appendChildTextNode(doc, level, "width", String.valueOf(width));
            appendChildTextNode(doc, level, "height", String.valueOf(height));
            appendChildTextNode(doc, level, "backgroundImage", backgroundImage);

            addLevelObjects(levelObjects, doc, level);

            String xmlString = getXMLAsString(doc);

            FileWriter writer = new FileWriter(filePath);
            writer.write(xmlString);
            writer.close();

            // print xml to console
            System.out.println("What was written in " + filePath + "\n\n" + xmlString);
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
            return UNSUCCESSFUL_WRITE;
        }
        catch (TransformerException e) {
            e.printStackTrace();
            return UNSUCCESSFUL_WRITE;
        }
        catch (IOException e) {
            e.printStackTrace();
            return UNSUCCESSFUL_WRITE;
        }
        return SUCCESSFUL_WRITE;
    }

    private static void addLevelObjects (Collection<Sprite> levelObjects, Document doc,
                                         Element level) {
        for (Sprite s : levelObjects) {
            Element spriteElement = doc.createElement("gameObject");
            spriteElement.setAttribute("tag", s.getTag());

            appendChildTextNode(doc, spriteElement, "x", String.valueOf(s.getX()));
            appendChildTextNode(doc, spriteElement, "y", String.valueOf(s.getY()));
            appendChildTextNode(doc, spriteElement, "width", String.valueOf(s.getWidth()));
            appendChildTextNode(doc, spriteElement, "height", String.valueOf(s.getHeight()));
            appendChildTextNode(doc, spriteElement, "imagePath", String.valueOf(s.getImagePath()));

            level.appendChild(spriteElement);
            // TODO add Sprite updateStrategies
        }
    }

    private static String getXMLAsString (Document doc) throws TransformerException {
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        String xmlString = sw.toString();
        return xmlString;
    }

    private static void appendChildTextNode (Document doc, Element parentElement,
                                             String attributeName, String value) {
        Element element = doc.createElement(attributeName);
        Text text = doc.createTextNode(value);
        element.appendChild(text);
        parentElement.appendChild(element);
    }
}
