package util.networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GameService implements Service {
    private boolean myHostStatus;
    private int myPlayNum;
    private OnlineGameState myOnlineGameState;
    
    public GameService(int playNum, boolean host){
        myPlayNum = playNum;
        myHostStatus = host;
    }
    
    public void serve(InputStream i, OutputStream o) throws IOException{
        ObjectInputStream in = new ObjectInputStream(i);
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(o)));
        
    }
}
