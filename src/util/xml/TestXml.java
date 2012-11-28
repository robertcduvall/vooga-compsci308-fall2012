package util.xml;

import java.io.File;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * 
 * @author Grant Oakley
 * 
 */
public class TestXml {

    private static final String XML_FILE_PATH = "src/util/xml/TestXml.xml";

    private static final String TOP_LEVEL_TAG = "topLevel";
    private static final String ELEMENT1_TAG = "someTag";
    private static final String ELEMENT1_CONTENT = "someContent";
    private static final String ELEMENT1_ATTRIBUTE_NAME = "someAttr";
    private static final String ELEMENT1_ATTRIBUTE_VAL = "someAttrVal";

    private Document doc;
    private Element topLevel;

    @Before
    public void setUp () throws Exception {
        doc = XmlUtilities.makeDocument();
        topLevel = XmlUtilities.makeElement(doc, TOP_LEVEL_TAG);
        doc.appendChild(topLevel);
    }

    @Test
    public void testAppendElement () throws Exception {
        XmlUtilities.appendElement(doc, topLevel, ELEMENT1_TAG, ELEMENT1_CONTENT);
    }

    @Test
    public void testAppendElementWithAttr () throws Exception {
        XmlUtilities.appendElement(doc, topLevel, ELEMENT1_TAG, ELEMENT1_ATTRIBUTE_NAME,
                                   ELEMENT1_ATTRIBUTE_VAL);
    }

    @After
    public void writeFile () throws Exception {
        XmlUtilities.write(doc, XML_FILE_PATH);
    }

}
