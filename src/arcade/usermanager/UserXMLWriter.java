package arcade.usermanager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.xml.XmlWriter;
import java.io.File;
import java.io.StringWriter;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import arcade.utility.XMLWriter;


/**
 * Writes user data to an XML file.
 * 
 * @author Howard, Difan
 * 
 */
public class UserXMLWriter {
    private final static String myUserFilePath="src/arcade/database/";
    /**
     * initiate a new xml for user (we should specify where to store these user
     * xmls)
     * 
     * @param userName
     * @param password
     * @param picture
     * @author difan
     */


    public static void makeUserXML(String userName, String password, String picture) {
        try{ 
          DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
          DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
       
          // root elements
          Document doc = docBuilder.newDocument();
          Element rootElement = doc.createElement("user");
          doc.appendChild(rootElement);
          
          XmlWriter.appendElement(doc,rootElement,"name",userName);
          XmlWriter.appendElement(doc,rootElement,"password",password);
          XmlWriter.appendElement(doc,rootElement,"picture",picture);
          Element game=XmlWriter.appendElement(doc,rootElement,"game_history","");
          XmlWriter.appendElement(doc,game,"game","");
          Element message=XmlWriter.appendElement(doc,rootElement,"message_box","");
          XmlWriter.appendElementWithAttribute(doc,message,"message","", "wula", "wula");
          
          XmlWriter.writeXML(doc, myUserFilePath+userName+".xml");
          
         
          
          
          /*
          
       // write the content into xml file
          TransformerFactory transformerFactory = TransformerFactory.newInstance();
          Transformer transformer = transformerFactory.newTransformer();
          DOMSource source = new DOMSource(doc);
          StreamResult result = new StreamResult(new File( myUserFilePath));
>>>>>>> master

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("user");
            doc.appendChild(rootElement);

            XMLWriter.appendElement(doc, rootElement, "name", userName);
            XMLWriter.appendElement(doc, rootElement, "password", password);
            XMLWriter.appendElement(doc, rootElement, "picture", picture);
            Element game = XMLWriter.appendElement(doc, rootElement,
                    "game_history", "");
            XMLWriter.appendElement(doc, game, "game", "");
            Element message = XMLWriter.appendElement(doc, rootElement,
                    "message_box", "");
            XMLWriter.appendElementWithAttribute(doc, message, "message", "",
                    "wula", "wula");

            /*
             * 
             * // write the content into xml file
             * TransformerFactory transformerFactory =
             * TransformerFactory.newInstance();
             * Transformer transformer = transformerFactory.newTransformer();
             * DOMSource source = new DOMSource(doc);
             * StreamResult result = new StreamResult(new
             * File("C:\\Users\\difan\\workspace\\gediva-group8"));
             * 
             * // Output to console for testing
             * // StreamResult result = new StreamResult(System.out);
             * 
             * transformer.transform(source, result);
             * 
             * System.out.println("File saved!");
             * // System.out.println(serializeDoc(doc));
             */

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }

        // catch (TransformerException tfe) {
        // tfe.printStackTrace();
        // }

          
      }
    
    
      
//    public static String serializeDoc (Node doc) {
//        StringWriter outText = new StringWriter();
//        StreamResult sr = new StreamResult(outText);
//        Properties oprops = new Properties();
//        oprops.put(OutputKeys.METHOD, "html");
//        oprops.put("indent-amount", "4");
//        TransformerFactory tf = TransformerFactory.newInstance();
//        Transformer t = null;
//        try {
//            t = tf.newTransformer();
//            t.setOutputProperties(oprops);
//            t.transform(new DOMSource(doc), sr);
//        }
//        catch (Exception e) {
//        }
//        return outText.toString();
//    }
}
