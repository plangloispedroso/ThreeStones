/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threestones;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A custom packet that is used to communicate information between the client 
 * and server of a ThreeStonesGame.
 * opCode = 1 -> Client wants to play a game.
 * opCode = 2 -> Client does not want to play a game.
 * 
 * @author Philippe
 */
public class ThreeStonesPacket {
    
    private byte[] packet = new byte[5];
    
    /**
     * Constructor that will take 5 different integers representing vital information
     * for a game of THreeStones. Will convert the integers into bytes and place them
     * in to a byte array in order to send through the Input and Output streams.
     * 
     * @param opCode
     * @param positionX
     * @param positionY
     * @param playerPoints
     * @param computerPoints 
     */
    public ThreeStonesPacket(int opCode, int positionX, int positionY, 
        int playerPoints, int computerPoints){
        try{
            packet[0] = (byte) opCode;
            packet[1] = (byte) positionX;
            packet[2] = (byte) positionY;
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
    
    /**
     * Helper method to make sure packets contain what they should.
     * DELETE LATER
     */
    private void checkValues(){
        System.out.println(packet[0] +", " +packet[1] +", " +packet[2] +", "
            +packet[3] +", " +packet[4]);
    }
}
