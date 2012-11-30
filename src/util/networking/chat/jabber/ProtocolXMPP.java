package util.networking.chat.jabber;

import java.util.Map;
import java.util.TreeMap;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.networking.chat.ChatCommand;
import util.networking.chat.ChatProtocol;
import util.xml.*;

public class ProtocolXMPP implements ChatProtocol {

    private static final int PORT = 5222;
    private static final String XML_TEMP_PATH = "src/util/networking/serverRequest.xml";
    private static final double ourVersion = 1.0;

    public String sendMessage (String from, String dest, String body) {
        Document d = XmlUtilities.makeDocument();
        Element message = d.createElement("message");
        Map<String, String> attributes = new TreeMap<String, String>();
        attributes.put("from", from);
        attributes.put("to", dest);
        XmlUtilities.addAttributes(message, attributes);
        d.appendChild(message);
        XmlUtilities.appendElement(d, message, "body", body);
        return docToString(d);
    }
    
    private String docToString(Document d){
        try {
            return XmlUtilities.getXmlAsString(d);
        }
        catch (TransformerException e) {
            return null;
        }  
    }

    @Override
    public ChatCommand getType (String input) {
        return null;
    }

    @Override
    public String getUser (String input) {
        return null;
    }

    @Override
    public String getPassword (String input) {
        return null;
    }

    @Override
    public String getTo (String input) {
        return null;
    }

    @Override
    public String getFrom (String input) {
        return null;
    }

    @Override
    public String createLoggedIn (boolean b) {
        return null;
    }

    @Override
    public String createAddUser (String user) {
        return null;
    }

    @Override
    public String createError (String string) {
        return null;
    }

    @Override
    public String createMessage (String to, String dest, String body) {
        return null;
    }

    @Override
    public int getPort () {
        return PORT;
    }
    
}
