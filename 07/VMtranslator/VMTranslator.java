import java.io.File;
import java.io.IOException;

/*
 * Created by Ziv Shacham on January 2023
 */

/* 
 * The VMTranslator implements the VM translator, using the services of:
 *   • Parser
 *   • CodeWriter
 * 
 * General design:
 *   • Parser: parses each VM command into its lexical elements
 *   • CodeWriter: writes the assembly code that implements the parsed command 
 *   • Main: drives the process (VMTranslator)
 *          Input: fileInput.vm
 *          Output: fileInput.asm
 * 
 * Main logic:
 *   • Constructs a Parser to handle the input file
 *   • Constructs a CodeWriter to handle the output file
 *   • Marches through the input file, parsing each line and generating code from it.
 * 
 */
public class VMTranslator {
    public static void main(String[] args) throws IOException {
        File fileInput = new File(args[0]);
        File fileOutput;
        // checking file directory validity
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Illegal input format. Please enter in the following format: java VMTranslator (directory/fileInput)");
        }
        // checking file format validity
        if (fileInput.isFile() && !(args[0].endsWith(".vm"))) {
            throw new IllegalArgumentException("File type is wrong. Please enter a .vm file.");
        }

        fileOutput = new File(newAsmName(args[0]));
        // construct parsers to parse VM input files
        Parser parser = new Parser(fileInput);

        // construct CodeWriter to generate code into corresponding output file
        CodeWriter codeWriter = new CodeWriter(fileOutput);

        // go through VM commands in input file, translate in to assembly code
        while (parser.hasMoreLines()) {
            parser.advance();
            if (parser.commandType().equals("C_ARITHMETIC")) {
                codeWriter.writeArithmetic(parser.arg1());
            } else if (parser.commandType().equals("C_PUSH") || parser.commandType().equals("C_POP")) {
                codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
            }
        }
        codeWriter.close();
    }

    /**
     * newASMName(string):
     * Returns a string in the format of "fileInput.asm"
     * 
     * @return: a string which represent the new name for the output file
     */
    public static String newAsmName(String fileInput) {
        int dot_index = fileInput.lastIndexOf(".");
        return fileInput.substring(0, dot_index) + ".asm";
    }
}