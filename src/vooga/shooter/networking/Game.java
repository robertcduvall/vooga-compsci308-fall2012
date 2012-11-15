package vooga.shooter.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * 
 * @author Oren Bukspan
 */
public class Game implements Runnable{

    private OnlineLevel myLevel;
    private PrintWriter myCOut1, myCOut2;
    private BufferedReader myCIn1, myCIn2;

    public Game (Socket c1, Socket c2, OnlineLevel l) throws IOException {
        myCOut1 = new PrintWriter(c1.getOutputStream(), true);
        myCOut2 = new PrintWriter(c2.getOutputStream(), true);
        myCIn1 =  new BufferedReader(new InputStreamReader(c1.getInputStream()));
        myCIn2 =  new BufferedReader(new InputStreamReader(c2.getInputStream()));
        myCOut1.println("server: Connected to Game");
        myCOut2.println("server: Connected to Game");
    }

    @Override
    public void run () {
        // TODO Auto-generated method stub
        myCOut1.close();
        myCOut2.close();
        try {
            myCIn1.close();
            myCIn2.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }  
    }

    /*
     * while (true) {
            input = clientInput.readLine().trim();
            if (input.equals("STOP")) {
                break;
            }
            System.out.println(input);
            output = serverInput.readLine();
            clientOutput.println(output);
            if (output.equals("STOP")) {
                break;
            }
        }
        
        
        
     */
    
}
