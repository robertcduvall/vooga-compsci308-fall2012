package vooga.platformer.levelfileio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;
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
import vooga.platformer.leveleditor.Sprite;


/**
 * A static class responsible for writing level data to an XML file that can
 * parsed by the level factory.
 * 
 * @author Grant Oakley
 * 
 */
public final class LevelFileWriter {

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

    private LevelFileWriter () {
        /*
         * Empty constructor
         */
    }

    /**
     * Method that writes the data describing a platformer level to an XML file
     * so that it can be reconstructed by a level factory.
     * 
     * @param filePath where the XML should be saved
     * @param levelType specifies which Level subclass to use
     * @param levelName name of the level
     * @param width overall width of the level in pixels
     * @param height overall height of the level in pixels
     * @param backgroundImage path to the image that should be painted to the
     *        level's background
     * @param levelObjects Sprites that populate the level
     * @param collisionCheckerType class name of the CollisionChecker to use for
     *        this level
     * @param cameraType class name of the Camera to use for this level
     * @return an integer constant representing whether the file was written
     *         successfully or not
     */
    public static int writeLevel (String filePath, String levelType, String levelName, int width,
                                  int height, String backgroundImage,
                                  Collection<Sprite> levelObjects, String collisionCheckerType,
                                  String cameraType) {
        try {
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element level = doc.createElement("level");
            doc.appendChild(level);

            level.setAttribute("type", levelType);
            appendChildTextNode(doc, level, "id", levelName);
            appendChildTextNode(doc, level, "width", String.valueOf(width));
            appendChildTextNode(doc, level, "height", String.valueOf(height));
            appendChildTextNode(doc, level, "backgroundImage", backgroundImage);
            appendChildTextNode(doc, level, "collisionChecker", collisionCheckerType);
            appendChildTextNode(doc, level, "camera", cameraType);

            addLevelObjects(levelObjects, doc, level);

            String xmlString = getXMLAsString(doc);
            FileWriter writer = new FileWriter(filePath);
            writer.write(xmlString);
            writer.close();
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
            spriteElement.setAttribute("type", s.getType());

            appendChildTextNode(doc, spriteElement, "x", String.valueOf(s.getX()));
            appendChildTextNode(doc, spriteElement, "y", String.valueOf(s.getY()));
            appendChildTextNode(doc, spriteElement, "width", String.valueOf(s.getWidth()));
            appendChildTextNode(doc, spriteElement, "height", String.valueOf(s.getHeight()));
            appendChildTextNode(doc, spriteElement, "imagePath", String.valueOf(s.getImagePath()));

            if (s.getUpdateStrategies() != null && s.getUpdateStrategies().size() > 0) {
                Element strategiesElement = doc.createElement("strategies");
                for (Map<String, String> strategy : s.getUpdateStrategies()) {

                    String strategyType = "";
                    if (strategy.containsKey("type")) {
                        strategyType = strategy.get("type");
                        strategy.remove("type");
                    }

                    Element strategyElement = makeElementFromMap(doc, "strategy", strategy);
                    strategyElement.setAttribute("type", strategyType);
                    strategiesElement.appendChild(strategyElement);
                }
                spriteElement.appendChild(strategiesElement);
            }

            if (s.getAttributes() != null && s.getAttributes().size() > 0) {
                appendMapContents(doc, spriteElement, "attr", s.getAttributes());
            }

            level.appendChild(spriteElement);
        }
    }

    private static void appendMapContents (Document doc, Element parentElement,
                                           String childElementName, Map<String, String> map) {
        Element childElement = makeElementFromMap(doc, childElementName, map);
        parentElement.appendChild(childElement);
    }

    private static Element makeElementFromMap (Document doc, String childElementName,
                                               Map<String, String> map) {
        Element childElement = doc.createElement(childElementName);
        for (String param : map.keySet()) {
            appendChildTextNode(doc, childElement, param, map.get(param));
        }
        return childElement;
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
