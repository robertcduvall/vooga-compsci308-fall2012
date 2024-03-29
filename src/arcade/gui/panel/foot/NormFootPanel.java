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

    private static final int BTN_WIDTH = 200;
    private static final int BTN_HEIGHT = 30;
    
    public NormFootPanel (Arcade a) {
        super(a);
    }

    @Override
    public ArcadePanel createPanel () {
        ArcadePanel myPanel = initializeNewPanel();

        JButton aboutBut = new JButton("About");
        aboutBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        JButton helpBut = new JButton("Help");
        helpBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        JButton companyBut = new JButton("Team");
        companyBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));
        JButton jobsBut = new JButton("Jobs");
        jobsBut.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT));

        aboutBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("AboutMain");
            }
        });

        helpBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("HelpMain");
            }
        });

        companyBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().replacePanel("CompanyMain");
            }
        });

        jobsBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {

                getArcade().replacePanel("JobsMain");
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
