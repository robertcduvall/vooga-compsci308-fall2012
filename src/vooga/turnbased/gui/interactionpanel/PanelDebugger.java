package vooga.turnbased.gui.interactionpanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * To debug the panel!
 * 
 * @author rex
 * 
 */
public class PanelDebugger extends JPanel {
    InteractionPanel myInteractionPanel;

    public PanelDebugger () {
        setFocusable(true);
        addMouseListener(new GameMouseListener());
        List<String> options = new LinkedList<String>();
        for (int i = 0; i < 5; i++) {
            options.add(((Integer) i).toString());
        }
        myInteractionPanel = new InteractionPanel(options);
    }

    public void paintComponents (Graphics g) {
    }

    public void paint (Graphics g) {
        g.drawImage(myInteractionPanel.renderImage(), 0, 0, null);
    }

    public static void main (String[] args) {
        JFrame myFrame = new JFrame();
        myFrame.setSize(700, 700);
        myFrame.setVisible(true);
        myFrame.setTitle("debug");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.add(new PanelDebugger());
    }

    private class GameMouseListener implements MouseListener {
        @Override
        public void mouseClicked (MouseEvent e) {

        }

        @Override
        public void mouseEntered (MouseEvent e) {
        }

        @Override
        public void mouseExited (MouseEvent e) {
        }

        @Override
        public void mousePressed (MouseEvent e) {
            myInteractionPanel.highlightOption(e);
            repaint();
        }

        @Override
        public void mouseReleased (MouseEvent e) {
            myInteractionPanel.dehighlightOption(e);
            repaint();
        }
    }
}
