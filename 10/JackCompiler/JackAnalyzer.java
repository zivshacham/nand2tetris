import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Created by Ziv Shacham on January 2023
 */

/* 
 * The JackAnalyzer implements the Jack Analyzer, using the services of:
 *   • JackTokenizer
 *   • CompilationEngine
 * 
 * The top-most / main module
 *    Input: a single fileName.jack, or a folder containing 0 or more such files
 *    • For each file:
 *       1. Creates a JackTokenizer from fileName.jack
 *       2. Creates an output file named fileName.xml
 *       3. Creates a CompilationEngine, and calls the compileClass method.
 */
public class JackAnalyzer {
    public static void main(String[] args) {
        File fileInput = new File(args[0]);
        File fileOutput;
        // checking file directory validity
        if (args.length != 1) {
            throw new IllegalArgumentException(
                    "Illegal input format. Please enter in the following format: java JackAnalyzer (directory or fileInput.jack)");
        }
        // checking file format validity
        if (fileInput.isFile() && !(args[0].endsWith(".jack"))) {
            throw new IllegalArgumentException("File type is wrong. Please enter a .jack file.");
        }

        String fileNameOut = "";
        ArrayList<File> files = new ArrayList<>();
        if (fileInput.isFile() && args[0].endsWith(".jack")) {
            files.add(fileInput);
            fileNameOut = args[0].substring(0, args[0].length() - 5);
        } else if (fileInput.isDirectory()) {
            files = getJackFiles(fileInput);
            fileNameOut = args[0];
        }

        // creating the output xml file
        fileNameOut = fileNameOut + ".xml";
        fileOutput = new File(fileNameOut);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileOutput);
        } catch (IOException e) {
            System.out.println("An error occurred (writing to output file).");
        }
        for (File file : files) {
            String outputFileName = file.toString().substring(0, file.toString().length() - 5) + ".xml";
            File outputFile = new File(outputFileName);
            // compile the new files with the CompilationEngine
            CompilationEngine compilationEngine = new CompilationEngine(file, outputFile);
            compilationEngine.compileClass();
        }
        
        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred (closing output file).");
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
}
