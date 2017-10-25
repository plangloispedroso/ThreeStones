package threestones;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class that represents a game session between a client and a server.
 * 
 * @author Philippe
 */
public class ThreeStonesSession {
    
    private boolean playAgain;
    private boolean gameOver;
    private Socket clientSocket;
    private ThreeStonesPacket packet;
    private InputStream in;
    private OutputStream out;
    private Scanner reader;

    public ThreeStonesSession(InputStream in, OutputStream out) {
        this.playAgain = false;
        this.gameOver = false;
        this.in = in;
        this.out = out;
    }
    
    public void playSession()throws IOException{
        byte[] values = new byte[5];
        reader = new Scanner(System.in);  
        int count = 0;
        ThreeStonesPacket packet = new ThreeStonesPacket(count, 0, 0, 0, 0);
        
        // Check if user wants to start a game
        values = packet.receivePacket(in);
        if((int) values[0] == 1){
            playAgain = true;
        }
        
        // Main session loop
        while(playAgain == true){
            int row;
            int column;
            int playerScore = 0;
            int compScore = 0;
            int turnCounter = 0;
            int[] compXY = new int[2];
            int[] scores = new int[2];
            ThreeStonesGame game = new ThreeStonesGame();
            packet = new ThreeStonesPacket(3, 0, 0, 0, 0); // tell client game will start
            packet.sendPacket(out);
            
            // Main gameplay loop
            while(gameOver == false){
                values = packet.receivePacket(in);
                row = values[1];
                column = values[2];
                game.userMove(row, column); // User makes their move to the board
                compXY = game.compMove(); // Computer decides their move
                playerScore = game.getWhiteScore(); // Get the client's score
                compScore = game.getBlackScore(); // Get the computer's score 
                // Send the computer's move to the client
                packet = new ThreeStonesPacket(4, compXY[0], compXY[1], playerScore, compScore);
                System.out.println("Vlack places at: " +Integer.toString(compXY[0] +1) +", " +Integer.toString(compXY[1] +1));
                packet.sendPacket(out);   
                turnCounter += 2; // two moves have passed
                game.printBoardAndResult();
                if(turnCounter == 36) // Check if the game is over
                    gameOver = true;
            }
            
            // ask user to play another game
            packet = new ThreeStonesPacket(5, 0, 0, 0, 0);
            packet.sendPacket(out);
            values = packet.receivePacket(in);
            
            if((int) values[0] == 2){
                playAgain = false; // client does not want to play again.
            }
        }
        clientSocket.close(); // close the connection
    }
}
