
// this command is C_FUNCTION
// original is: function SimpleFunction.test 2
(SimpleFunction.test)
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

// this command is C_ARITHMETIC
// original is: add
@SP
M=M-1
A=M
D=M
A=A-1
M=M+D

// this command is C_ARITHMETIC
// original is: not
@SP
A=M-1
M=!M

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

// this command is C_ARITHMETIC
// original is: add
@SP
M=M-1
A=M
D=M
A=A-1
M=M+D

// this command is C_POP or PUSH
// original is: push argument 1
@ARG
D=M
@1
A=D+A
D=M
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
@RETURN_ADDR0
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
@RETURN_ADDR0
A=M
0;JMP
