package arcade.utility;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class that imports images. The method is static, so you don't need to
 * instantiate an object.
 * 
 * @author Michael Deng
 *
 */
public class ImageReader {

    /**
     * Returns a specific image. The given directory should be given relative to
     * the source code, this function will find its absolute location.
     * 
     * @param file filename of image
     */
    public static Image loadImage (String dir, String file) {
        try {
            File path = new File(dir + "/" + file);
            return ImageIO.read(path);

        }
        catch (IllegalArgumentException e) {
            System.out.println("Illegal Argument");
        }
        catch (IOException e) {
            System.out.println("IOException");
        }
        return null;
    }
    
}
