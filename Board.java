/*
* Name: Patrick Wu
* Pennkey: patwu
* Execution: n/a
*
* This class represents the whole 4 x 4 board of tiles in 2048. It handles
* the following: movements of the tiles, the user's input, spawning tiles, and 
* whether the game is over.
*/

public class Board {
    // width and heigh of screen (the board)
    private int width, height;

    /// the tiles in the board
    private Tile[][] tiles = new Tile[4][4];

    // number of moves
    private int numMoves = 0;

    // are there possible moves. initially set at true
    private boolean movesUp, movesRight, movesDown, movesLeft;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                this.tiles[row][col] = new Tile(0.2, 0, row, col);
            }
        }
    } 

    /*
    * Inputs: none
    * Outputs: none
    * Description: sets the initial state of the board by spawning two random tiles 
    * onto the empty board
    */
    public void initialize() {
        spawnTile();
        spawnTile();
    }

    /*
    * Inputs: none
    * Outputs: none
    * Description: draws all the tiles of the board
    */
    public void draw() {
        PennDraw.clear(); // clear what was drawn previously
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                tiles[row][col].draw();
            }
        }
    }

    /*
    * Inputs: none
    * Outputs: boolean representing if player has won
    * Description: checks if there is a 2048 tile, which means the player has won
    */
    private boolean didPlayerWin() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (tiles[row][col].getValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    * Inputs: none
    * Outputs: boolean representing if there are any possible merges
    * Description: checks if there are any possible merges in any direction
    */
    private boolean merges() {
        // iterate through tiles
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                // no last row or column to not exceed array
                if (col < 3) {
                    if (tiles[row][col].getValue() == 
                    tiles[row][col + 1].getValue()) {
                        return true;
                    }
                }
                if (row < 3) {
                    if (tiles[row][col].getValue() == 
                    tiles[row + 1][col].getValue()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
    * Inputs: none
    * Outputs: boolean representing if player has lost
    * Description: check if board is full, no possible moves, and no 2048 tile. this
    * means the player has lost
    */
    private boolean didPlayerLose() {
        int count = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (tiles[row][col].getValue() > 0 && 
                tiles[row][col].getValue() < 2048) {
                    count++;
                }
            }
        }
        if (count == 16 && !merges()) {
            return true;
        }
        return false;
    }

    /*
    * Inputs: none
    * Outputs: boolean representing if game is over
    * Description: checks if player has won or lost, which means game is over
    */
    public boolean gameOver() {
        return didPlayerWin() || didPlayerLose();
    }

    /*
    * Inputs: none
    * Outputs: none
    * Description: updates all the tiles in the board based on the user's 
    * character input. controls are w, a, s, d. 
    */ 
    public void update() {
        if (PennDraw.hasNextKeyTyped()) {
            char input = PennDraw.nextKeyTyped();
            if (input == 'w') {
                movesUp = false; // initally set at false
                moveUp();
                mergeUp();
                updater(movesUp);
            }
            if (input == 'd') {
                movesRight = false;
                moveRight();
                mergeRight();
                updater(movesRight);
                
            }
            if (input == 's') {
                movesDown = false;
                moveDown();
                mergeDown();
                updater(movesDown);
            }
            if (input == 'a') {
                movesLeft = false;
                moveLeft();
                mergeLeft();
                updater(movesLeft);
            }
        }
    }

    /*
    * Inputs: a boolean moves representing if a move was made
    * Outputs: none
    * Description: when a move is made, spawn a new tile randomly and increase the
    * number of moves by one
    */
    private void updater(boolean moves) {
        if (moves) {
            spawnTile();
            numMoves++;
        }
    }

    /* 
    * note: movement and merge functions are similar for all 4 directions, so I only
    * left comments explaining moveUp and mergeUp. the same comments apply for all 4,
    * just fill in corresponding directions.
    */

    /*
    * Inputs: none
    * Outputs: none
    * Description: moves tiles up in accordance to mechanics of 2048 game. 
    */
    private void moveUp() {
        // iterate through tiles 
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {
                // move tiles whose value is not 0
                if (tiles[row][col].getValue() == 0) {
                    // iterate through rest of rows, stopping when a tile has value
                    for (int tempRow = row + 1; tempRow < 4; tempRow++) {
                        if (tiles[tempRow][col].getValue() > 0) {
                            movesUp = true;
                            // tile with value replaces other empty tile ("moves")
                            tiles[row][col].setValue(tiles[tempRow][col].getValue());
                            tiles[tempRow][col].setValue(0);
                            break;    
                        } 
                    }
                }
            }
        }   
    }

    /* 
    * Inputs: none
    * Outputs: none
    * Description: merges tiles upward in accordance to mechanics of 2048 game.
    *              always runs after moveUp function so that tiles to be merged
    *              are already adjacent.
    */
    private void mergeUp() {
        // iterate through tiles
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 3; row++) {
                // merge when two tiles on top of each other have same value
                if (tiles[row][col].getValue() > 0 && 
                tiles[row + 1][col].getValue() == tiles[row][col].getValue()) {
                    movesUp = true;
                    // upper tile doubles, lower tile becomes empty
                    tiles[row][col].merge();
                    tiles[row + 1][col].setValue(0);
                } 
            } 
        }
        moveUp(); // ensure that empty tiles caused by merge are moved into
    }

    /*
    * Inputs: none
    * Outputs: none
    * Description: moves tiles right in accordance to mechanics of 2048 game. 
    */
    private void moveRight() {
        for (int row = 0; row < 4; row++) {
            for (int col = 3; col > 0; col--) {
                if (tiles[row][col].getValue() == 0) {
                    for (int tempCol = col - 1; tempCol >= 0; tempCol--) {
                        if (tiles[row][tempCol].getValue() > 0) {
                            movesRight = true;
                            tiles[row][col].setValue(tiles[row][tempCol].getValue());
                            tiles[row][tempCol].setValue(0);    
                            break;
                        } 
                    }
                } 
            }
        }
    }

    /* 
    * Inputs: none
    * Outputs: none
    * Description: merges tiles rightward in accordance to mechanics of 2048 game.
    *              always runs after moveRight function so that tiles to be merged
    *              are already adjacent.
    */
    private void mergeRight() {
        for (int row = 0; row < 4; row++) {
            for (int col = 3; col > 0; col--) {
                if (tiles[row][col].getValue() > 0 && 
                tiles[row][col - 1].getValue() == tiles[row][col].getValue()) {
                    movesRight = true;
                    tiles[row][col].merge();
                    tiles[row][col - 1].setValue(0); 
                } 
            }
        }
        moveRight();  
    }

    /*
    * Inputs: none
    * Outputs: none
    * Description: moves tiles down in accordance to mechanics of 2048 game. 
    */
    private void moveDown() {
        for (int col = 0; col < 4; col++) {
            for (int row = 3; row > 0; row--) {
                if (tiles[row][col].getValue() == 0) {
                    for (int tempRow = row - 1; tempRow >= 0; tempRow--) {
                        if (tiles[tempRow][col].getValue() > 0) {
                            movesDown = true;
                            tiles[row][col].setValue(tiles[tempRow][col].getValue());
                            tiles[tempRow][col].setValue(0);    
                            break;
                        } 
                    }
                }
            }
        }   
    }

    /* 
    * Inputs: none
    * Outputs: none
    * Description: merges tiles downward in accordance to mechanics of 2048 game.
    *              always runs after moveDown function so that tiles to be merged
    *              are already adjacent.
    */
    private void mergeDown() {
        for (int col = 0; col < 4; col++) {
            for (int row = 3; row > 0; row--) {
                if (tiles[row][col].getValue() > 0 && 
                tiles[row - 1][col].getValue() == tiles[row][col].getValue()) {
                    movesDown = true;
                    tiles[row][col].merge();
                    tiles[row - 1][col].setValue(0); 
                } 
            }
        }
        moveDown();
    }

    /*
    * Inputs: none
    * Outputs: none
    * Description: moves tiles left in accordance to mechanics of 2048 game. 
    */
    private void moveLeft() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                if (tiles[row][col].getValue() == 0) {
                    for (int tempCol = col + 1; tempCol < 4; tempCol++) {
                        if (tiles[row][tempCol].getValue() > 0) {
                            movesLeft = true;
                            tiles[row][col].setValue(tiles[row][tempCol].getValue());
                            tiles[row][tempCol].setValue(0);    
                            break;
                        } 
                    }
                } 
            }
        }
    }

    /* 
    * Inputs: none
    * Outputs: none
    * Description: merges tiles leftward in accordance to mechanics of 2048 game.
    *              always runs after moveLeft function so that tiles to be merged
    *              are already adjacent.
    */
    private void mergeLeft() {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 3; col++) {
                if (tiles[row][col].getValue() > 0 && 
                tiles[row][col + 1].getValue() == tiles[row][col].getValue()) {
                    movesLeft = true;
                    tiles[row][col].merge();
                    tiles[row][col + 1].setValue(0);
                } 
            }
        }
        moveLeft(); 
    }

    /*
    * Inputs: none
    * Outpus: none
    * Description: set the value of one empty tile in the board to 2 or 4
    */
    private void spawnTile() {
        // determine number of empty squares
        int count = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (tiles[row][col].getValue() == 0) {
                    count++;
                }
            }
        }

        // randomly set the value of one of the empty tiles to 2 or 4
        int c = 0;
        double spawnOdds = Math.random() * count;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (tiles[row][col].getValue() == 0) {
                    c++;
                    if (spawnOdds < c) {
                        tiles[row][col].setValue(twoOrFour());
                        return;
                    }
                }
            }
        } 
    }

    /* 
    * Inputs: none
    * Outputs: an integer 2 or 4
    * Description: returns 2 90% of the time or 4 10% of the time
    */
    private int twoOrFour() {
        if (Math.random() < 0.1) {
            return 4;
        } else {
            return 2;
        }
    }

    /* 
    * Inputs: none
    * Outputs: none
    * Description: draws the game complete screen when the game is over based on
    *              if the player won or lost
    */
    public void drawGameCompleteScreen() {
        // PennDraw.clear();
        PennDraw.setPenColor();
        PennDraw.setFontSize(50);

        // draw the appropriate screen
        if (didPlayerWin()) {
            PennDraw.text(0.5, 0.5, "You Win! " + numMoves + " moves");
        } else {
            PennDraw.text(0.5, 0.5, "You lost. " + numMoves + " moves");
        }
        PennDraw.advance();
    }

}