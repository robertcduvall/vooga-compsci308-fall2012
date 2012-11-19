package arcade.utility;

import java.io.File;
import java.io.IOException;

/**
 * utility class for file operation
 * @author difan
 *
 */

public class FileOperation {
    
    
    public static void deleteFile(String path){
        File file=new File(path);
        file.delete();
        
        
    }
    
    public static void createFile(String path) throws IOException{
        File file=new File(path);
        if (! file.exists() )
        {
        file.createNewFile();
    }
        
        
    }
    
    public static String stripExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }

    

}
