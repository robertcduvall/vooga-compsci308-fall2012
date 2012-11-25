package util.imageprocessing;

import java.awt.Image;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator for images that infinitely loops through the images it contains
 * @author rex, Tony
 *
 */
public class ImageLoop implements Iterator{

	private List<Image> myImages;
	private int myCurrentIndex;
	
	public ImageLoop(List<Image> images) {
		myImages = images;
		myCurrentIndex = 0;
	}

	@Override
	public boolean hasNext() {
		return (myImages.size() > 0);
	}

	@Override
	public Object next() {
		myCurrentIndex ++;
		if (myCurrentIndex >= myImages.size()) {
			myCurrentIndex = 0;
		}
		return myImages.get(myCurrentIndex);
	}

	@Override
	public void remove() {
		myImages.remove(myCurrentIndex);
	}
	
	public void add(Image image) {
		myImages.add(image);
	}
}
