package util.graphicprocessing;

import java.awt.Image;
import java.util.Iterator;
import java.util.List;


/**
 * Iterator for images that infinitely loops through the images it contains
 * Can be used to simulate motion
 * 
 * @author rex, Tony
 * 
 */
public class ImageLoop implements Iterator<Image> {

    private List<Image> myImages;
    private int myCurrentIndex;

    /**
     * Constructed from a collection of images
     * @param images 
     */
    public ImageLoop (List<Image> images) {
        myImages = images;
        myCurrentIndex = 0;
    }

    @Override
    public boolean hasNext () {
        return myImages.size() > 0;
    }

    @Override
    public Image next () {
        myCurrentIndex++;
        if (myCurrentIndex >= myImages.size()) {
            myCurrentIndex = 0;
        }
        return myImages.get(myCurrentIndex);
    }

    @Override
    public void remove () {
        myImages.remove(myCurrentIndex);
    }
    /**
     * Add an image to the loop
     * @param image 
     */
    public void add (Image image) {
        myImages.add(image);
    }
}
