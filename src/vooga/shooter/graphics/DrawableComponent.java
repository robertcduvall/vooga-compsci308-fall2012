package vooga.shooter.graphics;

import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;

public interface DrawableComponent {
    public void update();
    public void paint(Graphics g);
    public void setMouseListener(MouseMotionListener m);
    public void setKeyboardListener(KeyListener k);
}
