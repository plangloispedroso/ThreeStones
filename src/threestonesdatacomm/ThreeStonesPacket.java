package threestonesdatacomm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A custom packet that is used to communicate information between the client 
 * and server of a ThreeStonesGame.
 * 
 * opCode = 1 -> Client wants to play a game.
 * opCode = 2 -> Client does not want to play a game.
 * opCode = 3 -> Server is initializing the game.
 * opCode = 4 -> A move is being made.
 * opCode = 5 -> Server asks user to play again.
 * opCode = 6 -> Client tried to make an invalid move
 * 
 * @authors Philippe Langlois-Pedroso, Kevin Bui and Amin Manai.
 * @version 1.0
 */
public class ThreeStonesPacket {
    
    private byte[] packet = new byte[5];
    
    /**
     * Constructor that will take 5 different integers representing vital information
     * for a game of THreeStones. Will convert the integers into bytes and place them
     * in to a byte array in order to send through the Input and Output streams.
     * 
     * @param opCode
     * @param row
     * @param column
     * @param playerPoints
     * @param computerPoints 
     */
    public ThreeStonesPacket(int opCode, int row, int column, 
        int playerPoints, int computerPoints){
        try{
            packet[0] = (byte) opCode;
            packet[1] = (byte) row;
            packet[2] = (byte) column;
            packet[3] = (byte) playerPoints;
            packet[4] = (byte) computerPoints;
        }catch(Exception ex){
            System.out.println("Error Creating the packet");
        }
    }
    
    /**
     * Method that will send the packet through the InputStream
     * 
     * @param in
     * @return 
     */
    public byte[] receivePacket(InputStream in){
        try{
            in.read(packet);
        }catch(IOException e){
            System.out.println("There was a problem receiving the packet");
        }
        //System.out.println("Receiving Packet");
        //checkValues();
        return packet;
    }
    
    /**
     * Method that will send the packet through the OutputSteam
     * 
     * @param out
     */
    public void sendPacket(OutputStream out){
        try{
            out.write(packet);
        }catch(IOException e){
            System.out.println("There was a problem sending the packet");
        }
        //System.out.println("Sending Packet");
        //checkValues(); 
    }
}
