package arcade.usermanager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.xml.XmlWriter;
import java.io.File;
import java.io.StringWriter;
import java.util.Properties;
import java.util.ResourceBundle;
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
    private   String myUserBasicFilePath ;
    private  String myUserMessageFilePath ;
    private   String myUserGameFilePath; 
    private  ResourceBundle resource;
    
    public UserXMLWriter(){
        resource = ResourceBundle.getBundle("resources.filePath");
        myUserBasicFilePath=resource.getString("BasicFilePath");
        myUserMessageFilePath=resource.getString("MessageFilePath");
        myUserGameFilePath=resource.getString("GameFilePath");
    }
    /**
     * initiate a new xml for user 
     * 
     * @param userName
     * @param password
     * @param picture
     * @author difan
     */


    public  void makeUserXML(String userName, String password, String picture) {
          
         makeBasicXml(userName,picture,picture);
         makeMessageXml(userName);
         makeGameXml(userName);
}

    private  void makeBasicXml(String userName, String password, String picture){
        String basicInfoFilePath=myUserBasicFilePath+userName+".xml";
        Document basicDoc=XmlWriter.makeNewDocument(basicInfoFilePath);
        
        Element rootElement = basicDoc.createElement("user");
        basicDoc.appendChild(rootElement);
        
        XmlWriter.appendElement(basicDoc,rootElement,"name",userName);
        XmlWriter.appendElement(basicDoc,rootElement,"password",password);
        XmlWriter.appendElement(basicDoc,rootElement,"picture",picture);
        XmlWriter.writeXML(basicDoc, basicInfoFilePath);
        
    }
    
    private  void makeMessageXml(String userName){
        String messageFilePath=myUserMessageFilePath+userName+".xml";
        Document doc=XmlWriter.makeNewDocument(messageFilePath);
        
        Element rootElement = doc.createElement("message");
        doc.appendChild(rootElement);
        
       
        XmlWriter.writeXML(doc, messageFilePath);
        
    }
    
    private  void makeGameXml(String userName){
        String gameFilePath=myUserGameFilePath+userName+".xml";
        Document doc=XmlWriter.makeNewDocument(gameFilePath);
        
        Element rootElement = doc.createElement("game");
        doc.appendChild(rootElement);
        
       
        XmlWriter.writeXML(doc, gameFilePath);
        
    }

    
    
      
 
}
