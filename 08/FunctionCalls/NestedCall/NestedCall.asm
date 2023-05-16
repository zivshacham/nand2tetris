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
// original is: function Sys.init 0
(Sys.init)

// this command is C_POP or PUSH
// original is: push constant 4000
@4000
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop pointer 0
@THIS
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D

// this command is C_POP or PUSH
// original is: push constant 5000
@5000
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop pointer 1
@THAT
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D

// this command is C_CALL
// original is: call Sys.main 0
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
@Sys.main
0;JMP
(RETURN_LABEL1)

// this command is C_POP or PUSH
// original is: pop temp 1
@6
D=A
@R14
M=D
@SP
M=M-1
A=M
D=M
@R14
A=M
M=D

// this command is C_LABEL
// original is: label LOOP
(LOOP)

// this command is C_GOTO
// original is: goto LOOP
@LOOP
0;JMP

// this command is C_FUNCTION
// original is: function Sys.main 5
(Sys.main)
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
@0
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push constant 4001
@4001
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop pointer 0
@THIS
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D

// this command is C_POP or PUSH
// original is: push constant 5001
@5001
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop pointer 1
@THAT
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D

// this command is C_POP or PUSH
// original is: push constant 200
@200
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop local 1
@LCL
D=M
@1
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

// this command is C_POP or PUSH
// original is: push constant 40
@40
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop local 2
@LCL
D=M
@2
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

// this command is C_POP or PUSH
// original is: push constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop local 3
@LCL
D=M
@3
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

// this command is C_POP or PUSH
// original is: push constant 123
@123
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_CALL
// original is: call Sys.add12 1
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
@Sys.add12
0;JMP
(RETURN_LABEL2)

// this command is C_POP or PUSH
// original is: pop temp 0
@5
D=A
@R14
M=D
@SP
M=M-1
A=M
D=M
@R14
A=M
M=D

// this command is C_POP or PUSH
// original is: push local 0
@LCL
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
// original is: push local 1
@LCL
D=M
@1
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push local 2
@LCL
D=M
@2
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push local 3
@LCL
D=M
@3
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push local 4
@LCL
D=M
@4
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_ARITHMETIC
// original is: add
@SP
M=M-1
A=M
D=M
A=A-1
M=M+D

// this command is C_ARITHMETIC
// original is: add
@SP
M=M-1
A=M
D=M
A=A-1
M=M+D

// this command is C_ARITHMETIC
// original is: add
@SP
M=M-1
A=M
D=M
A=A-1
M=M+D

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
// original is: function Sys.add12 0
(Sys.add12)

// this command is C_POP or PUSH
// original is: push constant 4002
@4002
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop pointer 0
@THIS
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D

// this command is C_POP or PUSH
// original is: push constant 5002
@5002
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: pop pointer 1
@THAT
D=A
@R13
M=D
@SP
M=M-1
A=M
D=M
@R13
A=M
M=D

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
// original is: push constant 12
@12
D=A
@SP
A=M
M=D
@SP
M=M+1

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
