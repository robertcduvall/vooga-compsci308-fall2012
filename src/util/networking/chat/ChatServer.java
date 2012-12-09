package util.networking.chat;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import util.networking.Server;
import util.xml.XmlUtilities;


/**
 * ChatServer sets up the basics for a chat program that enables
 * multiple users to create accounts, login, logout, and send messages
 * to the other users online. It saves user data to the specified File
 * and works with any number of corresponding ChatClients that runs on
 * the same ChatProtocol.
 * 
 * @author Oren Bukspan
 */
public class ChatServer extends Server {

    private static final int DEFAULT_MAX_CONNECTIONS = 10;

    private File myDatabase;
    private Map<String, String> myUserInfo;

    /**
     * Instantiates a new ChatServer that uses ChatProtocol protocol and
     * has at most DEFAULT_MAX_CONNECTIONS connections to the server.
     * 
     * @param protocol The ChatProtocol that the server should use to chat.
     * @param database An xml file to load/store user data.
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
     * @param database An xml file to load/store user data.
     * @param maxConnections the maximum number of connections to the server
     * @throws UnknownHostException Could not determine Sever's HostName.
     */
    public ChatServer (ChatProtocol protocol, File database, int maxConnections) 
        throws UnknownHostException {
        super(maxConnections);
        myDatabase = database;
        myUserInfo = Collections.synchronizedMap(loadUserInfo());
        ChatService myChatService = new ChatService(protocol);
        try {
            this.addService(myChatService, myChatService.getPort());
        }
        catch (IOException e) {
            System.out.println("Specified port is already in use.");
            e.printStackTrace();
        }
    }

    /**
     * Logs in a user using the ChatServer's current database.
     * Does not encrypt data. This responsibility is left to programs
     * using the ChatServer.
     * 
     * @param user The username to check.
     * @param pass The password to match.
     * @return Returns TRUE on success and FAlSE if the username is not
     *         in the database OR if pass and the existing password do not
     *         match.
     */
    public boolean login (String user, String pass) {
        synchronized (myUserInfo) {
            String stored = myUserInfo.get(user);
            if (stored != null) { return stored.equals(pass); }
            return false;
        }
    }

    /**
     * Checks if the username exists in the database. Matching previous
     * encryption is left to the program using this ChatServer.
     * 
     * @param user The username to check against the database.
     * @return Returns TRUE if the user exists and FALSE if there is no
     *         such user in the database.
     */
    public boolean hasUser (String user) {
        synchronized (myUserInfo) {
            if (myUserInfo != null) {
                String stored = myUserInfo.get(user);
                return stored != null;
            }
            return false;
        }
    }

    /**
     * Adds a user to the database. Encryption is left to the program
     * using this ChatServer.
     * 
     * @param user The username of the new user.
     * @param pass The password of the new user.
     */
    public void addUser (String user, String pass) {
        if (myUserInfo != null) {
            synchronized (myUserInfo) {
                myUserInfo.put(user, pass);
                storeUserInfo();
            }
        }
    }

    private Map<String, String> loadUserInfo () {
        Map<String, String> userInfo = new HashMap<String, String>();
        Document doc = XmlUtilities.makeDocument(myDatabase);
        NodeList nodes = doc.getElementsByTagName("user");
        Collection<Element> users = XmlUtilities.convertNodeListToCollection(nodes);
        for (Element child : users) {
            String user = XmlUtilities.getChildContent(child, "username");
            String pass = XmlUtilities.getChildContent(child, "password");
            userInfo.put(user, pass);
        }
        return userInfo;
    }

    private void storeUserInfo () {
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
