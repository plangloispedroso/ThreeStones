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
    private boolean playAgain = true;
    private InputStream in;
    private OutputStream out;
    private String answer;
    private Cell[][] board;
    private int playerScore = 0;
    private int compScore = 0;
    private int totalTurns = 0;
    private int x;
    private int y;
    
    public enum Cell {
        WALL, EMPTY, WHITE, BLACK
    };
    
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
        while(playAgain){
            ThreeStonesPacket packet;
            System.out.println("Would you like to play a game (y/n)");
            answer = reader.next();
            if(answer.equals("y")){
                playGame();
            }else{
                packet = new ThreeStonesPacket(2, 0, 0, 0, 0);
                playAgain = false;
                endSession(packet);
            }    
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
    }
    
    /**
     * Method that will be the main gameplay loop for the client.
     */
    private void playGame(){
        totalTurns = 0;
        playerScore = 0;
        compScore = 0;
        instantiateBoard();
        ThreeStonesPacket packet;
        byte[] values = new byte[5];
        while(totalTurns != 36){
            printBoardAndResult();
            System.out.println("Select your Column");
            x = Integer.parseInt(reader.next());
            System.out.println("Select your Row");
            y = Integer.parseInt(reader.next());
            packet = new ThreeStonesPacket(4, x, y, playerScore, compScore);
            board[x][y] = Cell.WHITE;
            packet.sendPacket(out);
            values = packet.receivePacket(in);
            board[(int)values[1]][(int)values[2]] = Cell.BLACK;
            playerScore = (int) values[3];
            compScore = (int) values[4];
            totalTurns += 2;
        }
    }
    
    /**
     * 
     */
    private void instantiateBoard(){
        board = new Cell[11][11];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == 0 || i == 1 || i == 9 || i == 10) {
                    board[i][j] = Cell.WALL;
                }
                if (i == 2) {
                    if (j == 4 || j == 5 || j == 6) {
                        board[i][j] = Cell.EMPTY;
                    } else {
                        board[i][j] = Cell.WALL;
                    }
                }
                if (i == 3) {
                    if (j == 3 || j == 4 || j == 5 || j == 6 || j == 7) {
                        board[i][j] = Cell.EMPTY;
                    } else {
                        board[i][j] = Cell.WALL;
                    }
                }

                if (i == 4) {
                    if (j == 2 || j == 3 || j == 4 || j == 5 || j == 6 || j == 7 || j == 8) {
                        board[i][j] = Cell.EMPTY;
                    } else {
                        board[i][j] = Cell.WALL;
                    }
                }

                if (i == 5) {
                    if (j == 2 || j == 3 || j == 4 || j == 6 || j == 7 || j == 8) {
                        board[i][j] = Cell.EMPTY;
                    } else {
                        board[i][j] = Cell.WALL;
                    }
                }

                if (i == 6) {
                    if (j == 2 || j == 3 || j == 4 || j == 5 || j == 6 || j == 7 || j == 8) {
                        board[i][j] = Cell.EMPTY;
                    } else {
                        board[i][j] = Cell.WALL;
                    }
                }

                if (i == 7) {
                    if (j == 3 || j == 4 || j == 5 || j == 6 || j == 7) {
                        board[i][j] = Cell.EMPTY;
                    } else {
                        board[i][j] = Cell.WALL;
                    }
                }

                if (i == 8) {
                    if (j == 4 || j == 5 || j == 6) {
                        board[i][j] = Cell.EMPTY;
                    } else {
                        board[i][j] = Cell.WALL;
                    }
                }
            }
        }
    }
    
    /**
     * Print the board and score, only for testing purposes
     */
    public void printBoardAndResult() {
        String result = "";
        for (ThreeStonesClient.Cell[] row : board) {
            for (ThreeStonesClient.Cell c : row) {
                result += " " + c;
            }
            result += "\n";
        }
        System.out.println(result);
        System.out.println("Total Turns: " + totalTurns);
        System.out.println("Player Score: " + playerScore);
        System.out.println("Comp Score: " + compScore);
    }

    
}
