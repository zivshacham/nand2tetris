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

// R0 - LAST SCREEN CELL
	@8191
	D=A
	@SCREEN
	D=D+A
	@R0
	M=D
// R1 - CURRENT CELL TO COLOR
	@SCREEN
	D=A
	@R1
	M=D

(CHECK_KEYBOARD)
	// SET THE CURRENT CELL TO THE FIRST ONE
	@SCREEN
	D=A
	@R1
	M=D

// KBD LOOP
    @KBD
	D=M
	@COLOR_IN_BLACK
	D;JNE
    @COLOR_IN_WHITE
	D;JEQ
	@CHECK_KEYBOARD
	0;JMP

(COLOR_IN_BLACK)
    // COLOR THE CURRENT CELL IN BLACK
    @R1
    A=M
	M=-1

    // INCREMANT THE CURRENT CELL
	@R1
	D=M
	M=D+1

    // IF KBD IS 0 NOW
    @KBD
	D=M
	@CHECK_KEYBOARD
	D;JEQ

    // IF COUNTER REACHED TO THE END OF THE SCREEN
	@R0
	D=M
	@R1
	D=D-M
	@CHECK_KEYBOARD
	D;JLT
	
    // ELSE - CONTINUE COLOR IN BLACK
	@COLOR_IN_BLACK
	0;JMP

(COLOR_IN_WHITE)
    // COLOR THE CURRENT CELL IN WHITE 
    @R1
    A=M
	M=0

    // INCREMENT THE CURRENT CELL
	@R1
	D=M
	M=D+1

    // IF KBD IS 1 NOW
    @KBD
	D=M
	@CHECK_KEYBOARD
	D;JGT

    // IF COUNTER REACHED TO THE END OF THE SCREEN
	@R0
	D=M
	@R1
	D=D-M
	@CHECK_KEYBOARD
	D;JLT
	
    // ELSE - CONTINUE COLOR IN WHITE
	@COLOR_IN_WHITE
	0;JMP