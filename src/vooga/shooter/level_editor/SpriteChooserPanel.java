package vooga.shooter.level_editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class SpriteChooserPanel extends JPanel {
    private JPanel spritePanel;
    private ImageIcon selectedSprite;
    private static final int iconWidth = 32;
    private GridLayout layout;

    public SpriteChooserPanel () {
        spritePanel = new JPanel();
        selectedSprite = null;
        layout = new GridLayout(0, 5);
        spritePanel.setLayout(layout);
        this.setLayout(new BorderLayout());
        this.add(spritePanel, BorderLayout.NORTH);

    }

    private void loadSprites () {

    }

    public void actionPerformed (ActionEvent e) {
        // TODO: complete
    }

    public ImageIcon getSelectedSprite () {
        return selectedSprite;
    }

}
