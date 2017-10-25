package threestones;

import javax.swing.JOptionPane;

/**
 *
 * @author Kevin Bui
 */
public class ThreeStonesGame {
    
    public enum Cell {
        WALL, EMPTY, WHITE, BLACK
    };
    private Cell[][] board;
    private int totalTurns = 0;
    private int playerTurn = 0;
    private int playerScore = 0;
    private int compScore = 0;
    private boolean firstCompMove = true;
    private int[] decision = new int[2];
    
    /**
     * Default constructor, build the initial board
     */
    public ThreeStonesGame() {
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
     * Method to return the current score for the client.
     * 
     * @return 
     */
    public int getWhiteScore(){
        return playerScore;
    }
    
    /**
     * Method to return the current score for the computer;
     * 
     * @return 
     */
    public int getBlackScore(){
        return compScore;
    }
    
    /**
     * This method is passed the x and y locations that the client would like
     * to place their white piece
     * 
     * @param row
     * @param column
     */
    public void userMove(int row, int column){
        board[row][column] = Cell.WHITE;
        checkScore(row, column);
    }
    
    /**
     * The AI will make their move depending on if it is their first turn or not.
     * If it is their first turn, the computer will place a piece in a random location.
     * If it is not the first turn, the AI will decide what is their most logical next
     * move.
     * 
     * @return 
     */
    public int[] compMove(){
        if(firstCompMove == true){
            firstCompMove = false;
            aiRandomMove();
        }else{
            AIMove();
        }
        totalTurns++;
        return decision;
    }

    /**
     * Selects a random place on the board to place a marble
     */
    public void aiRandomMove() {
        int r1;
        int r2;
        boolean invalid = true;
        while (invalid) {
            r1 = (int) (Math.random() * 11);
            r2 = (int) (Math.random() * 11);
            if (board[r1][r2] == Cell.EMPTY) {
                board[r1][r2] = Cell.BLACK;
                System.out.println("Comp placed marble");
                decision[0] = r1;
                decision[1] = r2;
                checkScore(r1, r2);
                invalid = false;
            }
        }
    }

    /**
     * Checks if any of the players scored any points
     *
     * @param x The x position on the board
     * @param y The y position on the board
     */
    public void checkScore(int x, int y) {
        // Checks left and right for the same marbles
        if (board[x + 1][y] == board[x][y] && board[x - 1][y] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks right for 2 consecutive marbles
        if (board[x + 1][y] == board[x][y] && board[x + 2][y] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks left for 2 consecutive marbles
        if (board[x - 1][y] == board[x][y] && board[x - 2][y] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks top and bottom for the same marbles
        if (board[x][y + 1] == board[x][y] && board[x][y - 1] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks top for 2 consecutive marbles
        if (board[x][y + 1] == board[x][y] && board[x][y + 2] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks bottom for 2 consecutive marbles
        if (board[x][y - 1] == board[x][y] && board[x][y - 2] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks diagonal
        if (board[x - 1][y - 1] == board[x][y] && board[x + 1][y + 1] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks 2 diagonal top left
        if (board[x - 1][y - 1] == board[x][y] && board[x - 2][y - 2] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks 2 diagonal top right
        if (board[x + 1][y - 1] == board[x][y] && board[x + 2][y - 2] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks 2 diagonal bottom left
        if (board[x - 1][y + 1] == board[x][y] && board[x - 2][y + 2] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }

        // Checks 2 diagonal bottom right
        if (board[x + 1][y + 1] == board[x][y] && board[x + 2][y + 2] == board[x][y]) {
            if (board[x][y] == Cell.WHITE) {
                playerScore++;
            } else if (board[x][y] == Cell.BLACK) {
                compScore++;
            }
        }
    }

    /**
     * Checks the possibilities for the AI whether it can score or block a point
     */
    public void AIMove() {
        boolean aiMoved = false;
        outerloop:
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Check for the comp point
                if (board[i][j] == Cell.BLACK) {
                    // Score a point
                    // Check if top has another black cell and bottom cell is empty
                    if (board[i][j - 1] == Cell.BLACK && board[i][j + 1] == Cell.EMPTY) {
                        board[i][j + 1] = Cell.BLACK;
                        decision[0] = i;
                        decision[1] = j+1;
                        //aiMoved = true;
                        checkScore(i, j + 1);
                        //System.out.println("AI Scored!");
                        break outerloop;
                    }

                    // Check if bottom has another black cell and bottom cell is empty
                    if (board[i][j + 1] == Cell.BLACK && board[i][j - 1] == Cell.EMPTY) {
                        board[i][j - 1] = Cell.BLACK;
                        decision[0] = i;
                        decision[1] = j-1;
                        //aiMoved = true;
                        checkScore(i, j - 1);
                        //System.out.println("AI Scored!");
                        break outerloop;
                    }

                    // Check if left has another black cell and right cell is empty
                    if (board[i - 1][j] == Cell.BLACK && board[i + 1][j] == Cell.EMPTY) {
                        board[i + 1][j] = Cell.BLACK;
                        decision[0] = i+1;
                        decision[1] = j;
                        //aiMoved = true;
                        checkScore(i + 1, j);
                        //System.out.println("AI Scored!");
                        break outerloop;
                    }

                    // Check if right has another black cell and left cell is empty
                    if (board[i + 1][j] == Cell.BLACK && board[i - 1][j] == Cell.EMPTY) {
                        board[i - 1][j] = Cell.BLACK;
                        decision[0] = i-1;
                        decision[1] = j;
                        //aiMoved = true;
                        checkScore(i - 1, j);
                        //System.out.println("AI Scored!");
                        break outerloop;
                    }

                    // Check if top left has another black cell and bottom right cell is empty
                    if (board[i - 1][j - 1] == Cell.BLACK && board[i + 1][j + 1] == Cell.EMPTY) {
                        board[i + 1][j + 1] = Cell.BLACK;
                        decision[0] = i+1;
                        decision[1] = j+1;
                        //aiMoved = true;
                        checkScore(i + 1, j + 1);
                        //System.out.println("AI Scored!");
                        break outerloop;
                    }

                    // Check if top right has another black cell and bottom left cell is empty
                    if (board[i + 1][j - 1] == Cell.BLACK && board[i - 1][j + 1] == Cell.EMPTY) {
                        board[i - 1][j + 1] = Cell.BLACK;
                        decision[0] = i-1;
                        decision[1] = j+1;
                        //aiMoved = true;
                        checkScore(i - 1, j + 1);
                        //System.out.println("AI Scored!");
                        break outerloop;
                    }

                    // Check if bottom right has another black cell and top left cell is empty
                    if (board[i + 1][j + 1] == Cell.BLACK && board[i - 1][j - 1] == Cell.EMPTY) {
                        board[i - 1][j - 1] = Cell.BLACK;
                        decision[0] = i-1;
                        decision[1] = j-1;
                        //aiMoved = true;
                        checkScore(i - 1, j - 1);
                        //System.out.println("AI Scored!");
                        break outerloop;
                    }

                    // Check if bottom left has another black cell and top right cell is empty
                    if (board[i - 1][j + 1] == Cell.BLACK && board[i + 1][j - 1] == Cell.EMPTY) {
                        board[i + 1][j - 1] = Cell.BLACK;
                        decision[0] = i+1;
                        decision[1] = j-1;
                        //aiMoved = true;
                        checkScore(i + 1, j - 1);
                        //System.out.println("AI Scored!");
                        break outerloop;
                    }
                }

                if (board[i][j] == Cell.WHITE) {
                    // Block player
                    // Check if top has another black cell and bottom cell is empty
                    if (board[i][j - 1] == Cell.WHITE && board[i][j + 1] == Cell.EMPTY) {
                        board[i][j + 1] = Cell.BLACK;
                        decision[0] = i;
                        decision[1] = j+1;
                        //aiMoved = true;
                        checkScore(i, j + 1);
                        //System.out.println("AI Blocked!");
                        break outerloop;
                    }

                    // Check if bottom has another black cell and bottom cell is empty
                    if (board[i][j + 1] == Cell.WHITE && board[i][j - 1] == Cell.EMPTY) {
                        board[i][j - 1] = Cell.BLACK;
                        decision[0] = i;
                        decision[1] = j-1;
                        //aiMoved = true;
                        checkScore(i, j - 1);
                        //System.out.println("AI Blocked!");
                        break outerloop;
                    }

                    // Check if left has another black cell and right cell is empty
                    if (board[i - 1][j] == Cell.WHITE && board[i + 1][j] == Cell.EMPTY) {
                        board[i + 1][j] = Cell.BLACK;
                        decision[0] = i+1;
                        decision[1] = j;
                        //aiMoved = true;
                        checkScore(i + 1, j);
                        //System.out.println("AI Blocked!");
                        break outerloop;
                    }

                    // Check if right has another black cell and left cell is empty
                    if (board[i + 1][j] == Cell.WHITE && board[i - 1][j] == Cell.EMPTY) {
                        board[i - 1][j] = Cell.BLACK;
                        decision[0] = i-1;
                        decision[1] = j;
                        //aiMoved = true;
                        checkScore(i - 1, j);
                        //System.out.println("AI Blocked!");
                        break outerloop;
                    }

                    // Check if top left has another black cell and bottom right cell is empty
                    if (board[i - 1][j - 1] == Cell.WHITE && board[i + 1][j + 1] == Cell.EMPTY) {
                        board[i + 1][j + 1] = Cell.BLACK;
                        decision[0] = i+1;
                        decision[1] = j+1;
                        //aiMoved = true;
                        checkScore(i + 1, j + 1);
                        //System.out.println("AI Blocked!");
                        break outerloop;
                    }

                    // Check if top right has another black cell and bottom left cell is empty
                    if (board[i + 1][j - 1] == Cell.WHITE && board[i - 1][j + 1] == Cell.EMPTY) {
                        board[i - 1][j + 1] = Cell.BLACK;
                        decision[0] = i-1;
                        decision[1] = j+1;
                        //aiMoved = true;
                        checkScore(i - 1, j + 1);
                        //System.out.println("AI Blocked!");
                        break outerloop;
                    }

                    // Check if bottom right has another black cell and top left cell is empty
                    if (board[i + 1][j + 1] == Cell.WHITE && board[i - 1][j - 1] == Cell.EMPTY) {
                        board[i - 1][j - 1] = Cell.BLACK;
                        decision[0] = i-1;
                        decision[1] = j-1;
                        //aiMoved = true;
                        checkScore(i - 1, j - 1);
                        //System.out.println("AI Blocked!");
                        break outerloop;
                    }

                    // Check if bottom left has another black cell and top right cell is empty
                    if (board[i - 1][j + 1] == Cell.WHITE && board[i + 1][j - 1] == Cell.EMPTY) {
                        board[i + 1][j - 1] = Cell.BLACK;
                        decision[0] = i+1;
                        decision[1] = j-1;
                        //aiMoved = true;
                        checkScore(i + 1, j - 1);
                        //System.out.println("AI Blocked!");
                        break outerloop;
                    }
                }
            }
        }
        //return aiMoved;
    }
    
    /**
     * Print the board and score, only for testing purposes
     */
    public void printBoardAndResult() {
        String result = "";
        for (Cell[] row : board) {
            for (Cell c : row) {
                result += " " + c;
            }
            result += "\n";
        }
        System.out.println(result);
        System.out.println("Player Turn: " + playerTurn);
        System.out.println("Total Turns: " + totalTurns);
        System.out.println("Player Score: " + playerScore);
        System.out.println("Comp Score: " + compScore);
    }

    /**
     * Prints the board
     *
     * @return result A String representation of the board
     */
    public String toString() {
        String result = "";
        for (Cell[] row : board) {
            for (Cell c : row) {
                result += " " + c;
            }
            result += "\n";
        }
        return result;
    }
}
