Name: Patrick Wu
Pennkey: patwu

Instructions:
To run this program, you should type "java TwentyFortyEight" into the terminal. This
runs the TwentyFortyEight class. Afterwards, go to view running program.
To play, use the keyboard controls w, a, s, d which correlate to up, left, down, and 
right, respectively. These controls will allow you to move the tiles around and
combine them to try to reach 2048.

Additional features:
I implemented the color gradient extra credit worth one point. The tiles should 
appear to be different colors as they change value.

File Descriptions:
TwentyFortyEight.java represents the overall 2048 game. It creates a board of tiles 
and runs the game until the player wins or loses. Aftwerwards, it draws the game 
complete screen to display either a victory or loss message. This is the class that
the user runs in the terminal.

Board.java represents the whole 4 x 4 board of tiles in 2048. It handles
the following: movements of the tiles, the user's input, spawning tiles, and 
determining whether the game is over.

Tile.java represents an individual tile in the 2048 game. Each tile has a value
which affects its behavior and can be changed. The class also handles the drawing of 
the individual tile with the color gradient.