package util.networking.chat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import util.networking.chat.ChatClient;
import util.networking.chat.ChatListener;
import util.networking.chat.ErrorEvent;
import util.networking.chat.MessageReceivedEvent;
import util.networking.chat.UsersUpdateEvent;

/**
 * The ExampleGUI class creates a user interface for the chat client in a panel that can be displayed in a window. 
 * @author Connor Gordon
 *
 */

public class ExampleGUI extends JPanel implements KeyListener {
    
    
    
    private ChatClient myChatClient; 
    private JTabbedPane tabbedPane;
    private JTextArea buddyList;
    private Set<String> usersOnline;
    private JTextArea userInput;
    private Map<String, ChatDialog> usersToDialogs;
    
    public ExampleGUI(ChatClient c){
        myChatClient = c;
        usersOnline = new TreeSet<String>();
        myChatClient.addListener(new ChatListener() {

            public void handleMessageReceivedEvent (MessageReceivedEvent e) {
                if(usersToDialogs.keySet().contains(e.getSender())){
                    JTextArea taNewMessage = usersToDialogs.get(e.getSender()).getTextArea();
                    taNewMessage.append("<" + e.getSender() + ">:     " + e.getMessageBody() + "\n");
                    taNewMessage.setCaretPosition(taNewMessage.getDocument().getLength());
                    if(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()) != e.getSender()){
                        tabbedPane.setIconAt(tabbedPane.indexOfTab(e.getSender()), createImageIcon("images/chat-icon2.png"));
                    }
                }else{
                    ChatDialog cd = new ChatDialog("<" + e.getSender() + ">:     " + e.getMessageBody() + "\n");
                    usersToDialogs.put(e.getSender(), cd);
                    tabbedPane.add(e.getSender(), cd);
                    tabbedPane.setIconAt(tabbedPane.indexOfTab(e.getSender()), createImageIcon("images/chat-icon2.png"));
                }
                    
            }

            @Override
            public void handleErrorEvent (ErrorEvent e) {
                JOptionPane.showMessageDialog(null, e.getErrorMessage());
            }

            @Override
            public void handleUsersUpdateEvent (UsersUpdateEvent e) {
                updateBuddyList();
                
            }});
        
        
        usersToDialogs = new TreeMap<String, ChatDialog>();
        setUpChatGUI();
    }
    
         
    private void setUpChatGUI () {
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Welcome", getWelcomePage());
        add(tabbedPane, BorderLayout.CENTER);
        
        JScrollPane buddyScroll = new JScrollPane(initBuddyList());
        buddyScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        buddyScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(buddyScroll, BorderLayout.AFTER_LINE_ENDS);
        
        add(initChatInput(), BorderLayout.AFTER_LAST_LINE);
        tabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged (ChangeEvent arg0) {
                int currentIndex = tabbedPane.getSelectedIndex();
                if(currentIndex != 0){
                    userInput.setEditable(true);
                    userInput.setText("");
                    tabbedPane.setIconAt(currentIndex, createImageIcon("images/chat-icon.png"));
                }
                else{
                    userInput.setText("");
                    userInput.setEditable(false);
                }
            }});
    }

     private JTextArea initBuddyList () {
        buddyList = new JTextArea("Users Online:\n\n", 5, 15);
        usersOnline.addAll(myChatClient.getListUsers());
        for (String s: usersOnline){
            buddyList.append(s + "\n");
        }
        buddyList.setEditable(false);
        buddyList.setWrapStyleWord(true);
        buddyList.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        return buddyList;
    }
     
     private void updateBuddyList(){
         usersOnline.clear(); usersOnline.addAll(myChatClient.getListUsers());
         buddyList.setText("Users Online:\n\n");
         for(String name : usersOnline){
             buddyList.append(name + "\n");
         }
     }
     
    protected void newConversation(String userName){
        if (userName != null) {
            ChatDialog cd = new ChatDialog("");
            tabbedPane.addTab(userName, createImageIcon("images/chat-icon.png"),
                              cd);
            tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
            usersToDialogs.put(userName, cd);
            System.out.println(userName);
        }    
    }
    
    protected void closeConversation(){
        if (tabbedPane.getTabCount() == 1) return;
        tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
    }
    
    public List<String> getUsersOnline(){
        List<String> temp = new ArrayList<String>();
        for(String s: usersOnline){
            temp.add(s);
        }
        return Collections.unmodifiableList(temp);
    }
    
    private JScrollPane initChatInput(){
        userInput = new JTextArea("Type message here", 3, 42);
        userInput.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        userInput.setLineWrap(true);
        userInput.setWrapStyleWord(true);
        userInput.addKeyListener(this);
        userInput.setCaretPosition(0);
        userInput.setEditable(false);
        userInput.append("\n");
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
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
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
        ChatDialog cd = (ChatDialog)(tabbedPane.getSelectedComponent());
        String content = "<" + myChatClient.getUserName() + ">:     " + body;
        content = content.replace("\r\n", "").replace("\n","");
        cd.getTextArea().append(content+"\n");
    }

    @Override
    public void keyPressed (KeyEvent e) {
    }

    @Override
    public void keyReleased (KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            sendMessage();
    }

    @Override
    public void keyTyped (KeyEvent e) {
    }
    
    protected void logout(){
        myChatClient.logout();
        try{
            ChatApp.class.newInstance().run();
        }
        catch(IllegalAccessException w){}
        catch(InstantiationException ie){}
    }
}
    