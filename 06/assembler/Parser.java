import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * Created by Ziv Shacham on January 2023
 */

public class Parser {

    // file: the file to read from
    private File file;
    // scanner: the scanner which go through the file to read (file)
    private BufferedReader reader;
    // currentInst: the string which represent the current instruction from the file
    private String currentInst;

    private boolean atLastLine;

    /**
     * Constructor / initializer(string):
     * Creates a Parser and opens the source text file.
     * if can't open the file - a message is displayed.
     * 
     * @param fileName: string that represents the file name
     */
    public Parser(String fileName) {
        try {
            // try to read from file
            file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
            currentInst = reader.readLine();
            atLastLine = false;
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
        if (reader.ready())
            return true;
        if (currentInst != "" && currentInst.charAt(0) != '/') {
            atLastLine = true;
            return true;
        }
        return false;
    }

    /**
     * advance():
     * Gets the next instruction from the next line in the file
     * and makes it the current instruction (currentInst).
     * 
     * @throws IOException
     */
    public void advance() throws IOException {
        if (atLastLine) {
            currentInst = "";
            return;
        }
        currentInst = reader.readLine();
        currentInst = currentInst.replace(" ", "");
        currentInst = currentInst.replaceAll("/.*", "");
        // int front_slash = currentInst.indexOf("/");
        // if(front_slash != -1) {
        // currentInst = currentInst.substring(0, front_slash);
        // }

    }

    /**
     * isValidLine():
     * Checks if the current line (currentInst) is valid.
     * 
     */
    public boolean isValidLine() {
        currentInst = currentInst.replace(" ", "");
        currentInst = currentInst.replaceAll("/.*", "");
        // if (currentInst == "" || currentInst.charAt(0) == '/')
        if (currentInst.equals("") || currentInst == null)
            return false;
        // if (currentInst.charAt(0) == '/') return false;
        return true;
    }

    /**
     * instructionType():
     * Returns the instruction type .
     * 
     * @return: String which represents the instruction type.
     */
    public String instructionType() {
        if (currentInst.length() <= 0) {
            return "NULL";
        }
        if (currentInst.charAt(0) == '@')
            return "A_INSTRUCTION";
        if (currentInst.charAt(0) == '(')
            return "L_INSTRUCTION";
        return "C_INSTRUCTION";
    }

    /**
     * symbol:
     * Returns the instruction’s symbol.
     * 
     * @return the string which represents the instruction's symbol
     */
    public String symbol() {
        if (currentInst.charAt(0) == '@')
            return currentInst.substring(1);
        return currentInst.substring(1, currentInst.length() - 1);
    }

    /**
     * dest:
     * Returns the instruction’s dest field.
     * 
     * @return the string which represents the dest field
     */
    public String dest() {
        int equal_sign = currentInst.indexOf("=");
        if (equal_sign == -1)
            return null;
        return currentInst.substring(0, equal_sign);
    }

    /**
     * comp:
     * Returns the instruction’s comp field.
     * 
     * @return the string which represents the comp field
     */
    public String comp() {
        int equal_sign = currentInst.indexOf("=");
        if (currentInst.contains(";")) {
            int semi_colon = currentInst.indexOf(";");
            return currentInst.substring(equal_sign + 1, semi_colon);
        }
        String cmd = currentInst.substring(equal_sign + 1);
        // delete extra spaces from the end
        int first_space = cmd.indexOf(" ");
        if (first_space > -1) {
            return cmd.substring(0, first_space);
        }
        return cmd;
    }

    /**
     * jump:
     * Returns the instruction’s jump field.
     * 
     * @return the string which represents the jump command
     */
    public String jump() {
        if (currentInst.contains(";")) {
            int semi_colon = currentInst.indexOf(";");
            String cmd = currentInst.substring(semi_colon + 1);
            // delete extra spaces from the end
            int first_space = cmd.indexOf(" ");
            if (first_space > -1) {
                return cmd.substring(0, first_space);
            }
            return cmd;
        }
        return null;
    }
}
