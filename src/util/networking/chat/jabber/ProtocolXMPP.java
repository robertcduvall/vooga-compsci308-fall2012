package util.networking.chat.jabber;

import java.util.Map;
import java.util.TreeMap;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.networking.chat.ChatProtocol;
import util.xml.*;

public class ProtocolXMPP implements ChatProtocol {

    private static final int PORT = 5222;
    private static final String XML_TEMP_PATH = "src/util/networking/serverRequest.xml";
    private static final double ourVersion = 1.0;
        
    @Override
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
    public int getPort () {
        return PORT;
    }

    @Override
    public String getUsersOnline () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String login (String userName, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String register (String userName, String password) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
