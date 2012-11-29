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

    @Override
    public String queryRoomInformation (String roomName) {
        return roomName;
    }

    @Override
    public String openStream(String dest) {
        Document d = XmlUtilities.makeDocument();
        d.setXmlVersion("1.0");
        Map<String, String> attributes = new TreeMap<String, String>();
        attributes.put("to", dest);
        attributes.put("version", "1.0");
        attributes.put("xlmns", "jabber:client");
        Element stream = XmlUtilities.makeElement(d, "stream", attributes);
        d.appendChild(d.createComment("hi"));
        d.appendChild(stream);
        XmlUtilities.write(d, "src/util/networking/Tester.xml");
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
    
}
