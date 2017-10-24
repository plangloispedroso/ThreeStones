package threestones;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class that creates a Client to enter a session of ThreeStones with a server.
 * 
 * @author Philippe
 */
public class ThreeStonesClient {
    
    public static final int PORTNUMBER = 50000;
    private String address;
    private Socket socket;
    private Scanner reader;
    private boolean playerControl = true;
    private boolean gameInProgress = false;
    private InputStream in;
    private OutputStream out;
    private String answer;
    
    /**
     * Default constructor that takes a string to represent the server's 
     * IP address.
     * 
     * @param address 
     */
    public ThreeStonesClient(String address){
        this.address = address;
    }
    
    /**
     * Method that will try to create a connection with the ThreeStones server.
     */
    public void makeConnection(){
       try{
           socket = new Socket(address, PORTNUMBER);
           in = socket.getInputStream();
           out = socket.getOutputStream();
           System.out.println("Connected to server.");
           playSession();
       }catch(IOException e){
           System.out.println("There was a problem connectiong to the server");
       }
    }
    
    /**
     * 
     * @throws IOException 
     */
    public void playSession()throws IOException{
        reader = new Scanner(System.in);
        ThreeStonesPacket packet;
        System.out.println("Would you like to play a game (y/n)");
        answer = reader.next();
        if(answer.equals("y")){
            playGame();
        }else{
            packet = new ThreeStonesPacket(2, 0, 0, 0, 0);
            endSession(packet);
        }          
    }
    
    /**
     * Method that when called will send a packet to the server to tell that
     * the client no longer wishes to play and the session will close.
     */
    private void endSession(ThreeStonesPacket endPacket){
        endPacket.sendPacket(out);
        try{
            socket.close();
        }catch(IOException e){
            System.out.println("There was a plroblem closing the socket "
                + "in the client.");
            System.exit(1);
        }
        System.exit(1);
    }
    
    /**
     * Method that will be the main gameplay loop for the client.
     */
    private void playGame(){
        //GAMELOGIC
    }
    
    /**
     * The client will send the server their move.
     * 
     * @param packet
     * @throws IOException 
     */
    public void makeMove(ThreeStonesPacket packet)throws IOException{
        packet.sendPacket(out);
    }
    
    /**
     * The client will receive the move from the server.
     * 
     * @param packet
     * @throws IOException 
     */
    public void receiveMove(ThreeStonesPacket packet) throws IOException{
        packet.receivePacket(in);
    }
    
}
