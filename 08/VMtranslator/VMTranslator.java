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
                    "Illegal input format. Please enter in the following format: java VMTranslator (directory or fileInput.vm)");
        }
        // checking file format validity
        if (fileInput.isFile() && !(args[0].endsWith(".vm"))) {
            throw new IllegalArgumentException("File type is wrong. Please enter a .vm file.");
        }

        File[] filesArray;
        // creating files array and the name of the output file
        if (fileInput.isDirectory()) {
            filesArray = fileInput.listFiles();
            fileOutput = new File(args[0] + "/" + fileInput.getName() + ".asm");
        } else {
            filesArray = new File[1];
            filesArray[0] = fileInput;
            fileOutput = new File(newAsmName(args[0]));
        }
        // // for debugging purposes - printing the files in the directory
        // for (int i = 0; i < filesArray.length; i++) {
        //     System.out.println(filesArray[i].getName());
        // }
        // construct CodeWriter to generate code into corresponding output file
        CodeWriter codeWriter = new CodeWriter(fileOutput);
        boolean isSysExist = false;
        for (int i = 0; i < filesArray.length; i++) {
            if (filesArray[i].getName().equals("Sys.vm")) {
                isSysExist = true;
            }
        }
        if (fileInput.isDirectory() && isSysExist == true) {
            codeWriter.writeInit();
        }

        for (int i = 0; i < filesArray.length; i++) {
            if (filesArray[i].getName().endsWith(".vm")) {

                // construct parsers for every vm file in the files array 
                Parser parser = new Parser(filesArray[i]);
                
                codeWriter.setFileName(filesArray[i]);
                
                // go through VM commands in input file, translate in to assembly code
                while (parser.hasMoreLines()) {
                    parser.advance();
                    if (parser.commandType().equals("C_ARITHMETIC")) {
                        codeWriter.writeComment("C_ARITHMETIC");
                        codeWriter.writeOriginalCmd(parser.currentCMD);
                        codeWriter.writeArithmetic(parser.arg1());
                    } else if (parser.commandType().equals("C_PUSH") || parser.commandType().equals("C_POP")) {
                        codeWriter.writeComment("C_POP or PUSH");
                        codeWriter.writeOriginalCmd(parser.currentCMD);
                        codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
                    } else if (parser.commandType().equals("C_LABEL")) {
                        codeWriter.writeComment("C_LABEL");
                        codeWriter.writeOriginalCmd(parser.currentCMD);
                        codeWriter.writeLabel(parser.arg1());

                    } else if (parser.commandType().equals("C_GOTO")) {
                        codeWriter.writeComment("C_GOTO");
                        codeWriter.writeOriginalCmd(parser.currentCMD);
                        codeWriter.writeGoto(parser.arg1());

                    } else if (parser.commandType().equals("C_IF")) {
                        codeWriter.writeComment("C_IF");
                        codeWriter.writeOriginalCmd(parser.currentCMD);
                        codeWriter.writeIf(parser.arg1());

                    } else if (parser.commandType().equals("C_FUNCTION")) {
                        codeWriter.writeComment("C_FUNCTION");
                        codeWriter.writeOriginalCmd(parser.currentCMD);
                        codeWriter.writeFunction(parser.arg1(), parser.arg2());

                    } else if (parser.commandType().equals("C_CALL")) {
                        codeWriter.writeComment("C_CALL");
                        codeWriter.writeOriginalCmd(parser.currentCMD);
                        codeWriter.writeCall(parser.arg1(), parser.arg2());

                    } else {
                        codeWriter.writeComment("C_RETURN");
                        codeWriter.writeOriginalCmd(parser.currentCMD);
                        codeWriter.writeReturn();

                    }
                    // codeWriter.writeDebug();
                }
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