import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Created by Ziv Shacham on January 2023
 */

/* 
 * The JackAnalyzer implements the Jack Compiler, using the services of:
 *   • JackTokenizer
 *   • CompilationEngine
 *   • SymbolTable
 *   • Symbol
 *   • VMWriter
 * 
 * The top-most / main module
 *    Input: a single fileName.jack, or a folder containing 0 or more such files
 *    • For each file:
 *       1. Creates a JackTokenizer from fileName.jack
 *       2. Creates an output file named fileName.vm
 *       3. Creates a CompilationEngine, and calls the compileClass method.
 */
public class JackCompiler {
    public static void main(String[] args) throws IOException {
        File fileInput = new File(args[0]);
        // checking file directory validity
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Illegal input format. Please enter in the following format: java JackCompiler (directory or fileInput.jack)");
        }
        // checking file format validity
        if (fileInput.isFile() && !(args[0].endsWith(".jack"))) {
            throw new IllegalArgumentException("File type is wrong. Please enter a .jack file.");
        }

        ArrayList<File> filesArray = new ArrayList<>();
        if (fileInput.isFile() && args[0].endsWith(".jack")) {
            filesArray.add(fileInput);
        } else if (fileInput.isDirectory()) {
            filesArray = getJackFiles(fileInput);
        }

        for (File file : filesArray) {
            String outputName = newVMName(file.getPath());
            File outputFile = new File(outputName);
            // compile the new files with the CompilationEngine
            CompilationEngine compilationEngine = new CompilationEngine(file, outputFile);
            compilationEngine.compileClass();
        }
    }

    /**
     * getJackFiles():
     * get all of the jack files from the given directory
     * 
     * @param fileInput: the given file directory
     * @return a arrayList of the jack files in the directory
     */
    //
    public static ArrayList<File> getJackFiles(File fileInput) {
        File[] files = fileInput.listFiles();
        ArrayList<File> filesList = new ArrayList<>();
        if (files != null)
            for (File file : files) {
                if (file.getName().endsWith(".jack"))
                    filesList.add(file);
            }
        return filesList;
    }

    /**
     * newVMName(string):
     * Returns a string in the format of "fileInput.vm"
     * 
     * @return: a string which represent the new name for the output file
     */
    public static String newVMName(String fileInput) {
        int dot_index = fileInput.lastIndexOf(".");
        return fileInput.substring(0, dot_index) + ".vm";
    }
}
