package games.robsgame;
import java.awt.Point;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Set;

/**
 * 
 * @author Robert Bruce
 * This code isn't the best & is from the first assignment.
 * I just wanted to add it to the arcade so we had more
 * games to play.
 */
public class Player extends Sprite implements Settings {

	int tReload;
	boolean shooting;

	public Player() {
		super(CENTER, PLAYER_SIZE, 0);
		setImage("realDevil.png");
		shooting = false;
		setHealth(100);
		setDamage(0);
	}

	@Override
	public void update(TGame game) {
		if (getHealth() == 0) {
			game.setGameState(4);
			die(game);
		}
		Point mousePosition = game.getInMouse();
		look(mousePosition);
		compileInput(game.getPressedArray());
		
		if (getCenter().x + dx > WINDOW_SIZE.width - PLAYER_SIZE.width / 2
				|| getCenter().x + dx < PLAYER_SIZE.width / 2)
			dx = 0;
		if (getCenter().y + dy > WINDOW_SIZE.height - PLAYER_SIZE.height / 2
				|| getCenter().y + dy < PLAYER_SIZE.height / 2)
			dy = 0;
		// compileInput(keyPressed);
		move();
		if (shooting)
			shoot(game);
		shooting = false;
		tReload++; // Increase the counter that serves as a "reload time" for
					// shooting.
	}

	private void compileInput(Set<Integer> keyPressed) {
		dx = 0;
		dy = 0;
		if (keyPressed.contains(37) || keyPressed.contains(65))
			dx += -PLAYER_SPEED; // LArrow or A - Left
		if (keyPressed.contains(39) || keyPressed.contains(68))
			dx += PLAYER_SPEED; // RArrow or D - Right
		if (keyPressed.contains(38) || keyPressed.contains(87))
			dy += -PLAYER_SPEED; // UArrow or W - Up
		if (keyPressed.contains(40) || keyPressed.contains(83))
			dy += PLAYER_SPEED; // DArrow or S - Down
		if (keyPressed.contains(10) || keyPressed.contains(32))
			shooting = true; // Enter or Spacebar - shoot
	}

	private void shoot(TGame game) {
		if (tReload > RELOAD_CYCLES) {
			Projectile bullet = new Projectile(getCenter(), PROJECTILE_SIZE,
					getAngle());
			game.addSprite(bullet);
			tReload = 1;
		}

	}

}
