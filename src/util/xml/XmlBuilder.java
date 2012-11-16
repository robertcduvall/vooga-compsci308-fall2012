package util.xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * A set of utility methods for creating, editing, and
 * appending to an xml Document object.
 * 
 * There is much work to do here. We need to consolidate
 * all the xml code and this is only part of it.
 * 
 * This code effects all of us. Please email me with any
 * changes or proposals: stephenalexbrowne@gmail.com
 * 
 * @author Alex Browne
 */

public class XmlBuilder {
    
    /**
     * Creates a Document Object and handles any exceptions
     * that might be thrown. The Document object is brand new
     * and empty.
     * 
     * @return a new (empty) Document Object
     */
    public static Document createDocument() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            return doc;
        } catch (Exception e) {
            System.err.println("Could not instantiate a Document Object: "
                    + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Creates a Document Object and loads in the data from
     * an existing xml file. Also handles any exceptions
     * that might be thrown.
     * 
     * @param file an xml file.
     * @return a Document Object with data loaded from file.
     */
    public static Document createDocument(File file) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (Exception e) {
            System.err.println("Could not instantiate a DocumentBuilder Object: "
                    + e.getMessage());
            e.printStackTrace();
            return null;
        }
        
        try {
            Document doc = dBuilder.parse(file);
            return doc;
        }
        catch (SAXException e) {
            System.err.println("Could not parse the xml file! " + 
                    e.getMessage());
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            System.err.println("There was a problem loading the xml File!" +
            	"Does the file exist? Is it valid? " +
            	e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Creates a Document Object and loads in the data from
     * an existing xml file. Also handles any exceptions
     * that might be thrown.
     * 
     * @param filepath the full path to an xml file.
     * @return a Document Object with data loaded from file.
     */
    public static Document createDocument(String filepath) {
        
        File file = new File(filepath);
        return createDocument(file);
        
    }

}
