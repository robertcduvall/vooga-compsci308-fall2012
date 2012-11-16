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
    

}
