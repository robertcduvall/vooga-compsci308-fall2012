package games.robsgame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import arcade.utility.ImageReader;

/**
 * 
 * @author Robert Bruce
 * This code isn't the best & is from the first assignment.
 * I just wanted to add it to the arcade so we had more
 * games to play.
 */
public class Sprite implements Settings {
	protected Point myCenter;
	int dx;
	int dy;
	protected int myDamage;
	protected int myHealth;
	protected Dimension mySize;
	protected Image myImage;
	protected double myAngle;
	protected String imageName;
	protected boolean flipped;

	// A few constructors for robustness and to prevent error.
	public Sprite(int x, int y, int size, double angle) {
		this(new Point(x, y), new Dimension(size, size), angle);
	}

	public Sprite(Point center, Dimension size, double angle) {
		setCenter(center.x, center.y);
		setSize(size.width, size.height);
		setAngle(angle);
	}

	public Sprite(int x, int y, int size) {
		this(new Point(x, y), new Dimension(size, size), 0.0);
	}

	public Sprite(int x, int y, int width, int height) {
		this(new Point(x, y), new Dimension(width, height), 0.0);
	}

	public void paint(Graphics2D pen) {
		// save current state of the graphics area
		AffineTransform old = new AffineTransform(pen.getTransform());
		// move graphics area to center of this shape
		pen.translate(getCenter().x, getCenter().y);
		// rotate area about this shape
		if (flipped)
			pen.rotate(myAngle - Math.PI);
		else
			pen.rotate(myAngle);
		// move graphics area back to original position
		pen.translate(-getCenter().x, -getCenter().y);
		// draw as usual (i.e., rotated)
		pen.drawImage(myImage, getLeft(), getTop(), getSize().width,
				getSize().height, null);

		// restore graphics area to its old state, our changes have no lasting
		// effects
		pen.setTransform(old);
	}

	public void update(TGame game) {/* TODO: Override in each class. */
	}

	public void look(Point target) {
		if (target == null)
			return;
		double angle = calculateAngle(target);
		if (angle < -(Math.PI / 2) || angle > Math.PI / 2) {
			if (!flipped) {
				flipped = true;
				imageName = imageName.substring(0, imageName.indexOf("."))
						+ "_flipped"
						+ imageName.substring(imageName.indexOf("."));
				setImage(imageName);
			}
		} else if (flipped) {
			imageName = imageName.replaceFirst("_flipped", "");
			setImage(imageName);
			flipped = false;
		}
		setAngle(angle);
	}

	public void look(double angle) {
		if (angle < -(Math.PI / 2) || angle > Math.PI / 2) {
			if (!flipped) {
				flipped = true;
				imageName = imageName.substring(0, imageName.indexOf("."))
						+ "_flipped"
						+ imageName.substring(imageName.indexOf("."));
				setImage(imageName);
			}
		} else if (flipped) {
			imageName = imageName.replaceFirst("_flipped", "");
			setImage(imageName);
			flipped = false;
		}
		setAngle(angle);
	}

	public void bumpAway(Point moveAwayFrom) {
		calculateAngle(moveAwayFrom);
		int dDx = (int) (Math.cos(myAngle) * BUMP_DIST);
		int dDy = (int) (Math.sin(myAngle) * BUMP_DIST);
		setCenter(getCenter().x - dDx, getCenter().y - dDy);
	}

	public double calculateAngle(Point target) {
		return Math.atan2(target.y - getCenter().y, target.x - getCenter().x);
	}

	public void setCenter(int x, int y) {
		myCenter = new Point(x, y);
	}

	/**
	 * Resets shape's size.
	 */
	public void setSize(int width, int height) {
		mySize = new Dimension(width, height);
	}

	/**
	 * Set this shape's image.
	 */
	public void setImage(Image image) {
		myImage = image;
	}

	public void setAngle(double angle) {
		myAngle = angle;
	}

	public void setImage(String directoryOfImage) {
	    imageName = directoryOfImage;
	    Image img = ImageReader.loadImage(IMAGE_LOCATION, directoryOfImage);
	    setImage(img);
	}

	/**
	 * Returns shape's center.
	 */
	public Point getCenter() {
		return myCenter;
	}

	protected void move() {
		setCenter(getCenter().x + dx, getCenter().y + dy);
	}

	/**
	 * Returns shape's left-most coordinate.
	 */
	public int getLeft() {
		return getCenter().x - getSize().width / 2;
	}

	/**
	 * Returns shape's top-most coordinate.
	 */
	public int getTop() {
		return getCenter().y - getSize().height / 2;
	}

	/**
	 * Returns shape's right-most coordinate.
	 */
	public int getRight() {
		return getCenter().x + getSize().width / 2;
	}

	/**
	 * Reports shape's bottom-most coordinate.
	 * 
	 * @return bottom-most coordinate
	 */
	public int getBottom() {
		return getCenter().y + getSize().height / 2;
	}

	/**
	 * Returns shape's size.
	 */
	public Dimension getSize() {
		return mySize;
	}

	/**
	 * Get this shape's current image.
	 */
	public Image getImage() {
		return myImage;
	}

	public double getAngle() {
		return myAngle;
	}

	/**
	 * Returns true if the given point is within a rectangle representing this
	 * shape.
	 */
	public boolean intersects(Point pt) {
		return (getLeft() <= pt.x && pt.x <= getRight() && getTop() <= pt.y && pt.y <= getBottom());
	}

	public int getDamage() {
		return myDamage;
	}

	public int getHealth() {
		return myHealth;
	}

	public void setDamage(int dam) {
		myDamage = dam;
	}

	public void setHealth(int health) {
		myHealth = health;
	}

	public boolean enemyInCameron() {
		return false;
	}

	public void die(TGame game) {
		System.out.println("Killed one!");
		game.remSprite(this);
	}
}
