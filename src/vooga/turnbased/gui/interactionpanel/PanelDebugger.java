package vooga.turnbased.gui.interactionpanel;

import java.awt.Graphics;
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
    private static final long serialVersionUID = 1L;
    private InteractionPanel myInteractionPanel;

    /**
     * constructor
     */
    public PanelDebugger () {
        final int OPTION_NUMBER = 5;
        setFocusable(true);
        addMouseListener(new GameMouseListener());
        List<String> options = new LinkedList<String>();
        for (int i = 0; i < OPTION_NUMBER; i++) {
            options.add(((Integer) i).toString());
        }
        // myInteractionPanel = new InteractionPanel(options);
    }

    @Override
    public void paint (Graphics g) {
        g.drawImage(myInteractionPanel.renderImage(), 0, 0, null);
    }

    /**
     * main method used for debugging interaction panel
     * 
     * @param args arguments
     */
    public static void main (String[] args) {
        final int SIZE = 700;
        JFrame myFrame = new JFrame();
        myFrame.setSize(SIZE, SIZE);
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
            myInteractionPanel.highlightOption(e.getPoint());
            repaint();
        }

        @Override
        public void mouseReleased (MouseEvent e) {
            myInteractionPanel.dehighlightOption();
            repaint();
        }
    }
}
