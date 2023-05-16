import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * Created by Ziv Shacham on January 2023
 */

public class Parser {
    private BufferedReader reader;
    private String currentCMD = null;
    private String field_arg0 = null;
    private String field_arg1 = null;
    private String field_arg2 = null;
    private String cmdType = null;

    /**
     * Constructor / initializer(string):
     * Creates a Parser and opens the source text file.
     * if can't open the file - a message is displayed.
     * 
     * @param fileName: string that represents the file name
     */
    public Parser(File fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            System.out.println("Could not open file: " + fileName);
        }

    }

    /**
     * hasMoreLines():
     * Checks if there is more work to do.
     * 
     * @return: boolean value.
     * @throws IOException
     */
    public boolean hasMoreLines() throws IOException {
        if (reader.ready()) {
            return true;
        }
        return false;
    }

    /**
     * advance():
     * Gets the next instruction from the next line in the file
     * and makes it the current instruction (currentInst).
     * In addition, it is updating the following fields according to the cmd:
     * field_arg1, field_arg1, field_arg2, cmdType
     * 
     * @throws IOException
     */
    public void advance() throws IOException {
        String strLine = reader.readLine();
        while (strLine.equals("") || hasComments(strLine)) {
            if (hasComments(strLine)) {
                strLine = removeComments(strLine);
            }
            if (strLine.trim().equals("")) {
                strLine = reader.readLine();
            }
        }

        currentCMD = strLine;
        String[] argsCMD = currentCMD.split(" ");
        field_arg0 = argsCMD[0];
        if (argsCMD.length > 1) {
            field_arg1 = argsCMD[1];
        } else field_arg1 = "";
        if (argsCMD.length > 2) {
            field_arg2 = argsCMD[2];
        } else field_arg2 = "";

        if (field_arg0.equals("push")) {
            cmdType = "C_PUSH";
        } else {
            if (field_arg0.equals("pop")) {
                cmdType = "C_POP";
            } else cmdType = "C_ARITHMETIC";
        }
    }

    /**
     * hasComments():
     * checks whether is a comments it the current line
     * 
     * @param strLine: the current line
     */
    private boolean hasComments(String strLine) {
        if (strLine.contains("//")) {
            return true;
        }
        return false;
    }

    /**
     * removeComments():
     * remove the comments from the current line and
     * returns the new line without the comments
     * 
     * @param strLine: the current line
     * @return the same line without comments
     */
    private String removeComments(String strLine) {
        String strNoComments = strLine;
        if (hasComments(strLine)) {
            int offSet = strLine.indexOf("//");
            strNoComments = strLine.substring(0, offSet).trim();
        }
        return strNoComments;
    }

    /**
     * commandType():
     * Returns the command type .
     * 
     * @return: String which represents the instruction type.
     */
    public String commandType() {
        return cmdType;
    }

    /**
     * arg1():
     * Returns first argument of current command.
     * in case of C_ARITHMETIC the command itself (add, sub, etc) is returned
     * should not be called if current command is C_RETURN
     * 
     * @return the string which represents the field_arg1 field
     */
    public String arg1() {
        if (cmdType.equals("C_ARITHMETIC")) {
            return field_arg0;
        }
        return field_arg1;
    }

    /**
     * arg2():
     * Returns second argument of current command.
     * should be called only if current command is C_PUSH, C_POP, C_FUNCTION, or C_CALL
     * 
     * @return the integer which represents the field_arg2 field
     */
    public int arg2() {
        return Integer.parseInt(field_arg2);
    }
}