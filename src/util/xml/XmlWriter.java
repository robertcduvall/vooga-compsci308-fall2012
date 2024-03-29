package util.xml;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;


/**
 * @deprecated Use XmlUtilities instead.
 * 
 * helper function in creating elements in xml
 * 
 * @author difan
 * 
 */

public class XmlWriter {

    /**
     * @deprecated use the write method in XmlUtilities instead.
     * 
     * write document into a xml file
     * 
     * @param doc
     * @param filePath(including filename.xml)
     * @author difan zhao
     */

    public static void writeXML (Document doc, String filePath) {

        try {
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory
                    .newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");
        }

        catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

}
