package games.robsgame;
import java.awt.Dimension;
import java.awt.Point;
/**
 * 
 * @author Robert Bruce
 * This code isn't the best & is from the first assignment.
 * I just wanted to add it to the arcade so we had more
 * games to play.
 */
public interface Settings {
	public static final Dimension WINDOW_SIZE = new Dimension(1000, 800);
	public static final Dimension PLAYER_SIZE = new Dimension(100, 100);
	public static final Dimension ENEMY_SIZE = new Dimension(80, 80);
	public static final Dimension PROJECTILE_SIZE = new Dimension(40, 40);
	public static final Point CAMERON = new Point(850, 200);
	public static final Point CENTER = new Point(500, 400);
	public static final int BUMP_DIST = 3;
	public static final int WIDTH = 1000;
	public static final int HEIGTH = 800;
	public static final int ONE_SECOND = 1000;
	public static final int FRAMES_PER_SECOND = 20; // Should try to get 25
													// later.
	public static final int CYCLES_BETWEEN_WAVES = 30;
	public static final int NUMBER_OF_WAVES = 10;
	public static final int NO_KEY_PRESSED = -1;
	public static final int PROJECTILE_SPEED = 10;
	public static final int RELOAD_CYCLES = 9;
	public static final int PLAYER_SPEED = 8;
	public static final int ENEMY_SPEED = 7;
	public static final String IMAGE_LOCATION = "src/games/robsgame/images";
	public static final String SOUND_LOCATION = "/sounds/";

}
