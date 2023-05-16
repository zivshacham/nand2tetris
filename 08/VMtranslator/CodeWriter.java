import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Created by Ziv Shacham on January 2023
 */

public class CodeWriter {

    private FileWriter newFile;
    private String fileName = "";
    private int counter = 0;
    private int numLabels = 0;

    /**
     * Constructor / initializer(string):
     * Opens output file/stream and gets ready to write into it.
     * if can't open the file - a message is displayed.
     * 
     * @param fileName: string that represents the file name
     */
    public CodeWriter(File fileName) {
        try {
            newFile = new FileWriter(fileName);
            setFileName(fileName);
        } catch (IOException e) {
            System.out.println("Could not open file: " + fileName);
        }
        // writeInit();
    }

    // writing to the file the command type 
    public void writeComment(String cmdType) {
        String lineToWrite = "";

        lineToWrite += "\n// this command is " + cmdType;
        lineToWrite += "\n";

        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeComment)");
        }
    }

    // writing to the file the original command from the input file 
    public void writeOriginalCmd(String cmdType) {
        String lineToWrite = "";

        lineToWrite += "// original is: " + cmdType;
        lineToWrite += "\n";

        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeInit)");
        }
    }

    // informs code writer that translation of new VM file is started
    public void setFileName(File file) {
        fileName = file.getName();
    }

    public void writeInit() {
        String lineToWrite = "";

        lineToWrite += "@256\n";
        lineToWrite += "D=A\n";
        lineToWrite += "@SP\n";
        lineToWrite += "M=D\n";
        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeInit)");
        }
        writeCall("Sys.init", 0);
    }

    /**
     * writeArithmetic():
     * writes the assembly code that is the translation of the given arithmetic
     * command.
     * 
     * @currentCMD: the current command to write (vm command)
     */
    public void writeArithmetic(String currentCMD) {
        String lineToWrite = "";
        // add command
        if (currentCMD.equals("add")) {
            lineToWrite += getArithFormat1();
            lineToWrite += "M=M+D\n";
            // sub command
        } else if (currentCMD.equals("sub")) {
            lineToWrite += getArithFormat1();
            lineToWrite += "M=M-D\n";
            // neg command
        } else if (currentCMD.equals("neg")) {
            lineToWrite += "D=0\n";
            lineToWrite += "@SP\n";
            lineToWrite += "A=M-1\n";
            lineToWrite += "M=D-M\n";
            // eq command
        } else if (currentCMD.equals("eq")) {
            lineToWrite += getArithFormat1();
            lineToWrite += getArithFormat2("JNE");
            counter++;
            // gt command
        } else if (currentCMD.equals("gt")) {
            lineToWrite += getArithFormat1();
            lineToWrite += getArithFormat2("JLE");
            counter++;
            // lt command
        } else if (currentCMD.equals("lt")) {
            lineToWrite += getArithFormat1();
            lineToWrite += getArithFormat2("JGE");
            counter++;
            // and command
        } else if (currentCMD.equals("and")) {
            lineToWrite += getArithFormat1();
            lineToWrite += "M=M&D\n";
            // or command
        } else if (currentCMD.equals("or")) {
            lineToWrite += getArithFormat1();
            lineToWrite += "M=M|D\n";
            // not command
        } else if (currentCMD.equals("not")) {
            lineToWrite += "@SP\n";
            lineToWrite += "A=M-1\n";
            lineToWrite += "M=!M\n";
        }
        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeArithmetic)");
        }
    }

    /**
     * writePushPop():
     * writes the assembly code that is the translation of the given command, where
     * command is either C_PUSH or C_POP.
     * 
     * @currentCMD: the current command to write (vm command)
     * @cmdSegment: the segment of the command (arg1)
     * @cmdIndex: the index of the command (arg2)
     */
    public void writePushPop(String currentCMD, String cmdSegment, int cmdIndex) {
        String lineToWrite = "";
        // if it push command
        if (currentCMD.equals("C_PUSH")) {
            if (cmdSegment.equals("static")) {
                lineToWrite += "@" + fileName + cmdIndex + "\n";
                lineToWrite += "D=M\n";
                lineToWrite += "@SP\n";
                lineToWrite += "A=M\n";
                lineToWrite += "M=D\n";
                lineToWrite += "@SP\n";
                lineToWrite += "M=M+1\n";
                // lineToWrite += getPushFormat2(String.valueOf(16 + cmdIndex));
            } else if (cmdSegment.equals("this")) {
                lineToWrite += getPushFormat1("THIS", cmdIndex);
            } else if (cmdSegment.equals("local")) {
                lineToWrite += getPushFormat1("LCL", cmdIndex);

            } else if (cmdSegment.equals("argument")) {
                lineToWrite += getPushFormat1("ARG", cmdIndex);

            } else if (cmdSegment.equals("that")) {
                lineToWrite += getPushFormat1("THAT", cmdIndex);

            } else if (cmdSegment.equals("constant")) {
                lineToWrite += "@" + cmdIndex + "\n";
                lineToWrite += "D=A\n";
                lineToWrite += "@SP\n";
                lineToWrite += "A=M\n";
                lineToWrite += "M=D\n";
                lineToWrite += "@SP\n";
                lineToWrite += "M=M+1\n";
            } else if (cmdSegment.equals("pointer") && cmdIndex == 0) {
                lineToWrite += getPushFormat2("THIS");
            } else if (cmdSegment.equals("pointer") && cmdIndex == 1) {
                lineToWrite += getPushFormat2("THAT");
            } else if (cmdSegment.equals("temp")) {
                lineToWrite += getPushFormat1("R", cmdIndex + 5);
            }

            // if it pop command
        } else if (currentCMD.equals("C_POP")) {
            if (cmdSegment.equals("static")) {
                lineToWrite += "@" + fileName + cmdIndex + "\n";
                lineToWrite += "D=A\n";
                lineToWrite += "@R13\n";
                lineToWrite += "M=D\n";
                lineToWrite += "@SP\n";
                lineToWrite += "M=M-1\n";
                lineToWrite += "A=M\n";
                lineToWrite += "D=M\n";
                lineToWrite += "@R13\n";
                lineToWrite += "A=M\n";
                lineToWrite += "M=D\n";

                // lineToWrite += getPopFormat2(String.valueOf(16 + cmdIndex));
            } else if (cmdSegment.equals("this")) {
                lineToWrite += getPopFormat1("THIS", cmdIndex);
            } else if (cmdSegment.equals("local")) {
                lineToWrite += getPopFormat1("LCL", cmdIndex);

            } else if (cmdSegment.equals("argument")) {
                lineToWrite += getPopFormat1("ARG", cmdIndex);

            } else if (cmdSegment.equals("that")) {
                lineToWrite += getPopFormat1("THAT", cmdIndex);

            } else if (cmdSegment.equals("constant")) {
                lineToWrite += "@" + cmdIndex + "\n";
                lineToWrite += "D=A\n";
                lineToWrite += "@SP\n";
                lineToWrite += "A=M\n";
                lineToWrite += "M=D\n";
                lineToWrite += "@SP\n";
                lineToWrite += "M=M+1\n";
            } else if (cmdSegment.equals("pointer") && cmdIndex == 0) {
                lineToWrite += getPopFormat2("THIS");
            } else if (cmdSegment.equals("pointer") && cmdIndex == 1) {
                lineToWrite += getPopFormat2("THAT");
            } else if (cmdSegment.equals("temp")) {
                String s = "" + (cmdIndex + 5);
                lineToWrite += "@" + s + "\n";
                lineToWrite += "D=A\n";
                lineToWrite += "@R14\n";
                lineToWrite += "M=D\n";
                lineToWrite += "@SP\n";
                lineToWrite += "M=M-1\n";
                lineToWrite += "A=M\n";
                lineToWrite += "D=M\n";
                lineToWrite += "@R14\n";
                lineToWrite += "A=M\n";
                lineToWrite += "M=D\n";
            }
        }
        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writePushPop)");
        }
    }

    /**
     * writeLabel():
     * writes the assembly code that is the translation of the given label
     * command.
     * 
     * @label: the label name to write (vm command)
     */
    public void writeLabel(String label) {
        String lineToWrite = "";
        lineToWrite += "(" + label + ")\n";
        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeLabel)");
        }
    }

    /**
     * writeGoto():
     * writes the assembly code that is the translation of the given goto
     * command.
     * 
     * @label: the label name to go (vm command)
     */
    public void writeGoto(String label) {
        String lineToWrite = "";
        lineToWrite += "@";
        lineToWrite += label + "\n";
        lineToWrite += "0;JMP\n";
        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeGoto)");
        }
    }

    /**
     * writeIf():
     * writes the assembly code that is the translation of the given if-goto
     * command.
     * 
     * @label: the label name to go (vm command)
     */
    public void writeIf(String label) {
        String lineToWrite = "";
        lineToWrite += getArithFormat1();
        lineToWrite += "@";
        lineToWrite += label + "\n";
        lineToWrite += "D;JNE\n";
        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeIf)");
        }
    }

    /**
     * writeFunction():
     * writes the assembly code that is the translation of the given function
     * command.
     * 
     * @functionName: the function name to go (vm command)
     * @nVars: the number of args to the function (vm command)
     */
    public void writeFunction(String functionName, int nVars) {
        String lineToWrite = "";
        // (functionName) // function’s entry point (label)
        lineToWrite += "(" + functionName + ")\n";

        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeFunction)");
        }

        // repeat nVars times: // initializes nVars local variables
        for (int i = 0; i < nVars; i++) {
            // push 0 // to 0
            writePushPop("C_PUSH", "constant", 0);
        }
    }

    /**
     * writeCall():
     * writes the assembly code that is the translation of the given call
     * command.
     * 
     * @functionName: the function name to go (vm command)
     * @nVars: the number of args to the function (vm command)
     */
    public void writeCall(String functionName, int nVars) {
        // push retAddrLabel // Generates and pushes this label
        String returnLabel = "RETURN_LABEL" + numLabels;
        numLabels++;

        String lineToWrite = "";
        lineToWrite += "@" + returnLabel;
        lineToWrite += "\n";

        lineToWrite += "D=A\n";
        lineToWrite += "@SP\n";
        lineToWrite += "A=M\n";
        lineToWrite += "M=D\n";
        lineToWrite += "@SP\n";
        lineToWrite += "M=M+1\n";
        // push LCL // Saves the caller’s LCL
        lineToWrite += getPushFormat2("LCL");
        // push ARG // Saves the caller’s ARG
        lineToWrite += getPushFormat2("ARG");
        // push THIS // Saves the caller’s THIS
        lineToWrite += getPushFormat2("THIS");
        // push THAT // Saves the caller’s THAT
        lineToWrite += getPushFormat2("THAT");
        // ARG = SP – 5 – nArgs // Repositions ARG
        lineToWrite += "@SP\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@" + nVars + "\n";
        lineToWrite += "D=D-A\n";
        lineToWrite += "@5\n";
        lineToWrite += "D=D-A\n";
        lineToWrite += "@ARG\n";
        lineToWrite += "M=D\n";
        // LCL = SP // Repositions LCL
        lineToWrite += "@SP\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@LCL\n";
        lineToWrite += "M=D\n";
        // goto functionName // Transfers control to the callee
        lineToWrite += "@" + functionName + "\n";
        lineToWrite += "0;JMP\n";
        // (retAddrLabel)
        lineToWrite += "(" + returnLabel + ")\n";

        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeCall)");
        }
    }

    /**
     * writeReturn():
     * writes the assembly code that is the translation of the given return command.
     */
    public void writeReturn() {
        String lineToWrite = "";

        // endFrame = LCL // gets the address at the frame’s end
        lineToWrite += "@LCL\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@END_FRAME\n";
        lineToWrite += "M=D\n";
        // retAddr = *(endFrame – 5) // gets the return address
        lineToWrite += "@5\n";
        lineToWrite += "D=A\n";
        lineToWrite += "@END_FRAME\n";
        lineToWrite += "D=M-D\n";
        lineToWrite += "A=D\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@RETURN_ADDR" + numLabels + "\n";
        lineToWrite += "M=D\n";
        // *ARG = pop() // puts the return value for the caller
        lineToWrite += getPopFormat1("ARG", 0);
        lineToWrite += "@ARG\n";
        lineToWrite += "D=M+1\n";
        // SP = ARG + 1 // repositions SP
        lineToWrite += "@SP\n";
        lineToWrite += "M=D\n";
        // THAT = *(endFrame – 1) // restores THAT
        lineToWrite += "@END_FRAME\n";
        lineToWrite += "A=M-1\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@THAT\n";
        lineToWrite += "M=D\n";
        // THIS = *(endFrame – 2) // restores THIS
        lineToWrite += "@2\n";
        lineToWrite += "D=A\n";
        lineToWrite += "@END_FRAME\n";
        lineToWrite += "A=M-D\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@THIS\n";
        lineToWrite += "M=D\n";
        // ARG = *(endFrame – 3) // restores ARG
        lineToWrite += "@3\n";
        lineToWrite += "D=A\n";
        lineToWrite += "@END_FRAME\n";
        lineToWrite += "A=M-D\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@ARG\n";
        lineToWrite += "M=D\n";
        // LCL = *(endFrame – 4) // restores LCL
        lineToWrite += "@4\n";
        lineToWrite += "D=A\n";
        lineToWrite += "@END_FRAME\n";
        lineToWrite += "A=M-D\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@LCL\n";
        lineToWrite += "M=D\n";
        // goto retAddr // jumps to the return address
        lineToWrite += "@RETURN_ADDR" + numLabels + "\n";
        lineToWrite += "A=M\n";
        lineToWrite += "0;JMP\n";

        // try to write to the file
        try {
            newFile.write(lineToWrite);
        } catch (IOException e) {
            System.out.println("An error occurred. (writing to file - writeReturn)");
        }
    }

    /**
     * getArithFormat1():
     * predefined format for arithmetic command.
     * applied to all of the arithmetic command except for neg and not
     * 
     * @return: the string of the generic part of the translated command
     */
    public String getArithFormat1() {
        String arithFormatString = "";
        arithFormatString += "@SP\n";
        arithFormatString += "M=M-1\n";
        arithFormatString += "A=M\n";
        arithFormatString += "D=M\n";
        arithFormatString += "A=A-1\n";
        return arithFormatString;
    }

    /**
     * getArithFormat2():
     * the second predefined format for arithmetic command.
     * applied only for et, gt, and lt
     * 
     * @return: the second part of the generic string of the translated command
     */
    public String getArithFormat2(String strJump) {
        String arithFormatString = "";
        arithFormatString += "D=M-D\n";
        arithFormatString += "@FALSE" + counter + "\n";
        arithFormatString += "D;" + strJump + "\n";
        arithFormatString += "@SP\n";
        arithFormatString += "A=M-1\n";
        arithFormatString += "M=-1\n";
        arithFormatString += "@CONTINUE" + counter + "\n";
        arithFormatString += "0;JMP\n";
        arithFormatString += "(FALSE" + counter + ")\n";
        arithFormatString += "@SP\n";
        arithFormatString += "A=M-1\n";
        arithFormatString += "M=0\n";
        arithFormatString += "(CONTINUE" + counter + ")\n";
        return arithFormatString;
    }

    /**
     * getPushFormat1():
     * predefined format for push command.
     * applied only for this, that, local, argument, and temp.
     * 
     * @cmdSegment: the segment of the command (arg1)
     * @cmdIndex: the index of the command (arg2)
     * @return: the string of the generic part of the translated command
     */
    public String getPushFormat1(String cmdSegment, int cmdIndex) {
        String lineToWrite = "";
        lineToWrite += "@" + cmdSegment + "\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@" + cmdIndex + "\n";
        lineToWrite += "A=D+A\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@SP\n";
        lineToWrite += "A=M\n";
        lineToWrite += "M=D\n";
        lineToWrite += "@SP\n";
        lineToWrite += "M=M+1\n";
        return lineToWrite;
    }

    /**
     * getPushFormat2():
     * predefined format for push command.
     * applied only for static & pointer.
     * 
     * @cmdSegment: the segment of the command (arg1)
     * @return: the string of the generic part of the translated command
     */
    public String getPushFormat2(String cmdSegment) {
        String lineToWrite = "";
        lineToWrite += "@" + cmdSegment + "\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@SP\n";
        lineToWrite += "A=M\n";
        lineToWrite += "M=D\n";
        lineToWrite += "@SP\n";
        lineToWrite += "M=M+1\n";
        return lineToWrite;
    }

    /**
     * getPopFormat1():
     * predefined format for pop command.
     * applied only for this, that, local, argument, and temp.
     * 
     * @cmdSegment: the segment of the command (arg1)
     * @cmdIndex: the index of the command (arg2)
     * @return: the string of the generic part of the translated command
     */
    public String getPopFormat1(String cmdSegment, int cmdIndex) {
        String lineToWrite = "";
        lineToWrite += "@" + cmdSegment + "\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@" + cmdIndex + "\n";
        lineToWrite += "D=D+A\n";
        lineToWrite += "@R13\n";
        lineToWrite += "M=D\n";
        lineToWrite += "@SP\n";
        lineToWrite += "M=M-1\n";
        lineToWrite += "A=M\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@R13\n";
        lineToWrite += "A=M\n";
        lineToWrite += "M=D\n";
        return lineToWrite;
    }

    /**
     * getPopFormat2():
     * predefined format for pop command.
     * applied only for static & pointer.
     * 
     * @cmdSegment: the segment of the command (arg1)
     * @return: the string of the generic part of the translated command
     */
    public String getPopFormat2(String cmdSegment) {
        String lineToWrite = "";
        lineToWrite += "@" + cmdSegment + "\n";
        lineToWrite += "D=A\n";
        lineToWrite += "@R13\n";
        lineToWrite += "M=D\n";
        lineToWrite += "@SP\n";
        lineToWrite += "M=M-1\n";
        lineToWrite += "A=M\n";
        lineToWrite += "D=M\n";
        lineToWrite += "@R13\n";
        lineToWrite += "A=M\n";
        lineToWrite += "M=D\n";
        return lineToWrite;
    }

    /**
     * close():
     * closes output file.
     */
    public void close() {
        try {
            newFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}