package arcade.gui.panel.foot;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
public class NormFootPanel extends AFootPanel {

    public NormFootPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();
        System.out.println("NormFootPanel");

        JButton aboutBut = new JButton("About");
        JButton helpBut = new JButton("Help");
        JButton companyBut = new JButton("Company");
        JButton jobsBut = new JButton("Jobs");

        aboutBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                // getArcade().replacePanel("Login");
            }
        });

        helpBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                // getArcade().replacePanel("Login");
            }
        });

        companyBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                // getArcade().replacePanel("Login");
            }
        });

        jobsBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                // getArcade().replacePanel("Login");
            }
        });

        myPanel.setLayout(new MigLayout("", "[c, grow][c, grow][c, grow][c, grow]", "[c]"));

        myPanel.add(aboutBut, "align center");
        myPanel.add(companyBut, "align center");
        myPanel.add(helpBut, "align center");
        myPanel.add(jobsBut, "align center");

        return myPanel;
    }

}
