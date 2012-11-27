package arcade.usermanager;

import java.io.IOException;
import java.util.ResourceBundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import arcade.utility.FileOperation;
import util.xml.XmlBuilder;
import util.xml.XmlUtilities;
import util.xml.XmlWriter;


/**
 * Writes user data to an XML file.
 * 
 * @author Howard, Difan
 * TODO: message XML should contain sender, not receiver tag
 * TODO: change to use new XMLUtilities
 * 
 */
public class UserXMLWriter {
    private String myUserBasicFilePath;
    private String myUserMessageFilePath;
    private String myUserGameFilePath;
    private ResourceBundle resource;
    private String myUserImageFilePath;

    public UserXMLWriter () {
        resource = ResourceBundle.getBundle("arcade.usermanager.filePath");
        myUserBasicFilePath = resource.getString("BasicFilePath");
        myUserMessageFilePath = resource.getString("MessageFilePath");
        myUserGameFilePath = resource.getString("GameFilePath");
        myUserImageFilePath=resource.getString("ImageFilePath");
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
        
        Document doc = XmlUtilities.makeDocument();

        Element rootElement = doc.createElement("user");
        doc.appendChild(rootElement);

        XmlUtilities.appendElement(doc, rootElement, "name", userName);
        XmlUtilities.appendElement(doc, rootElement, "password", password);
        XmlUtilities.appendElement(doc, rootElement, "picture", myUserImageFilePath+picture);
        XmlUtilities.appendElement(doc, rootElement, "credits", "0");
        XmlUtilities.write(doc, basicInfoFilePath);

    }

    private void makeMessageXml (String userName) throws IOException {
        String messageFilePath = myUserMessageFilePath + userName + ".xml";
       
        Document doc = XmlUtilities.makeDocument();

        Element rootElement = doc.createElement("messagelist");
        doc.appendChild(rootElement);

        XmlUtilities.write(doc, messageFilePath);

    }

    private void makeGameXml (String userName) throws IOException {
        String gameFilePath = myUserGameFilePath + userName + ".xml";
        
        Document doc = XmlUtilities.makeDocument();

        Element rootElement = doc.createElement("gamelist");
        doc.appendChild(rootElement);

        XmlUtilities.write(doc, gameFilePath);

    }

}
