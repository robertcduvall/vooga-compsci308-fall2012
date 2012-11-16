package vooga.platformer.leveleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class SpritePopupMenu extends JPopupMenu{
    protected SpritePopupMenu (MouseEvent e, final Sprite s, LevelBoard b) {
        JPopupMenu pop = new JPopupMenu();
        JMenuItem j = new JMenuItem();
        j.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if ("Flip".equals(event.getActionCommand())) {
                    s.flipImage();
                }
                else if ("Duplicate".equals(event.getActionCommand())) {
                    Sprite ns = new Sprite(s.getType(), s.getX(), s.getY(),
                            s.getWidth(), s.getHeight(), s.getImagePath());
                    c.add()
                }
            }
        });
        pop.show(c, e.getX(), e.getY());
    }
}
