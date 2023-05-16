import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Created by Ziv Shacham on January 2023
 */

/* 
 * The HackAssembler implements the assembly algorithm, using the services of:
 *   • Parser
 *   • Code
 *   • SymbolTable
 * 
 *  Initialize:
 * Opens the input file (Prog.asm) and gets ready to process it
 * Constructs a symbol table, and adds to it all the predefined symbols
 * 
 * First pass:
 * Reads the program lines, one by one focusing only on (label) declarations.
 * Adds the found labels to the symbol table
 * 
 * Second pass (main loop):
 * (starts again from the beginning of the file)
 * While there are more lines to process:
 * Gets the next instruction, and parses it
 *      If the instruction is @ symbol
 *      If symbol is not in the symbol table, adds it
 * Translates the symbol into its binary value
 *      If the instruction is dest =comp ; jump
 * Translates each of the three fields into its binary value
 * Assembles the binary values into a string of sixteen 0’s and 1’s
 * Writes the string to the output file.
 */

public class HackAssembler {

    // Opens the input file (xxx.asm) and gets ready to process it
    // Constructs a symbol table, and adds to it all the predefined symbols
    public static void main(String[] args) throws IOException {
        // Preparations - initializing parser, symbolTable and code.
        if (!inputValidity(args[0])) {
            System.out.println("file name is not valid");
            return;
        }
        Parser parser = new Parser(args[0]);
        // SymbolTable symbolTable = new SymbolTable("symbols.txt");
        SymbolTable symbolTable = new SymbolTable();
        Code code = new Code();

        try {
            parser.hasMoreLines();
        } catch (NullPointerException e) {
            return;
        }

        // First pass
        int counter = 0;
        // counter = firstPass(parser, symbolTable, counter);
        while (parser.hasMoreLines()) {
            if (parser.isValidLine()) {
                counter = firstPass(parser, symbolTable, counter);
            }
            parser.advance();
        }

        // firstPass(parser, symbolTable, counter);

        // Second pass
        parser = new Parser(args[0]);
        String newFileName = newHackName(args[0]);
        // try to create new file
        try {
            File outputFile = new File(newFileName);
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            return;
        }

        counter = 0;
        boolean isFirstLine = true;
        String lineToWrite = "";
        // try to write to the new file
        try {
            FileWriter myWriter = new FileWriter(newFileName);
            // start reading the file for second pass
            while (parser.hasMoreLines()) {
                if (parser.isValidLine()) {
                    lineToWrite = secondPass(parser, symbolTable, code, counter);
                    if (lineToWrite != null) {
                        if (isFirstLine) {
                            isFirstLine = false;
                        } else {
                            myWriter.write("\n");
                        }
                        myWriter.write(lineToWrite);
                    }
                    counter++;
                }
                parser.advance();
            }
            // lineToWrite = secondPass(parser, symbolTable, code, counter);
            // if (lineToWrite != null) {
            // myWriter.write(lineToWrite);
            // }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            return;
        }
    }

    /**
     * firstPass(string):
     * this function is used to handle with the "first pass" through the file.
     * it read the line from the file and if its an L instruction
     * it adds it to the symbolTable.
     * 
     * @param parser:      instance of Parser class
     * @param symbolTable: the symbolTable for that is used in this program
     * @param counter:     counter for iterating the lines
     * @return: increases the line number counter - only if the line isn't L
     *          instruction
     */
    public static int firstPass(Parser parser, SymbolTable symbolTable, int counter) {
        if (parser.instructionType() == "L_INSTRUCTION") {
            String newSymbol = parser.symbol();
            symbolTable.addEntry(newSymbol, counter);
            return counter;
        }
        return counter + 1;
    }

    /**
     * secondPass(string):
     * this function is used to handle with the "second pass" through the file.
     * it read the line from the file and
     * translates into "lineToWrite" in the output file.
     * 
     * @param parser:      instance of Parser class
     * @param symbolTable: the symbolTable for that is used in this program
     * @param code:        instance of Code class
     * @param counter:     counter for iterating the lines
     * @return: a string which represent the new name for the output file
     */
    public static String secondPass(Parser parser, SymbolTable symbolTable, Code code, int counter) {
        if (!parser.isValidLine())
            return null;
        String newSymbol = "";
        String lineToWrite = "";
        if (parser.instructionType() == "L_INSTRUCTION") {
            return null;
        }
        // if the command is @
        if (parser.instructionType() == "A_INSTRUCTION") {
            newSymbol = parser.symbol();
            if (symbolTable.contains(newSymbol)) {
                lineToWrite = code.to16BitBinary(symbolTable.getAddress(newSymbol));
            } else {
                // if it's a variable
                try {
                    lineToWrite = code.to16BitBinary(Integer.parseInt(newSymbol));
                } catch (NumberFormatException e) {
                    symbolTable.addEntry(newSymbol);
                    lineToWrite = code.to16BitBinary(symbolTable.getAddress(newSymbol));
                }
            }
            return lineToWrite;
        }
        // If the instruction is dest=comp;jump
        String destCode = parser.dest();
        destCode = code.dest(destCode);
        String compCode = parser.comp();
        String aCode = "0";
        if (compCode.contains("M"))
            aCode = "1";
        compCode = code.comp(compCode);
        String jumpCode = parser.jump();
        jumpCode = code.jump(jumpCode);
        lineToWrite += "111";
        lineToWrite += aCode;
        lineToWrite += compCode;
        lineToWrite += destCode;
        lineToWrite += jumpCode;
        return lineToWrite;
    }

    /**
     * newHackName(string):
     * Returns a string in the format of "fileName.hack"
     * 
     * @return: a string which represent the new name for the output file
     */
    public static String newHackName(String fileName) {
        int dot_index = fileName.indexOf(".");
        return fileName.substring(0, dot_index) + ".hack";
    }

    public static boolean inputValidity(String fileName) {
        int dot_index = fileName.indexOf(".");
        if (dot_index == -1)
            return false;
        String filaNameExtension = fileName.substring(dot_index + 1);
        if (filaNameExtension.equals("asm"))
            return true;
        return false;
    }
}