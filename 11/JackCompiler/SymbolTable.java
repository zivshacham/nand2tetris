import java.util.Hashtable;

/*
 * Created by Ziv Shacham on January 2023
 */

public class SymbolTable {

    // Table: Hash table that holds the static variables
    private Hashtable<String, Symbol> staticTable = new Hashtable<String, Symbol>();
    // Table: Hash table that holds the field variables
    private Hashtable<String, Symbol> fieldTable = new Hashtable<String, Symbol>();
    // Table: Hash table that holds the arg variables
    private Hashtable<String, Symbol> argTable = new Hashtable<String, Symbol>();
    // Table: Hash table that holds the variables
    private Hashtable<String, Symbol> varTable = new Hashtable<String, Symbol>();
    // Table: Hash table that holds the counters for the other tables
    private Hashtable<String, Integer> countersTable = new Hashtable<String, Integer>();

    /**
     * Constructor / initializer(string):
     * Creates and initializes a new SymbolTable.
     */
    public SymbolTable() {
        staticTable = new Hashtable<>();
        fieldTable = new Hashtable<>();
        argTable = new Hashtable<>();
        varTable = new Hashtable<>();
        countersTable = new Hashtable<>();
        countersTable.put("static", 0);
        countersTable.put("field", 0);
        countersTable.put("argument", 0);
        countersTable.put("var", 0);
    }

    /**
     * reset():
     * 
     * Empties the symbol table, and resets the four indexes to 0.
     * Should be called when starting to compile a subroutine declaration.
     */
    public void reset() {
        argTable.clear();
        varTable.clear();
        countersTable.put("argument", 0);
        countersTable.put("var", 0);
    }

    /**
     * define():
     * 
     * Defines (adds to the table) a new variable of the given name, type and
     * kind.
     * Assigns to it the index value of that kind, and adds 1 to the index.
     */
    public void define(String name, String type, String kind) {
        int newIndex = countersTable.get(kind);
        Symbol newSymbol = new Symbol(type, newIndex);
        newIndex++;
        countersTable.put(kind, newIndex);
        if (kind.equals("static")) {
            staticTable.put(name, newSymbol);
        }
        if (kind.equals("field")) {
            fieldTable.put(name, newSymbol);
        }
        if (kind.equals("argument")) {
            argTable.put(name, newSymbol);
        }
        if (kind.equals("var")) {
            varTable.put(name, newSymbol);
        }
    }

    /**
     * varCount():
     * 
     * Returns the number of variables of the given kind already defined in the
     * table
     */
    public int varCount(String kind) {
        return countersTable.get(kind);
    }

    /**
     * kindOf():
     * 
     * Return the kind of the named identifier.
     * If the identifier is not found, returns NONE.
     */
    public String kindOf(String name) {
        if (argTable.containsKey(name)) {
            return "argument";
        } else if (varTable.containsKey(name)) {
            return "var";
        } else if (staticTable.containsKey(name)) {
            return "static";
        } else if (fieldTable.containsKey(name)) {
            return "field";
        }
        return "none";
    }

    /**
     * typeOf():
     * 
     * Return the type of the named variable
     */
    public String typeOf(String name) {
        if (argTable.containsKey(name)) {
            return argTable.get(name).getType();
        } else if (varTable.containsKey(name)) {
            return varTable.get(name).getType();
        } else if (staticTable.containsKey(name)) {
            return staticTable.get(name).getType();
        } else if (fieldTable.containsKey(name)) {
            return fieldTable.get(name).getType();
        }
        return "";
    }

    /**
     * indexOf():
     * 
     * Return the index of the named variable
     */
    public int indexOf(String name) {
        if (argTable.containsKey(name)) {
            return argTable.get(name).getIndex();
        } else if (varTable.containsKey(name)) {
            return varTable.get(name).getIndex();
        } else if (staticTable.containsKey(name)) {
            return staticTable.get(name).getIndex();
        } else if (fieldTable.containsKey(name)) {
            return fieldTable.get(name).getIndex();
        }
        return -1;
    }
}
