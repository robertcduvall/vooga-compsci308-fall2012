package util.networking.chat;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.networking.Server;
import util.xml.XmlUtilities;

/**
 * 
 * @author Oren Bukspan
 */
public class ChatServer extends Server {

    private static final int DEFAULT_MAX_CONNECTIONS = 100;
    
    private File myDatabase;    
    private Map<String, String> myUserInfo;
    
    /**
     * Instantiates a new ChatServer that uses ChatProtocol protocol and
     * has at most DEFAULT_MAX_CONNECTIONS connections to the server.
     * 
     * @param protocol The ChatProtocol that the server should use to chat.
     * @param databasePath Path to an xml file to load/store user data.
     * @throws UnknownHostException Could not determine Server's HostName.
     */
    public ChatServer (ChatProtocol protocol, File database) throws UnknownHostException {
        this(protocol, database, DEFAULT_MAX_CONNECTIONS);
    }

    /**
     * Instantiates a new ChatServer that uses ChatProtocol protocol and
     * has at most maxConnections connections to the server.
     * 
     * @param protocol The ChatProtocol that the server should use to chat.
     * @param databasePath Path to an xml file to load/store user data.
     * @param maxConnections the maximum number of connections to the server
     * @throws UnknownHostException Could not determine Sever's HostName.
     */
    public ChatServer (ChatProtocol protocol, File database, int maxConnections) throws UnknownHostException {
        super(maxConnections);
        ChatService myChatService = new ChatService(protocol);
        try {
            this.addService(myChatService, myChatService.getPort());
        }
        catch (IOException e) {
            System.out.println("Specified port is already in use.");
            e.printStackTrace();
        }
        myDatabase = database;
        myUserInfo = loadUserInfo();
    }
    
    public boolean login(String user, String pass) {
        String stored = myUserInfo.get(user);
        if (stored != null) {
            return stored.equals(pass);
        }    
        return false;
    }

    public boolean hasUser (String user) {
        if (myUserInfo !=  null) {
            String stored = myUserInfo.get(user);
            return stored != null;
        }
        return false;
    }
    
    public void addUser(String user, String pass) {
        if (myUserInfo !=  null) {
            myUserInfo.put(user, pass);
            storeUserInfo();
        }
    }
    
    private Map<String, String> loadUserInfo() {
        Map<String, String> userInfo = new HashMap<String, String>();
        Document doc = XmlUtilities.makeDocument(myDatabase);
        NodeList nodes = doc.getElementsByTagName("user");
        Collection<Element> users = XmlUtilities.convertNodeListToCollection(nodes);
        for (Element child : users) {
            String user = XmlUtilities.getChildContent(child, "username");
            String pass = XmlUtilities.getChildContent(child, "password");
            userInfo.put(user,pass);
        }
        return userInfo;
    }
    
    private void storeUserInfo() {
        Document doc = XmlUtilities.makeDocument();
        Element root = XmlUtilities.makeElement(doc, "users");
        for (String user : myUserInfo.keySet()) {
            Element entry = XmlUtilities.makeElement(doc, "user");
            Element username = XmlUtilities.makeElement(doc, "username", user);
            Element password = XmlUtilities.makeElement(doc, "password", myUserInfo.get(user));
            entry.appendChild(username);
            entry.appendChild(password);
            root.appendChild(entry);
       }
       doc.appendChild(root);
       XmlUtilities.write(doc, myDatabase.getPath());
    }
}
