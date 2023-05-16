@256
D=A
@SP
M=D
@RETURN_LABEL0
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
D=M
@0
D=D-A
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(RETURN_LABEL0)

// this command is C_FUNCTION
// original is: function Main.fibonacci 0
(Main.fibonacci)

// this command is C_POP or PUSH
// original is: push argument 0
@ARG
D=M
@0
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push constant 2
@2
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_ARITHMETIC
// original is: lt
@SP
M=M-1
A=M
D=M
A=A-1
D=M-D
@FALSE0
D;JGE
@SP
A=M-1
M=-1
@CONTINUE0
0;JMP
(FALSE0)
@SP
A=M-1
M=0
(CONTINUE0)

// this command is C_IF
// original is: if-goto IF_TRUE
@SP
M=M-1
A=M
D=M
A=A-1
@IF_TRUE
D;JNE

// this command is C_GOTO
// original is: goto IF_FALSE
@IF_FALSE
0;JMP

// this command is C_LABEL
// original is: label IF_TRUE
(IF_TRUE)

// this command is C_POP or PUSH
// original is: push argument 0        
@ARG
D=M
@0
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_RETURN
// original is: return
@LCL
D=M
@END_FRAME
M=D
@5
D=A
@END_FRAME
D=M-D
A=D
D=M
@RETURN_ADDR1
M=D
@ARG
D=M
@0
D=D+A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
@ARG
D=M+1
@SP
M=D
@END_FRAME
A=M-1
D=M
@THAT
M=D
@2
D=A
@END_FRAME
A=M-D
D=M
@THIS
M=D
@3
D=A
@END_FRAME
A=M-D
D=M
@ARG
M=D
@4
D=A
@END_FRAME
A=M-D
D=M
@LCL
M=D
@RETURN_ADDR1
A=M
0;JMP

// this command is C_LABEL
// original is: label IF_FALSE
(IF_FALSE)

// this command is C_POP or PUSH
// original is: push argument 0
@ARG
D=M
@0
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push constant 2
@2
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_ARITHMETIC
// original is: sub
@SP
M=M-1
A=M
D=M
A=A-1
M=M-D

// this command is C_CALL
// original is: call Main.fibonacci 1
@RETURN_LABEL1
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
D=M
@1
D=D-A
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(RETURN_LABEL1)

// this command is C_POP or PUSH
// original is: push argument 0
@ARG
D=M
@0
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push constant 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_ARITHMETIC
// original is: sub
@SP
M=M-1
A=M
D=M
A=A-1
M=M-D

// this command is C_CALL
// original is: call Main.fibonacci 1
@RETURN_LABEL2
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
D=M
@1
D=D-A
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(RETURN_LABEL2)

// this command is C_ARITHMETIC
// original is: add
@SP
M=M-1
A=M
D=M
A=A-1
M=M+D

// this command is C_RETURN
// original is: return
@LCL
D=M
@END_FRAME
M=D
@5
D=A
@END_FRAME
D=M-D
A=D
D=M
@RETURN_ADDR3
M=D
@ARG
D=M
@0
D=D+A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D
@ARG
D=M+1
@SP
M=D
@END_FRAME
A=M-1
D=M
@THAT
M=D
@2
D=A
@END_FRAME
A=M-D
D=M
@THIS
M=D
@3
D=A
@END_FRAME
A=M-D
D=M
@ARG
M=D
@4
D=A
@END_FRAME
A=M-D
D=M
@LCL
M=D
@RETURN_ADDR3
A=M
0;JMP

// this command is C_FUNCTION
// original is: function Sys.init 0
(Sys.init)

// this command is C_POP or PUSH
// original is: push constant 4
@4
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_CALL
// original is: call Main.fibonacci 1
@RETURN_LABEL3
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@SP
D=M
@1
D=D-A
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(RETURN_LABEL3)

// this command is C_LABEL
// original is: label WHILE
(WHILE)

// this command is C_GOTO
// original is: goto WHILE
@WHILE
0;JMP
