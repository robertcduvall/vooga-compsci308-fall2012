package arcade.gui.frame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import net.miginfocom.swing.MigLayout;
import arcade.gui.Arcade;
import arcade.gui.panel.ArcadePanel;


/**
 * 
 * @author Michael Deng
 * 
 */
public class MainFrameCreator extends AbstractFrameCreator {

    public MainFrameCreator (Arcade a) {
        super(a);
    }

    @Override
    protected void layoutContentPanel () {
        super.layoutContentPanel();

        // set layout for content panel
        // getContentPanel().setLayout(new MigLayout("",
        // "[300!][500:500:1000][300!]",
        // "[100!][50!][450:450:1000][50!]"));

        // getContentPanel().setLayout(new MigLayout("",
        // "[300:300:][500:500:][300:300:]",
        // "[100:100:][50:50:][450:450:][50:50:]"));

        getContentPanel().setLayout(new MigLayout("", "[]0[grow]0[]", "[]0[]0[grow]0[]"));

        ArcadePanel panel1 = new ArcadePanel(getArcade(), "panel1");
        ArcadePanel panel2 = new ArcadePanel(getArcade(), "panel2");
        ArcadePanel panel3 = new ArcadePanel(getArcade(), "panel3");
        ArcadePanel panel4 = new ArcadePanel(getArcade(), "panel4");
        ArcadePanel panel5 = new ArcadePanel(getArcade(), "panel5");
        ArcadePanel panel6 = new ArcadePanel(getArcade(), "panel6");
        ArcadePanel panel7 = new ArcadePanel(getArcade(), "panel7");

        // Canvas panel1 = new Canvas();
        // Canvas panel2 = new Canvas();
        // Canvas panel3 = new Canvas();
        // Canvas panel4 = new Canvas();
        // Canvas panel5 = new Canvas();
        // Canvas panel6 = new Canvas();
        // Canvas panel7 = new Canvas();

        panel1.setMinimumSize(new Dimension(300, 100));
        panel1.setPreferredSize(new Dimension(300, 100));
        panel1.setMaximumSize(new Dimension(300, 100));

        panel2.setMinimumSize(new Dimension(500, 100));
        panel2.setPreferredSize(new Dimension(500, 100));
        panel2.setMaximumSize(new Dimension(10000, 100));

        panel3.setMinimumSize(new Dimension(300, 100));
        panel3.setPreferredSize(new Dimension(300, 100));
        panel3.setMaximumSize(new Dimension(300, 100));

        panel4.setMinimumSize(new Dimension(1100, 50));
        panel4.setPreferredSize(new Dimension(1100, 50));
        panel4.setMaximumSize(new Dimension(10000, 50));

        panel5.setMinimumSize(new Dimension(800, 450));
        panel5.setPreferredSize(new Dimension(800, 450));
        panel5.setMaximumSize(new Dimension(10000, 10000));

        panel6.setMinimumSize(new Dimension(300, 450));
        panel6.setPreferredSize(new Dimension(300, 450));
        panel6.setMaximumSize(new Dimension(300, 10000));

        panel7.setMinimumSize(new Dimension(1100, 50));
        panel7.setPreferredSize(new Dimension(1100, 50));
        panel7.setMaximumSize(new Dimension(10000, 50));

        getContentPanel().add(panel1, "center");
        getContentPanel().add(panel2, "growx, center");
        getContentPanel().add(panel3, "center, wrap");
        getContentPanel().add(panel4, "span, growx, wrap");
        getContentPanel().add(panel5, "span 2, grow");
        getContentPanel().add(panel6, "growy, wrap");
        getContentPanel().add(panel7, "span, growx");

        // getContentPanel().add(panel1, "");
        // getContentPanel().add(panel2, "");
        // getContentPanel().add(panel3, "wrap");
        // getContentPanel().add(panel4, "span, wrap");
        // getContentPanel().add(panel5, "span 2");
        // getContentPanel().add(panel6, "wrap");
        // getContentPanel().add(panel7, "span");

        setupPanel("logo", panel1);
        setupPanel("user", panel3);
        setupPanel("nav", panel4);
        setupPanel("main", panel5);
        setupPanel("search", panel6);
        setupPanel("foot", panel7);

    }

}
