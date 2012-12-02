package util.networking.chat.protocol;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.networking.chat.ChatCommand;
import util.networking.chat.ChatProtocol;
import util.xml.XmlUtilities;


/**
 * One implementation of ChatProtocol compatible with ChatServer.
 * 
 * @author Oren Bukspan
 * @author Connor Gordon
 */
public class GordonBukspanProtocol implements ChatProtocol {

    private static final int PORT = 5678;
    
    @Override
    public ChatCommand getType (String input) {
        String commandName;
        try {
            Document doc = GordonBukspanProtocol.loadXMLFrom(input);
            Element el = doc.getDocumentElement();
            commandName = el.getNodeName();
            ChatCommand c = ChatCommand.getChatCommandFromString(commandName);
            if (c != null)
                return c;
        }
        catch (SAXException e) {
            return ChatCommand.UNKNOWN;
        }
        catch (IOException e) {
        }
        return ChatCommand.UNKNOWN;
    }

    @Override
    public String getUser (String input) {
        return getValue(input, "user");
    }

    @Override
    public String getPassword (String input) {
       return getValue(input, "password");
    }

    @Override
    public String getTo (String input) {
       return getValue(input, "to");
    }

    @Override
    public String getFrom (String input) {
       return getValue(input, "from");
    }

    @Override
    public String createLoggedIn (boolean b) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("status", new Boolean(b).toString());
        return xmlFromMap("loggedIn", xmlTagMap);
    }

    @Override
    public String createAddUser (String user) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("user", user);
        return xmlFromMap("addUser", xmlTagMap);
    }

    @Override
    public String createRemoveUser (String user) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("user", user);
        return xmlFromMap("removeUser", xmlTagMap);
    }

    @Override
    public String createError (String message) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("message", message);
        return xmlFromMap("error", xmlTagMap);
        
    }

    @Override
    public String createMessage (String from, String to, String body) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("from", from);
        xmlTagMap.put("to", to);
        xmlTagMap.put("body", body);
        return xmlFromMap("message", xmlTagMap);
    }

    @Override
    public int getPort () {
        return PORT;
    }

    public static Document loadXMLFrom (String xml) throws SAXException, IOException {
        return loadXMLFrom(new ByteArrayInputStream(xml.getBytes()));
    }

    public static Document loadXMLFrom (InputStream is) throws SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException ex) {
        }
        Document doc = builder.parse(is);
        is.close();
        return doc;
    }
    
    private static String getValue(String input, String tagName) {
        String result;
        try {
            Document doc = GordonBukspanProtocol.loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Element el = XmlUtilities.getElement(root, tagName) ;
            result = XmlUtilities.getContent(el);
            return result;
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return null;
    }
    
    private static String xmlFromMap(String parent, Map<String,String> xmlMap) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, parent);
        for (String tag : xmlMap.values()) {
            Element current = XmlUtilities.makeElement(doc, tag, xmlMap.get(tag));
            root.appendChild(current);
        }
        doc.appendChild(root);
        try {
            xmlString = XmlUtilities.getXmlAsString(doc);
        }
        catch (TransformerException e) {
        }
        return xmlString;
    }

}
