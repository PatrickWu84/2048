/*
* Name: Patrick Wu
* Pennkey: patwu
* Execution: java TwentyFortyEight
*
* This class represents the 2048 game. It creates a board of tiles and runs the game
* until the player wins or loses. Aftwerwards, it draws the game complete screen.
*/
public class TwentyFortyEight {
    public static void main(String[] args) {
        // Instantiate a board
        Board board = new Board(500, 500);

        // set width and height
        PennDraw.setCanvasSize(500, 500);

        // game runs at 30 frames per second
        PennDraw.enableAnimation(30);
        
        // set initial state of board
        board.initialize();

        // run game until player wins or loses
        while (!board.gameOver()) {
            // update the tiles based on keyboard input. controls are w, a, s, d
            board.update();
            // draw the board of tiles
            board.draw();
            PennDraw.advance();
        }

        // draw the game complete screen after the while loop has finished
        board.drawGameCompleteScreen();
    }
}