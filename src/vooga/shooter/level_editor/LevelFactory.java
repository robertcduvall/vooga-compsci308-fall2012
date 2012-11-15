package vooga.shooter.level_editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.reflection.*;
import vooga.shooter.gameObjects.Sprite;


/**
 * Level Factory.java
 * Instantiates Levels from an XML file
 * 
 * @author Alex Browne
 */
public abstract class LevelFactory {


    /**
     * Creates Level object from XML file
     * 
     * @param f XML file input
     * @return
     */
    public Level createLevel (File f) {
        Level level = new Level();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Document doc = null;
        try {
            doc = dBuilder.parse(f);
        }
        catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();
        Element root = doc.getDocumentElement();

        if (!root.getTagName().equals("Level")) {
            System.out.println("ERROR: the xml document isn't formatted correctly. "
                               + "It most have <Level> as the root element.");
            return null;
        }

        NodeList nodeList = root.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String elementName = element.getTagName();

                // you can comment out or remove this print statment...
                System.out.println("Detected Sprite of type: " + elementName);

                if (!element.hasAttributes()) {
                    // if the element has no attributes, we assume the class has
                    // no arguments
                    // in the constructor.

                    // we need to add the package name to create a fully
                    // qualified class name
                    String packageName = this.getClass().getPackage().getName();
                    Sprite s = (Sprite) Reflection.createInstance(packageName + "." + elementName);

                    // add the sprite to the level we're constructing
                    level.addSprite(s);
                }
                else {
                    // if the element has attributes, we put them in a format
                    // that the Reflection
                    // utility can handle, and then instantiate the class with
                    // the attributes being
                    // used as arguments in the constructor.

                    NamedNodeMap attributes = element.getAttributes();

                    ArrayList<String> argList = new ArrayList<String>();

                    for (int j = 0; j < attributes.getLength(); j++) {
                        Node attr = attributes.item(j);
                        String value = attr.getNodeValue();
                        // you can comment out or remove this print statment...
                        System.out.println("Detected argument: " + value);
                        argList.add(value);
                    }

                    String packageName = this.getClass().getPackage().getName();

                    Sprite s = null;

                    try {
                        s =
                                (Sprite) Reflection.createInstance(packageName + "." + elementName,
                                                                   argList.toArray());
                    }
                    catch (Exception e) {
                        // try converting the args to integers, as this is a
                        // common case...
                        ArrayList<Integer> argsAsInts = new ArrayList<Integer>();
                        for (String arg : argList) {
                            argsAsInts.add(Integer.valueOf(arg));
                        }
                        s =
                                (Sprite) Reflection.createInstance(packageName + "." + elementName,
                                                                   argsAsInts.toArray());
                    }

                    // add the sprite to the level we're constructing
                    level.addSprite(s);

                    // TODO: evaluate attribute names to make sure they are the
                    // same
                    // arguments that the constructor expects and are in the
                    // right order

                    // TODO: make argument evaluation more robust for complex
                    // arguments
                    // currently we only support Strings and Integers.

                }

            }
        }

        return level;
    }

    /**
     * 
     * @param path the path to the xml file relative to the
     *        project root directory (e.g. levels/level1.xml).
     * 
     * @return the Level that was created by the factory
     */
    public Level createLevel (String path) {
        String current_dir = System.getProperty("user.dir");
        return createLevel(new File(current_dir + "/src/" + path));
    }

}
