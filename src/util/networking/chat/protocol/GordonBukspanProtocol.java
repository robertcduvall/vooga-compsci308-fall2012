package util.networking.chat.protocol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
            commandName = el.getTagName();
            ChatCommand c = ChatCommand.getChatCommandFromString(commandName);
            if (c != null) { return c; }
        }
        catch (SAXException e) {
            return ChatCommand.UNKNOWN;
        }
        catch (IOException e) {
        }
        return ChatCommand.UNKNOWN;
    }

    // Read specific data from XML String.

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
    public String getErrorMessage (String input) {
        return getValue(input, "message");
    }

    @Override
    public String getBody (String input) {
        return getValue(input, "body");
    }

    @Override
    public boolean getStatus (String input) {
        String s = getValue(input, "status");
        return Boolean.parseBoolean(s);
    }

    @Override
    public List<String> getListUsers (String input) {
        return getListValues(input, "user");
    }

    // Generate XML from input

    @Override
    public String createLoggedIn (String user, boolean b) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("user", user);
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
    public String createLogin (String user, String password) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("user", user);
        xmlTagMap.put("password", password);
        return xmlFromMap("login", xmlTagMap);
    }

    @Override
    public String createLogout (String user) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("user", user);
        return xmlFromMap("logout", xmlTagMap);
    }

    @Override
    public String createRegister (String user, String password) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        xmlTagMap.put("user", user);
        xmlTagMap.put("password", password);
        return xmlFromMap("register", xmlTagMap);
    }

    @Override
    public String createListUsers (List<String> users) {
        Map<String, String> xmlTagMap = new HashMap<String, String>();
        for (String user : users) {
            xmlTagMap.put("user", user);
        }
        return xmlFromMap("listUsers", xmlTagMap);
    }

    @Override
    public int getPort () {
        return PORT;
    }

    // Protocol helper functions below.

    private static Document loadXMLFrom (String xml) throws SAXException, IOException {
        return loadXMLFrom(new ByteArrayInputStream(xml.getBytes()));
    }

    private static Document loadXMLFrom (InputStream is) throws SAXException, IOException {
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

    private static String getValue (String input, String tagName) {
        String result = null;
        try {
            Document doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Element el = XmlUtilities.getElement(root, tagName);
            result = XmlUtilities.getContent(el);
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return result;
    }

    private static List<String> getListValues (String input, String tagName) {
        List<String> result = new ArrayList<String>();
        try {
            Document doc = loadXMLFrom(input);
            Element root = doc.getDocumentElement();
            Collection<Element> elements = XmlUtilities.getElements(root, tagName);
            for (Element e : elements) {
                result.add(e.getTextContent());
            }
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
        return result;
    }

    private static String xmlFromMap (String parent, Map<String, String> xmlMap) {
        String xmlString = null;
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, parent);
        for (String tag : xmlMap.keySet()) {
            Element current = XmlUtilities.makeElement(doc, tag, xmlMap.get(tag));
            root.appendChild(current);
        }
        doc.appendChild(root);
        try {
            xmlString = XmlUtilities.getXmlAsString(doc).replace("\r\n", "").replace("\n", "");
        }
        catch (TransformerException e) {
        }
        return xmlString;
    }
}
