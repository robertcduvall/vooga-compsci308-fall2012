package util.networking.chat.gui;


import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.net.URL;

public class ExampleGUI {

    static class TextDemoPanel extends JPanel{
        private JTextArea textArea;

        public TextDemoPanel(String text){
            textArea = new JTextArea(17, 40);
            textArea.setText("<Server>: You are connected. Start Chatting!");
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            add(scrollPane);
        }

        public JTextArea getTextArea() {
            return textArea;
        }
    }

    static class SetTextAction extends AbstractAction {
        private JTabbedPane tabbedPane;

        public SetTextAction(JTabbedPane tabbedPane){
            super("New Conversation");
            this.tabbedPane = tabbedPane;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           
            String value = JOptionPane.showInputDialog(tabbedPane, "Please enter the name of the user youwould like to converse with.", "Start a New Conversation", JOptionPane.QUESTION_MESSAGE);
            if (value != null){
                TextDemoPanel panel = (TextDemoPanel)tabbedPane.getSelectedComponent();
                if (panel != null)
                    panel.getTextArea().setText(value);
            }
        }
    }
    
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = ExampleGUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Welcome to the World of Chatting!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Tab 1", new TextDemoPanel("Tab 1 text"));
        tabbedPane.addTab("Tab1", createImageIcon("images/chat.png"), new TextDemoPanel(""));
        tabbedPane.addTab("Tab 2", new TextDemoPanel("Tab 2 text"));
        tabbedPane.addTab("Tab 3", new TextDemoPanel("Tab 3 text"));

        frame.add(tabbedPane, BorderLayout.CENTER);
        
        JTextArea buddyList = new JTextArea("Hello", 5, 15);
        buddyList.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        frame.add(buddyList, BorderLayout.AFTER_LINE_ENDS);

        JTextArea buddyList2 = new JTextArea("Hello", 3, 10);
        buddyList2.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        frame.add(buddyList2, BorderLayout.AFTER_LAST_LINE);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem item = new JMenuItem(new SetTextAction(tabbedPane));

        menu.add(item);

        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}