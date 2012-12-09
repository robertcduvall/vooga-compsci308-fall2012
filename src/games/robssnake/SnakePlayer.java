package games.robssnake;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import vooga.shooter.gameObjects.Bullet;
import vooga.shooter.gameObjects.Player;
import vooga.shooter.gameObjects.spriteUtilities.SpriteActionInterface;

public class SnakePlayer extends Player{

    private static final int SPRITE_MOVEMENT = 7;
    private static final String NO_KEY_PRESSED = "-1";

    public SnakePlayer (Point position, Dimension size, Dimension bounds,
            String imagePath, Point velocity, int health) {
        super(position, size, bounds, imagePath, velocity, health);
    }

    public void setMethods() {
        getMapper().addPair(Integer.toString(KeyEvent.VK_A), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                if (checkBounds(LEFT_BOUND)) {
                    setVelocity(-SPRITE_MOVEMENT, getVelocity().y);
                }
                else {
                    setVelocity(0, 0);
                }
            }
        });

        getMapper().addPair(Integer.toString(KeyEvent.VK_W), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                if (checkBounds(TOP_BOUND)) {
                    setVelocity(getVelocity().x, -SPRITE_MOVEMENT);
                }
                else {
                    setVelocity(0, 0);
                }
            }
        });

        getMapper().addPair(Integer.toString(KeyEvent.VK_D), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                if (checkBounds(RIGHT_BOUND)) {
                    setVelocity(SPRITE_MOVEMENT, getVelocity().y);
                }
                else {
                    setVelocity(0, 0);
                }
            }
        });

        getMapper().addPair(Integer.toString(KeyEvent.VK_S), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                if (checkBounds(BOTTOM_BOUND)) {
                    setVelocity(getVelocity().x, SPRITE_MOVEMENT);
                }
                else {
                    setVelocity(0, 0);
                }
            }
        });

        getMapper().addPair(Integer.toString(KeyEvent.VK_F), new SpriteActionInterface() {
            public void doAction (Object ... o) {
                fireBullet();
            }
        });

        getMapper().addPair(NO_KEY_PRESSED, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                setVelocity(0, 0);
            }
        });

        getMapper().addPair(HIT_BY_BULLET, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                decreaseHealth(((Bullet) o[0]).getDamage());
                ((Bullet) o[0]).die();
            }
        });

        getMapper().addPair(HIT_BY_ENEMY, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                die();
            }
        });
        
        getMapper().addPair(HIT_BY_PLAYER, new SpriteActionInterface() {
            public void doAction (Object ... o) {
                die();
            }
        });
    }
}
