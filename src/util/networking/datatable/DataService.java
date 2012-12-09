package util.networking.datatable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import util.datatable.DataTable;
import util.datatable.exceptions.InvalidXMLTagException;
import util.datatable.exceptions.RepeatedColumnNameException;
import util.networking.Server;
import util.networking.Service;
import util.networking.chat.ChatCommand;
import util.networking.chat.ChatServer;

public class DataService implements Service{

    private Server myServer; 
    private DataProtocol myProtocol;
    private DataTable myDataTable;
    
    public DataService(DataProtocol d){
        this(d, new DataTable());
    }
    
    public DataService(DataProtocol d, DataTable dt){
        myProtocol = d;
        myDataTable = dt;
    }
    
    
    public void serve (Socket socket, Server server) {
        System.out.println("Server adding client " + socket.getInetAddress());
        myServer = (DataServer) server;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (IOException e) {
        }

        while (true && in != null) {
            try {
                String input = in.readLine();
                if (input != null && !"".equals(input.trim())) {
                    System.out.println("Server received from " + socket.getInetAddress() + ": " +
                                       input);
                    DataCommand type = myProtocol.getType(input);
                    Method m;
                    m =
                            this.getClass().getDeclaredMethod("process" + type.toString(), String.class,
                                                              Socket.class);
                    m.setAccessible(true);
                    m.invoke(this, input, socket);
                }
            }
            catch (IOException e) {
                if ("Connection reset".equals(e.getMessage())) {
                    break;
                }
            }
            catch (SecurityException e) {
                System.out.println("2: " + e.getMessage());
            }
            catch (NoSuchMethodException e) {
                System.out.println("3: " + e.getMessage());
            }
            catch (IllegalArgumentException e) {
                System.out.println("4: " + e.getMessage());
            }
            catch (IllegalAccessException e) {
                System.out.println("5: " + e.getMessage());
            }
            catch (InvocationTargetException e) {
                System.out.println("6: " + e.getMessage());
            }
        }        
    }
    
    private void processAddRow(String input, Socket socket){
        myDataTable.addNewRow(myProtocol.getRowElement(input));
    }
    
    private void processAddColumns(String input, Socket socket) throws RepeatedColumnNameException, InvalidXMLTagException{
        myDataTable.addNewColumns(myProtocol.getColumns(input));
    }
    
    private void processClear(String input, Socket socket){
        myDataTable.clear();
    }
        
    private void processDelete(String input, Socket socket){
        myDataTable.deleteRowEntry(myProtocol.getStringKey(input), myProtocol.getObjValue(input));
    }
    
    private void processGetDataRows(String input, Socket socket){
        write(socket, myProtocol.createDataRows(myDataTable.getDataRows()));
    }
    private void processGetColumnNames(String input, Socket socket){
        write(socket, myProtocol.createColumnNames(myDataTable.getColumnNames()));
    }
    
    private void processEdit(String input, Socket socket){
        myDataTable.editRowEntry(myProtocol.getStringKey(input), myProtocol.getObjValue(input), myProtocol.getMapEntry());
    }
    
    private void processFind(String input, Socket socket){
        String key = myProtocol.getStringKey(input);
        String value = myProtocol.getObjValue(input);
        write(socket, myProtocol.createFound(myDataTable.find(key, value)));
    }
   
    
    
    private void write (Socket socket, String text) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(text);
            out.flush();
            System.out.println("Server sending to " + socket.getInetAddress() + ": " + text);
        }
        catch (IOException e) {
        }
    }
    
    private void processUnknown (String input, Socket socket) {
        // log the input
        System.out.println("unrecognized command from: " + socket.getInetAddress() + ": " + input);
    }

}
