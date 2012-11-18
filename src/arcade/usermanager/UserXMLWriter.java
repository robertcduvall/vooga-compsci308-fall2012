package arcade.usermanager;

import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
     */

    public void makeUserXML (String userName, String password, String picture) {

        makeBasicXml(userName, picture, picture);
        makeMessageXml(userName);
        makeGameXml(userName);
    }

    private void makeBasicXml (String userName, String password, String picture) {
        String basicInfoFilePath = myUserBasicFilePath + userName + ".xml";
        Document basicDoc = XmlBuilder.createDocument(basicInfoFilePath);

        Element rootElement = basicDoc.createElement("user");
        basicDoc.appendChild(rootElement);

        XmlBuilder.appendElement(basicDoc, rootElement, "name", userName);
        XmlBuilder.appendElement(basicDoc, rootElement, "password", password);
        XmlBuilder.appendElement(basicDoc, rootElement, "picture", picture);
        XmlWriter.writeXML(basicDoc, basicInfoFilePath);

    }

    private void makeMessageXml (String userName) {
        String messageFilePath = myUserMessageFilePath + userName + ".xml";
        Document doc = XmlBuilder.createDocument(messageFilePath);

        Element rootElement = doc.createElement("message");
        doc.appendChild(rootElement);

        XmlWriter.writeXML(doc, messageFilePath);

    }

    private void makeGameXml (String userName) {
        String gameFilePath = myUserGameFilePath + userName + ".xml";
        Document doc = XmlBuilder.createDocument(gameFilePath);

        Element rootElement = doc.createElement("game");
        doc.appendChild(rootElement);

        XmlWriter.writeXML(doc, gameFilePath);

    }

}
