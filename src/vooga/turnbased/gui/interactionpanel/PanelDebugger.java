package vooga.turnbased.gui.interactionpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelDebugger extends JPanel{
    InteractionPanel myInteractionPanel;
    
    public PanelDebugger() {
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
}
