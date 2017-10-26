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
    private int row;
    private int column;
    private int compRow;
    private int compColumn;
    private int playerWins = 0;
    private int computerWins = 0;
    
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
            System.out.println("You have won: " +playerWins +" time(s).");
            System.out.println("You have lost: " +computerWins +" time(s).");
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
        boolean validMove = false;
        boolean firstTurn = true;
        byte[] values = new byte[5];
        instantiateBoard();
        ThreeStonesPacket packet = new ThreeStonesPacket(1, 0, 0, 0, 0);
        packet.sendPacket(out);
        values = packet.receivePacket(in);
        System.out.println(Integer.toString((int)values[0]));
        while(totalTurns < 30){
            printBoardAndResult();
            if(firstTurn){
                while(!validMove){
                    System.out.println("Select your Row");
                    row = Integer.parseInt(reader.next());
                    System.out.println("Select your Column");
                    column = Integer.parseInt(reader.next());
                    if(board[row][column] == Cell.EMPTY){
                        validMove = true;
                    }else{
                        System.out.println("That was an invalid move.");
                    }
                }
                firstTurn = false;
            }else{
                while(!validMove){ // loop for a valid user move
                    System.out.println("Select your Row");
                    row = Integer.parseInt(reader.next());
                    System.out.println("Select your Column");
                    column = Integer.parseInt(reader.next());
                    if((row == compRow || column == compColumn) && (board[row][column] == Cell.EMPTY)){
                        validMove = true;
                    }else{
                        System.out.println("That was an invalid move.");
                    }
                }
            }
            validMove = false;
            packet = new ThreeStonesPacket(4, row-1, column-1, playerScore, compScore);
            board[row-1][column-1] = Cell.WHITE;
            System.out.println("white placed at: " +Integer.toString(row) +", " +Integer.toString(column));
            packet.sendPacket(out);
            values = packet.receivePacket(in);
            compRow = (int) values[1] + 1;
            compColumn = (int) values[2] + 1;
            playerScore = (int) values[3];
            compScore = (int) values[4];
            board[compRow-1][compColumn-1] = Cell.BLACK;
            System.out.println("black placed at: " +Integer.toString(compRow) +", " +Integer.toString(compColumn));
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
