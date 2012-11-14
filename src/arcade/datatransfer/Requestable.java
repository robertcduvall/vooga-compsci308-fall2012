package arcade.datatransfer;

import java.io.Serializable;
import java.util.Map;


/**
 * Each class that needs to interface with the GUI must implement this
 * interface. This interface allows the GUI to make various data requests
 * to the class. There will be put and get requests.
 * 
 * @author Michael Deng
 * 
 */
public interface Requestable {

    public Map<String, Serializable> request (Map<String, Serializable> newRequest);

}
