// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

(SET_VARIABLES)
// last_cell - Last cell in the screen
	@8191
	D=A
	@SCREEN
	D=D+A
	@last_cell
	M=D
// current_cell - the current cell to color
	@SCREEN
	D=A
	@current_cell
	M=D

(CHECK_KEYBOARD)
	// Set the current cell into the first one
	@SCREEN
	D=A
	@current_cell
	M=D

// INFINITE LOOP - Checking for keyboard input
    @KBD
	D=M
	// if keyboard != 0 -> color the screen in black
	@COLOR_IN_BLACK
	D;JNE
	// if keyboard == 0 -> color the screen in white
    @COLOR_IN_WHITE
	D;JEQ
	// else -> loop again
	@CHECK_KEYBOARD
	0;JMP

(COLOR_IN_BLACK)
    // Color the current cell in black
    @current_cell
    A=M
	M=-1

    // Increment the current cell 
	@current_cell
	D=M
	M=D+1

    // If keyboard changed to 0 (there is no key pressed) -> go to CHECK_KEYBOARD
    @KBD
	D=M
	@CHECK_KEYBOARD
	D;JEQ

    // If current cell reached to the end of the screen -> go to CHECK_KEYBOARD
	@last_cell
	D=M
	@current_cell
	D=D-M
	@CHECK_KEYBOARD
	D;JLT
	
    // Else - Continue coloring the rest of the screen
	@COLOR_IN_BLACK
	0;JMP

(COLOR_IN_WHITE)
    // Color the current cell in white
    @current_cell
    A=M
	M=0

    // Increment the current cell
	@current_cell
	D=M
	M=D+1

    // If keyboard changed to 1 (there is key pressed) -> go to CHECK_KEYBOARD
    @KBD
	D=M
	@CHECK_KEYBOARD
	D;JGT

    // If current cell reached to the end of the screen -> go to CHECK_KEYBOARD
	@last_cell
	D=M
	@current_cell
	D=D-M
	@CHECK_KEYBOARD
	D;JLT
	
    // Else - Continue coloring the rest of the screen
	@COLOR_IN_WHITE
	0;JMP