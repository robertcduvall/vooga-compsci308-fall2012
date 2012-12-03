package util.networking.chat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import util.networking.chat.ChatClient;
import util.networking.chat.ErrorEvent;
import util.networking.chat.ChatAdapter;
import util.networking.chat.MessageReceivedEvent;
import util.networking.chat.UsersUpdateEvent;


public class ExampleGUI extends JPanel implements KeyListener {
    
    
    
    private ChatClient myChatClient; 
    private JTabbedPane tabbedPane;
    private JTextArea buddyList;
    private List<String> usersOnline;
    private JTextArea userInput;
    private Map<String, JTextArea> recipientsToTextArea;
    
    public ExampleGUI(ChatClient c, List<String> buddyList){
        myChatClient = c;
        usersOnline = buddyList;
        myChatClient.addListener(new ChatAdapter() {
            public void handleMessageReceivedEvent (MessageReceivedEvent e) {
                
            }

            public void handleErrorEvent (ErrorEvent e) {
            }
            
            public void handleUsersUpdateEvent(UsersUpdateEvent e){
                updateBuddyList(e.getUsers());
            }
            
        });
        
        
        recipientsToTextArea = new TreeMap<String, JTextArea>();
        setUpChatGUI();
    }
        
    private void setUpChatGUI () {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Welcome", getWelcomePage());
        add(tabbedPane, BorderLayout.CENTER);
        add(initBuddyList(), BorderLayout.AFTER_LINE_ENDS);
        add(initChatInput(), BorderLayout.AFTER_LAST_LINE);
    }

     private JTextArea initBuddyList () {
        buddyList = new JTextArea("Buddy List", 5, 15);
        for (String s: usersOnline){
            buddyList.append("\n" + s);
        }
        buddyList.setEditable(false);
        buddyList.setWrapStyleWord(true);
        buddyList.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        return buddyList;
    }
     
     private void updateBuddyList(List<String> newUsers){
         usersOnline = newUsers;
         buddyList = initBuddyList();
     }
     
    protected void newConversation(String userName){
        if (userName != null) {
            tabbedPane.addTab(userName, createImageIcon("images/chat-icon.png"),
                              new ChatDialog(""));
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        }    
    }
    
    protected void closeConversation(){
        if (tabbedPane.getTabCount() == 1) return;
        tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
    }
    
    private JScrollPane initChatInput(){
        userInput = new JTextArea(3, 40);
        userInput.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        userInput.setLineWrap(true);
        userInput.setWrapStyleWord(true);
        userInput.addKeyListener(this);
        JScrollPane chatInput = new JScrollPane(userInput);
        chatInput.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatInput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatInput.setMaximumSize(new Dimension(3, 10));
        chatInput.setFocusable(true);
        return chatInput;
    }

    private JPanel getWelcomePage(){
        JPanel jp = new JPanel();
        JEditorPane textArea = new JEditorPane("text/html", "");
        textArea.setPreferredSize(new Dimension(450, 275));
        textArea.setText("\t<b>Welcome to the Gordon-Bukspan Chat Center</b><br><br>To begin chatting, simply select <i>File -> New Conversation</i>. A list of all users online has been provided for you in the far right text pane. When prompted, enter the name of the user you would like to converse with. <br><br> When you are finished, please <b>log out</b> (<i>File -> Logout</i>).<br><br> <u>Authors:</u> Oren Bukspan and Connor Gordon");
        jp.add(textArea);
        return jp;
    }


    private static class ChatDialog extends JPanel {
        private JTextArea textArea;

        public ChatDialog (String text) {
            textArea = new JTextArea(17, 40);
            textArea.setText(text);
            textArea.setEditable(false);
            textArea.setBackground(new Color(220, 226, 255));
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setMaximumSize(new Dimension(17, 40));
            add(scrollPane);
        }

        public JTextArea getTextArea () {
            return textArea;
        }
    }
     

    public ImageIcon createImageIcon (String path) {
        URL imgURL = ExampleGUI.class.getResource(path);
        if (imgURL != null)
            return new ImageIcon(imgURL);
        else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    private void sendMessage(){
        String body = userInput.getText();
        String to = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        myChatClient.sendMessage(to, body);
        userInput.setText("");
    }

    @Override
    public void keyPressed (KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            sendMessage();
    }

    @Override
    public void keyReleased (KeyEvent e) {}

    @Override
    public void keyTyped (KeyEvent e) {}
}
    