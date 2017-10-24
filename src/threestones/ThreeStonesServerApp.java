package threestones;

import java.io.IOException;

/**
 * A class that is used to create a ThreeStonesServer object and start running 
 * the server.
 * 
 * @author Philippe
 */
public class ThreeStonesServerApp {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        ThreeStonesServer server = new ThreeStonesServer();
        try{
            server.runServer();
        }catch(IOException ioe){
            System.out.println("There was a problem running the server.");
        }  
    }  
}
