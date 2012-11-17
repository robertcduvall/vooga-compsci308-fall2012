package util.xml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * helper function in creating elements in xml
 * @author difan
 *
 */

public class XmlWriter {
    
    
            
         
        
   
    
    
   
    
    /**
     * write document into a xml file
     * @param doc 
     * @param filePath(including filename.xml)
     * @author difan zhao
     */
    
    public static void writeXML(Document doc, String filePath) {
        
        try{
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
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
