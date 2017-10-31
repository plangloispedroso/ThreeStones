package threestones;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class that handles all the tasks that a server will perform.
 * 
 * @authors Philippe Langlois-Pedroso, Kevin Bui and Amin Manai.
 * @version 1.0
 */
public class ThreeStonesServer {
    
    private static final int PORTNUMBER = 50000;
    private ServerSocket servSocket;
    
    /**
     * Default constructor.
     */
    public ThreeStonesServer(){
        try{
            servSocket = new ServerSocket(PORTNUMBER);
        }catch(IOException e){
            System.out.println("There was a problem starting the server");
        }
    }
    
    /**
     * Method that will keep the server up and running. The server will listen
     * listen for clients that wish to make a connection. Once connected a session
     * is made and the game begins. When the client closes the connection, the
     * server will return to listening for new clients.
     * 
     * @throws IOException 
     */
    public void runServer()throws IOException{
        for(;;){
            try{
                InetAddress address = InetAddress.getLocalHost();
                System.out.println("The address of this machine is: " +address.getHostAddress());
            }catch(UnknownHostException uhe){
                System.out.println("Unable to determine the host's address.");
            }
            
            // Start looking for clients
            System.out.println("Now looking for clients.");       
            Socket clientSocket = servSocket.accept();
            System.out.println("Handling client at: " 
                +clientSocket.getInetAddress().getHostAddress()
                +" on port: " +clientSocket.getLocalPort());
            
            // Create a new session with client
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            ThreeStonesSession session = new ThreeStonesSession(in, out);
            // Start playing with the client
            session.playSession();
            clientSocket.close();
            System.out.println("Client has disconnected");
        }
    }
}

