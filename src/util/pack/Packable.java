package util.pack;

import java.awt.Dimension;
import java.awt.Point;
import org.w3c.dom.Document;

public interface Packable<T> {

    /**
     * Serializes or "packs" an object of class T
     * into an xml format. This stores all the parameters
     * that can be passed to the objects constructor to
     * reconstruct the object.
     * 
     * @return Document an xml document that contains all
     * the relevant information about the object
     */
    public Document pack();

    /**
     * Instantiates an object from information stored
     * in an xml Document.
     * 
     * @return T an instance of an object of class
     * T that was stored in the xml element.
     */
    // public T unpack(Document xmlData);
    
    /*
     * Oh, the ridiculous qualms of the Java language!!!
     * I would like the unpack method to be static, so that
     * you could just call Enemy.unpack(). However, Java does
     * not allow you to define static methods in an Interface.
     * 
     * I was left with two options: 
     * 
     * 1) make the unpack() method non-static. This leads to
     * highly undesirable code, such as:
     * 
     * Enemy enemySprite = (new Enemy(new Point(0,0),
     *           new Dimension(0,0),
     *           new Dimension(0,0),
     *           System.getProperty("user.dir") + "/src/vooga/shooter/images/alien.png",
     *           new Point(0,0),
     *           0)).unpack(XmlUtilities.makeDocument(File f)));
     * 
     * It should be obvious why this is intolerable. All the parameters
     * passed to the constructor are useless. They are not really used,
     * except to instantiate an Enemy class for us to call unpack() on.
     * 
     * 
     * 2) Remove the unpack() method from the interface (but still add it as
     * a static method to anything that should be packable).
     * 
     * 
     * I've decided to go with option 2. There is no way to enforce
     * the fact that the unpack() method must be implemented. This is
     * incredibly unfortunate. 
     *           
     */
    
}
