import java.util.Hashtable;

/*
 * Created by Ziv Shacham on January 2023
 */

public class Code {

    // destTable: Hash table to "translate" dest codes
    private Hashtable<String, String> destTable = new Hashtable<String, String>();
    // jumpTable: Hash table to "translate" jump codes
    private Hashtable<String, String> jumpTable = new Hashtable<String, String>();
    // compTable: Hash table to "translate" comp codes
    private Hashtable<String, String> compTable = new Hashtable<String, String>();

    public Code() {
        // initializing the destTable table
        destTable.put("M", "001");
        destTable.put("D", "010");
        destTable.put("MD", "011");
        destTable.put("DM", "011");
        destTable.put("A", "100");
        destTable.put("MA", "101");
        destTable.put("AM", "101");
        destTable.put("DA", "110");
        destTable.put("AD", "110");
        destTable.put("AMD", "111");
        destTable.put("ADM", "111");
        destTable.put("DAM", "111");
        destTable.put("DMA", "111");
        destTable.put("MAD", "111");
        destTable.put("MDA", "111");

        // initializing the jumpTable table
        jumpTable.put("JGT", "001");
        jumpTable.put("JEQ", "010");
        jumpTable.put("JGE", "011");
        jumpTable.put("JLT", "100");
        jumpTable.put("JNE", "101");
        jumpTable.put("JLE", "110");
        jumpTable.put("JMP", "111");

        // initializing the compTable table
        compTable.put("0", "101010");
        compTable.put("1", "111111");
        compTable.put("-1", "111010");
        compTable.put("D", "001100");
        compTable.put("A", "110000");
        compTable.put("!D", "001101");
        compTable.put("!A", "110001");
        compTable.put("-D", "001111");
        compTable.put("-A", "110011");
        compTable.put("D+1", "011111");
        compTable.put("A+1", "110111");
        compTable.put("D-1", "001110");
        compTable.put("A-1", "110010");
        compTable.put("D+A", "000010");
        compTable.put("D-A", "010011");
        compTable.put("A-D", "000111");
        compTable.put("D&A", "000000");
        compTable.put("D|A", "010101");
        compTable.put("M", "110000");
        compTable.put("!M", "110001");
        compTable.put("-M", "110011");
        compTable.put("M+1", "110111");
        compTable.put("M-1", "110010");
        compTable.put("D+M", "000010");
        compTable.put("D-M", "010011");
        compTable.put("M-D", "000111");
        compTable.put("D&M", "000000");
        compTable.put("D|M", "010101");
    }

    /**
     * dest(string):
     * Returns the binary representation of the parsed dest field.
     * 
     * @param cmd: string that represents the code
     * @return: the binary code of the command
     */
    public String dest(String cmd) {
        if (cmd == null)
            return "000";
        return destTable.get(cmd);
    }

    /**
     * comp(string):
     * Returns the binary representation of the parsed comp field.
     * 
     * @param cmd: string that represents the code
     * @return: the binary code of the command
     */
    public String comp(String cmd) {
        return compTable.get(cmd);
    }

    /**
     * jump(string):
     * Returns the binary representation of the parsed jump field.
     * 
     * @param cmd: string that represents the code
     * @return: the binary code of the command
     */
    public String jump(String cmd) {
        if (cmd == null)
            return "000";
        return jumpTable.get(cmd);
    }

    /**
     * to16BitBinary(int):
     * Returns the 16-bit binary representation of integer number.
     * 
     * @param line: string that represents integer number
     * @return: the 16-bit binary code of the number
     */
    public String to16BitBinary(int line) {
        String result = "";
        String binary = Integer.toBinaryString(line);
        for (int i = binary.length(); i < 16; i++) {
            result += "0";
        }
        result += binary;
        return result;
    }
}
