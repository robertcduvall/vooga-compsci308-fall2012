package arcade.usermanager;

import java.io.IOException;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import arcade.utility.FileOperation;
import util.xml.XmlBuilder;
import util.xml.XmlWriter;


/**
 * Writes user data to an XML file.
 * 
 * @author Howard, Difan
 * 
 */
public class UserXMLWriter {
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private ResourceBundle resource;

    public UserXMLWriter () {
        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");
    }

    /**
     * initiate a new xml for user
     * 
     * @param userName
     * @param password
     * @param picture
     * @author difan
     * @throws IOException 
     */

    public void makeUserXML (String userName, String password, String picture) throws IOException {

        makeBasicXml(userName, password, picture);
        makeMessageXml(userName);
        makeGameXml(userName);
    }

    private void makeBasicXml (String userName, String password, String picture) throws IOException {
        String basicInfoFilePath = myUserBasicFilePath + userName + ".xml";
        
        Document basicDoc = XmlBuilder.createDocument();

        Element rootElement = basicDoc.createElement("user");
        basicDoc.appendChild(rootElement);

        XmlBuilder.appendElement(basicDoc, rootElement, "name", userName);
        XmlBuilder.appendElement(basicDoc, rootElement, "password", password);
        XmlBuilder.appendElement(basicDoc, rootElement, "picture", picture);
        XmlWriter.writeXML(basicDoc, basicInfoFilePath);

    }

    private void makeMessageXml (String userName) throws IOException {
        String messageFilePath = myUserMessageFilePath + userName + ".xml";
       
        Document doc = XmlBuilder.createDocument();

        Element rootElement = doc.createElement("message");
        doc.appendChild(rootElement);

        XmlWriter.writeXML(doc, messageFilePath);

    }

    private void makeGameXml (String userName) throws IOException {
        String gameFilePath = myUserGameFilePath + userName + ".xml";
        
        Document doc = XmlBuilder.createDocument();

        Element rootElement = doc.createElement("game");
        doc.appendChild(rootElement);

        XmlWriter.writeXML(doc, gameFilePath);

    }

}
