package util.networking;

import java.util.Map;
import java.util.TreeMap;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import util.xml.*;

public class ProtocolXMPP implements ChatProtocol {
        
    private static final String XML_TEMP_PATH = "src/util/networking/serverRequest.xml";
    private static final double ourVersion = 1.0;
        
    @Override
    public String sendMessage (String dest, String body) {
        Document d = XmlUtilities.makeDocument();
        return body;
    }

    @Override
    public String queryRoomInformation (String roomName) {
        return roomName;
    }

    @Override
    public String openStream(String dest) {
        Document d = XmlUtilities.makeDocument();
        Map<String, String> attributes = new TreeMap<String, String>();
        attributes.put("to", dest);
        attributes.put("version", "1.0");
        attributes.put("xlmns", "jabber:client");
        Element stream = XmlUtilities.makeElement(d, "stream", attributes);
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
    
}
