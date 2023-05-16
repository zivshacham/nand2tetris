// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Set R2 as the result cell - set it to 0
	@R2
	M=0

// Set R3 as counter - set it to the value of R1
	@R1
	D=M
	@R3
	M=D

// If one of the products is 0 -> go to END
	@R0
	D=M
	@END
	D;JEQ

	@R1
	D=M
	@END
	D;JEQ

(LOOP)
// Add R0 to R2 (the result)
	@R0
	D=M
	@R2
	M=M+D

// Decrement R3 - the counter
	@R3
	M=M-1		

// Check if R3 (the counter) is 0
// IF counter==0 -> Go to END
// ELSE -> Go to LOOP again

	D=M
	@END
	D;JLE

	@LOOP
	0;JMP

(END)
// Endless loop

	@END
	0;JMP