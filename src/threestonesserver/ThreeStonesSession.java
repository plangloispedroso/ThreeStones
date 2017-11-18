package threestonesserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import threestones.ThreeStonesGame;
import threestonesdatacomm.ThreeStonesPacket;

/**
 * Class that represents a game session between a client and a server.
 *
 * @authors Philippe Langlois-Pedroso and Kevin Bui
 * @version 1.0
 */
public class ThreeStonesSession implements Runnable {

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

    public void playSession() throws IOException {
        byte[] values = new byte[5];
        reader = new Scanner(System.in);
        int count = 0;
        ThreeStonesPacket packet = new ThreeStonesPacket(count, 0, 0, 0, 0);

        // Check if user wants to start a game
        values = packet.receivePacket(in);
        if ((int) values[0] == 1) {
            playAgain = true;
        }

        // Main session loop
        while (playAgain == true) {
            int playerRow = 0;
            int playerColumn = 0;
            int playerScore = 0;
            int compScore = 0;
            int turnCounter = 0;
            int[] compXY = new int[]{0, 0};
            boolean firstMove = true;
            boolean validMove = false;
            ThreeStonesGame game = new ThreeStonesGame();
            packet = new ThreeStonesPacket(3, 0, 0, 0, 0); // tell client game will start
            packet.sendPacket(out);

            // Main gameplay loop
            while (gameOver == false) {
                if (turnCounter == 14) // Check if the game is over
                {
                    gameOver = true;
                }

                // First time you enter the loop, validMove is always false
                while (!validMove) {
                    values = packet.receivePacket(in);
                    playerRow = (int) values[1];
                    playerColumn = (int) values[2];

                    // If first move, check if cell is empty
                    if (firstMove) {
                        if (game.isEmptyCell(playerRow, playerColumn)) {
                            validMove = true;
                            firstMove = false;
                        }
                    } else {
                        // Check if there is an available move
                        if (!game.isThereAnAvailableMove(compXY[0], compXY[1])) {
                            if (game.isEmptyCell(playerRow, playerColumn) == true) {
                                validMove = true;
                            }
                        } else {
                            // Check if the player column and player row is the same as computer
                            if (playerRow == compXY[0] || playerColumn == compXY[1]) {
                                if (game.isEmptyCell(playerRow, playerColumn)) {
                                    validMove = true;
                                }
                            }
                        }
                    }
                    // Sends the error packet
                    if (validMove == false) {
                        packet = new ThreeStonesPacket(6, 0, 0, playerScore, compScore);
                        packet.sendPacket(out);
                    }
                }

                validMove = false;

                game.userMove(playerRow, playerColumn); // User makes their move to the board 
                compXY = game.compMove(playerRow, playerColumn); // Computer decides their move
                playerScore = game.getWhiteScore(); // Get the client's score
                compScore = game.getBlackScore(); // Get the computer's score 

                // Send the computer's move to the client
                packet = new ThreeStonesPacket(4, compXY[0], compXY[1], playerScore, compScore);
                System.out.println("Black places at: " + Integer.toString(compXY[0] + 1) + ", " + Integer.toString(compXY[1] + 1));
                packet.sendPacket(out);
                turnCounter += 1; // two moves have passed
                game.printBoardAndResult();
            }

            // ask user to play another game
            packet = new ThreeStonesPacket(5, 0, 0, 0, 0);
            packet.sendPacket(out);
            values = packet.receivePacket(in);

            if ((int) values[0] == 2) {
                playAgain = false; // client does not want to play again.
            }
        }
    }

   @Override
    public void run() {
        try {
            playSession();
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        System.out.println("Finished serving lient");
    }
}
