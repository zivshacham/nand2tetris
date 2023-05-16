import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/*
 * Created by Ziv Shacham on January 2023
 */

public class SymbolTable {

    // Table: Hash table that holds the predefined symbols in the language
    private Hashtable<String, Integer> Table = new Hashtable<String, Integer>();
    // nextToAllocate: counter which follows the next memory slot to allocate
    private int nextToAllocate = 16;

    /**
     * Constructor / initializer(string):
     * Creates and initializes a SymbolTable.
     * if can't open the file - a message is displayed.
     * 
     * @param fileName: string that represents the file name
     */
    public SymbolTable() {
        Table.put("R0", 0);
        Table.put("R1", 1);
        Table.put("R2", 2);
        Table.put("R3", 3);
        Table.put("R4", 4);
        Table.put("R5", 5);
        Table.put("R6", 6);
        Table.put("R7", 7);
        Table.put("R8", 8);
        Table.put("R9", 9);
        Table.put("R10", 10);
        Table.put("R11", 11);
        Table.put("R12", 12);
        Table.put("R13", 13);
        Table.put("R14", 14);
        Table.put("R15", 15);
        Table.put("SCREEN", 16384);
        Table.put("KBD", 24576);
        Table.put("SP", 0);
        Table.put("LCL", 1);
        Table.put("ARG", 2);
        Table.put("THIS", 3);
        Table.put("THAT", 4);
    }

    /**
     * Constructor / initializer(string):
     * Creates and initializes a SymbolTable.
     * if can't open the file - a message is displayed.
     * 
     * @param fileName: string that represents the file name
     */
    public SymbolTable(String fileName) {
        try {
            // try to read from file
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String line = reader.readLine();
                String[] splittedLine = line.split(" ");
                Table.put(splittedLine[0], Integer.parseInt(splittedLine[1]));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not open file: " + fileName);
        }
    }

    /**
     * addEntry:
     * 
     * @param symbol:  a symbol which should added to the table
     * @param address: the address that the symbol is referring to
     */
    public void addEntry(String symbol, int address) {
        Table.put(symbol, address);
    }

    /**
     * addEntry (Overloading with addEntry):
     * Adds new entry for new variables in the code.
     * 
     * @param symbol:  a symbol which should added to the table
     * @param address: the address that the symbol is referring to
     */
    public void addEntry(String symbol) {
        Table.put(symbol, nextToAllocate);
        nextToAllocate++;
    }

    /**
     * contains:
     * 
     * @param symbol: a symbol which should be searched for in the table
     * @return boolean value if the symbol is existed in the table or not
     */
    public boolean contains(String symbol) {
        if (Table.containsKey(symbol))
            return true;
        return false;
    }

    /**
     * getAddress:
     * 
     * @param symbol: a symbol which we want to get the address it referring to
     * @return the address that the symbol is referring to
     */
    public int getAddress(String symbol) {
        return Table.get(symbol);
    }

    /**
     * printTable:
     * for debugging purposes - prints the table
     */
    public void printTable() {
        System.out.println(Table);
    }
}
