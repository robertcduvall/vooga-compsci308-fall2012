package arcade.datatransfer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Given the data transfer function, this class
 * will create a data container that you can fill with your data
 * 
 * @author Michael
 *
 */
public class ContainerFactory {

    public static Map<String, Serializable> createContainer(String function){
        
        Map<String, Serializable> myContainer = new HashMap<String, Serializable>();
        myContainer.put("function", function);
        
        return myContainer;
    }
    
}
