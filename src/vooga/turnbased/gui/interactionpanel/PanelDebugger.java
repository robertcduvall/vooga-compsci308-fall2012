package vooga.turnbased.gui.interactionpanel;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelDebugger extends JPanel{
    InteractionPanel myInteractionPanel;
    
    public PanelDebugger() {
        setFocusable(true);
        addMouseListener(new GameMouseListener());
        myInteractionPanel = new InteractionPanel();
    }
    
    public void paintComponents(Graphics g) {
    }
    
    public void paint(Graphics g) {
        myInteractionPanel.paint(g);
    }
    
    public static void main(String[] args) {
        JFrame myFrame = new JFrame();
        myFrame.setSize(700, 700);
        myFrame.setVisible(true);
        myFrame.setTitle("debug");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.add(new PanelDebugger());
    }
    
    private class GameMouseListener extends MouseAdapter {
        @Override
        //specifically deal with left click
        public void mouseClicked (MouseEvent e) {
            myInteractionPanel.handleMouseEvent(e);
            repaint();
        }
    }
}
