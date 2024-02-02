/*
* Name: Patrick Wu
* Pennkey: patwu
* Execution: n/a
*
* This class represents an individual tile in the 2048 game. Each tile has a value
* which affects it and can be changed. The class also handles the drawing of 
* the individual tile.
*/
public class Tile {
    // length of one side of tile
    private double size;

    // value of tile
    private int value;

    // position of tile
    private int row, col;

    public Tile(double size, int value, int row, int col) {
        this.size = size;
        this.value = value;
        this.row = row;
        this.col = col;
    }

    /* 
    * Inputs: none
    * Outputs: value of tile
    * Description: getter function that returns the value of the tile
    */
    public int getValue() {
        return value;
    }

    /*
    * Inputs: an integer number
    * Outputs: none
    * Description: set the value of the tile to the input number
    */
    public void setValue(int number) {
        value = number;
    }

    /*
    * Inputs: none
    * Outputs: none
    * Description: doubles the value of the tile
    */
    public void merge() {
        value *= 2;
    }

    /*
    * Inputs: none
    * Outputs: none
    * Description: draws the tile
    */
    public void draw() {
        // coordinates determined based on where the tile is in the 2d array
        double yPos = 0.8 - row * 0.2; 
        double xPos = 0.2 + col * 0.2;

        // color gradient
        if (value == 2048) {
            PennDraw.setPenColor(255, 255, 0);
        } else if (value == 1024) {
            PennDraw.setPenColor(255, 216, 0);
        } else if (value == 512) {
            PennDraw.setPenColor(255, 178, 0);
        } else if (value == 256) {
            PennDraw.setPenColor(255, 136, 0);
        } else if (value == 128) {
            PennDraw.setPenColor(255, 76, 0);
        } else if (value == 64) {
            PennDraw.setPenColor(255, 0, 0);
        } else if (value == 32) {
            PennDraw.setPenColor(255, 66, 66);
        } else if (value == 16) {
            PennDraw.setPenColor(255, 122, 122);
        } else if (value == 8) {
            PennDraw.setPenColor(255, 150, 150);
        } else if (value == 4) {
            PennDraw.setPenColor(255, 188, 188);
        } else if (value == 2) {
            PennDraw.setPenColor(255, 239, 239);
        } else {
            PennDraw.setPenColor();
            PennDraw.square(xPos, yPos, size / 2);
        }
        
        // value is drawn if it is more than 0
        if (value > 0) {
            PennDraw.filledSquare(xPos, yPos, size / 2);
            PennDraw.setPenColor();
            PennDraw.square(xPos, yPos, size / 2);
            PennDraw.text(xPos, yPos, value + "");
        }
    }
}