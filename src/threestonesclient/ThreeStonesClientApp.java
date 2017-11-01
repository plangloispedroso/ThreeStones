package threestonesclient;

import threestonesclient.ThreeStonesClient;
import java.util.Scanner;

/**
 * Class that is designed to start a client in order to play a game of ThreeStones
 * 
 * @authors Philippe Langlois-Pedroso, Kevin Bui and Amin Manai.
 * @version 1.0
 */
public class ThreeStonesClientApp {
    
    private static Scanner reader;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        reader = new Scanner(System.in);
        System.out.println("Please enter the Server address.");
        String clientAddress = reader.next();
        
        ThreeStonesClient client = new ThreeStonesClient(clientAddress);
        client.makeConnection();
    }
    
}
