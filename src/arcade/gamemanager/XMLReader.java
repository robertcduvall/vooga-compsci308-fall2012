package arcade.gamemanager;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 * Class that reads xml files and returns a String object as a result from an appropriate query.
 * @author Jei Min Yoo
 *
 */
public class XMLReader {

    Document myDoc;

    public void loadXMLFile(String filePath) {
         
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document myDoc = db.parse(file);
            myDoc.getDocumentElement().normalize();
            
        } catch (Exception e) {
            e.getStackTrace();
        }
    
    }
    

}