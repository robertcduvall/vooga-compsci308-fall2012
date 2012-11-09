package arcade.usermanager;

import org.w3c.dom.Node;
import java.io.StringWriter;
import java.util.Properties;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;

/**
 * Writes user data to an XML file.
 * @author Howard
 *
 */
public class UserXMLWriter {

    public static String serializeDoc (Node doc) {
        StringWriter outText = new StringWriter();
        StreamResult sr = new StreamResult(outText);
        Properties oprops = new Properties();
        oprops.put(OutputKeys.METHOD, "html");
        oprops.put("indent-amount", "4");
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = null;
        try {
            t = tf.newTransformer();
            t.setOutputProperties(oprops);
            t.transform(new DOMSource(doc), sr);
        }
        catch (Exception e) {
        }
        return outText.toString();
    }
}
