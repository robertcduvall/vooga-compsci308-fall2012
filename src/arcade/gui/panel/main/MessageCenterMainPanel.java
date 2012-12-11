package arcade.gui.panel.main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import arcade.gui.Arcade;
import arcade.gui.components.ArcadeListComponent;
import arcade.gui.components.MessageListComponent;
import arcade.gui.panel.ArcadePanel;
import arcade.usermanager.Message;


/**
 * A useful implementation of an ArcadeListMainPanel.
 * Loads and displays MessageListComponents as well
 * as a "Compose A Message." button at the top.
 * 
 * @author Robert Bruce
 */
public class MessageCenterMainPanel extends ArcadeListMainPanel {

    public MessageCenterMainPanel (Arcade a) {
        super(a);
    }

    @Override
    protected ArcadePanel addComponentsToTop (ArcadePanel panel) {
        JButton composeMessageButton = new JButton("Compose A Message.");
        composeMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent arg0) {
                getArcade().saveVariable("UserName", "");
                getArcade().replacePanel("SendMessage");
            }

        });

        panel.add(composeMessageButton, 0);
        return panel;
    }

    @Override
    protected List<ArcadeListComponent> loadListComponents () {
        List<ArcadeListComponent> ret = new ArrayList<ArcadeListComponent>();
        List<Message> myMessages = getArcade().getModelInterface().getMessage();
        for (Message aMessage : myMessages) {
            try {
                ret.add(new MessageListComponent(aMessage, myPanel));
            }
            catch (NullPointerException e) {
                System.out.println("Trouble loading a message...");
            }
        }
        return ret;
    }
}
