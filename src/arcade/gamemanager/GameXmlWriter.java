package arcade.gamemanager;

import java.io.File;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Xml writer for Game model specific uses.
 * 
 * @author Seon Kang
 * 
 */

public class GameXmlWriter {

	private File myGameXmlFile;
	private Document myGameXmlDocument;

	public GameXmlWriter(File file) {
		setFile(file);
		makeDocument();
	}

	/**
	 * 
	 */
	
	protected void makeDocument() {
		DocumentBuilderFactory myDocBuilderFactory = DocumentBuilderFactory.newInstance();
		
	}

	/**
	 * 
	 * @param f
	 */
	protected void setFile(File f) {
		myGameXmlFile = f;
	}
}
