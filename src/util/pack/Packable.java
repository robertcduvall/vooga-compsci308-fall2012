package util.pack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface Packable<T> {

    /**
     * Serializes or "packs" an object of class T
     * into an xml format. This stores all the parameters
     * that can be passed to the objects constructor to
     * reconstruct the object.
     * 
     * @return Element an xml element that contains all
     * the relevant information about the object
     */
    public Document pack();

    /**
     * Instantiates an object from information stored
     * in an xml Element.
     * 
     * @return T an instance of an object of class
     * T that was stored in the xml element.
     */
    public T unpack(Document xmlData);
    
}
