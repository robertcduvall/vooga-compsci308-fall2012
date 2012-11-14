package arcade.gui.panel.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import arcade.gui.Arcade;
import arcade.gui.panel.AbstractPanel;
import arcade.gui.panel.user.UserPanel;

public class LoginMainPanel extends AMainPanel {
    
    private ActionListener gobuttonaction;
    private AbstractPanel myself;

    public LoginMainPanel (Arcade a) {
        super(a);
        myself = this;
        setSize(new Dimension(100, 100));
        setBackground(Color.RED);
        setLayout(new BorderLayout());
    }

    @Override
    protected void makeListeners () {

        gobuttonaction = new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                myself.setBackground(Color.BLUE);
            }
        };
        
        
        
    }

    @Override
    protected void addComponents () {

        JButton b = new JButton("press me");
        b.setPreferredSize(new Dimension(20, 20));
        b.addActionListener(gobuttonaction);
        add(b, BorderLayout.SOUTH);
        
        
    }

    @Override
    public void refresh () {
        // TODO Auto-generated method stub
        
    }
    
    public static void main(String [] args){
        Arcade testArcade = new Arcade();
        JFrame testFrame = new JFrame();
        testFrame.setLayout(new BorderLayout());
        
        
        final LoginMainPanel testMe = new LoginMainPanel(testArcade);
        testMe.setBackground(Color.RED);
        testMe.setLayout(new BorderLayout());
        testMe.setSize(new Dimension(100, 100));

        
//        
//        JButton b = new JButton("press me");
//        b.setMaximumSize(new Dimension(100, 100));
//        b.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed (ActionEvent e) {
//                testMe.setBackground(Color.BLUE);
//            }
//        });
//        testMe.add(b, BorderLayout.SOUTH);
        
        
        
        
        testFrame.getContentPane().add(testMe, BorderLayout.CENTER);
        
        
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.pack();

        testFrame.setSize(new Dimension(400, 400));

        testFrame.setVisible(true);
        
    }
    

}
