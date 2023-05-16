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
// original is: function Class1.set 0
(Class1.set)

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
// original is: pop static 0
@Class1.vm0
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

// this command is C_POP or PUSH
// original is: pop static 1
@Class1.vm1
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
// original is: push constant 0
@0
D=A
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

// this command is C_FUNCTION
// original is: function Class1.get 0
(Class1.get)

// this command is C_POP or PUSH
// original is: push static 0
@Class1.vm0
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push static 1
@Class1.vm1
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

// this command is C_FUNCTION
// original is: function Sys.init 0
(Sys.init)

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
// original is: push constant 8
@8
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_CALL
// original is: call Class1.set 2
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
@2
D=D-A
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.set
0;JMP
(RETURN_LABEL1)

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
// original is: push constant 23
@23
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push constant 15
@15
D=A
@SP
A=M
M=D
@SP
M=M+1

// this command is C_CALL
// original is: call Class2.set 2
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
@2
D=D-A
@5
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.set
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

// this command is C_CALL
// original is: call Class1.get 0
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
@Class1.get
0;JMP
(RETURN_LABEL3)

// this command is C_CALL
// original is: call Class2.get 0
@RETURN_LABEL4
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
@Class2.get
0;JMP
(RETURN_LABEL4)

// this command is C_LABEL
// original is: label WHILE
(WHILE)

// this command is C_GOTO
// original is: goto WHILE
@WHILE
0;JMP

// this command is C_FUNCTION
// original is: function Class2.set 0
(Class2.set)

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
// original is: pop static 0
@Class2.vm0
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

// this command is C_POP or PUSH
// original is: pop static 1
@Class2.vm1
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
// original is: push constant 0
@0
D=A
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
@RETURN_ADDR5
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
@RETURN_ADDR5
A=M
0;JMP

// this command is C_FUNCTION
// original is: function Class2.get 0
(Class2.get)

// this command is C_POP or PUSH
// original is: push static 0
@Class2.vm0
D=M
@SP
A=M
M=D
@SP
M=M+1

// this command is C_POP or PUSH
// original is: push static 1
@Class2.vm1
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
@RETURN_ADDR5
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
@RETURN_ADDR5
A=M
0;JMP
