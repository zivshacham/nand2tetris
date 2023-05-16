import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Created by Ziv Shacham on January 2023
 */

public class VMWriter {
    private FileWriter fileWriter;

    public VMWriter(File fileName) {
        try {
            fileWriter = new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println("Could not open file: " + fileName);
        }
    }

    // writes a VM push command
    public void writePush(String strSegment, int nIndex) {
        if (strSegment.equals("var")) {
            strSegment = "local";
        }
        if (strSegment.equals("field")) {
            strSegment = "this";
        }
        try {
            fileWriter.write("push " + strSegment + " " + nIndex + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // writes a VM pop command
    public void writePop(String strSegment, int nIndex) {
        if (strSegment.equals("var")) {
            strSegment = "local";
        }
        if (strSegment.equals("field")) {
            strSegment = "this";
        }
        try {
            fileWriter.write("pop " + strSegment + " " + nIndex + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes a VM arithmetic command
    public void writeArithmetic(String strCommand) {
        try {
            fileWriter.write(strCommand + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes a VM label command
    public void writeLabel(String strLabel) {
        try {
            fileWriter.write("label " + strLabel + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes a VM goto command
    public void writeGoto(String strLabel) {
        try {
            fileWriter.write("goto " + strLabel + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes a VM if-goto command
    public void writeIf(String strLabel) {
        try {
            fileWriter.write("if-goto " + strLabel + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes a VM call command
    public void writeCall(String strName, int nArgs) {
        try {
            fileWriter.write("call " + strName + " " + nArgs + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes a VM function command
    public void writeFunction(String strName, int nLocals) {
        try {
            fileWriter.write("function " + strName + " " + nLocals + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // writes a VM return command
    public void writeReturn() {
        try {
            fileWriter.write("return\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // closes the output file
    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
