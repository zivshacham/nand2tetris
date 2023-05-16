import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Created by Ziv Shacham on January 2023
 */

/**
 * JackTokenizer class Parses the Jack code into separated tokens by their type,
 * to then be compiled by the CompilationEngine.
 * 
 * JackTokenizer handles the compiler's input and provides services for:
 * • Ignoring white space
 * • Getting the current token, and advancing the input just beyond it
 * • Getting the type of the current token
 */
public class JackTokenizer {
    private Scanner fileScanner;
    private ArrayList<String> tokens;
    private String jackCode;
    private String curTokenType;
    private String curKeyWord;
    private char curSymbol;
    private String curIdentifier;
    private String curStringVal;
    private int curIntVal;
    private int counter;
    private boolean isFirst;

    private static final ArrayList<String> KEYWORDS = initialKeyWords();
    private static final String SYMBOLS = "{}()[].,;+-*/&|<>=-~";
    private static final String OPERATIONS = "+-*/&|<>=";

    /**
     * Constructor / initializer():
     * Opens input file/stream and gets ready to tokenize it
     * 
     * @param fileName: the file to open and scan
     */
    public JackTokenizer(File fileName) {
        try {
            fileScanner = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file: " + fileName);
        }
        jackCode = "";
        while (fileScanner.hasNextLine()) {
            String strLine = fileScanner.nextLine();
            while (strLine.equals("") || hasComments(strLine)) {
                if (hasComments(strLine)) {
                    strLine = removeComments(strLine);
                }
                if (strLine.trim().equals("")) {
                    if (fileScanner.hasNextLine()) {
                        strLine = fileScanner.nextLine();
                    } else {
                        break;
                    }
                }
            }
            jackCode += strLine.trim();
        }
        isFirst = true;
        counter = 0;
        tokens = new ArrayList<String>();
        while (jackCode.length() > 0) {
            while (jackCode.charAt(0) == ' ') {
                jackCode = jackCode.substring(1);
            }
            for (int i = 0; i < KEYWORDS.size(); i++) {
                if (jackCode.startsWith(KEYWORDS.get(i).toString())) {
                    String keyword = KEYWORDS.get(i).toString();
                    tokens.add(keyword);
                    jackCode = jackCode.substring(keyword.length());
                }
            }
            if (SYMBOLS.contains(jackCode.substring(0, 1))) {
                char symbol = jackCode.charAt(0);
                tokens.add(Character.toString(symbol));
                jackCode = jackCode.substring(1);
            } else if (Character.isDigit(jackCode.charAt(0))) {
                String value = jackCode.substring(0, 1);
                jackCode = jackCode.substring(1);
                while (Character.isDigit(jackCode.charAt(0))) {
                    value += jackCode.substring(0, 1);
                    jackCode = jackCode.substring(1);
                }
                tokens.add(value);
            } else if (jackCode.substring(0, 1).equals("\"")) {
                jackCode = jackCode.substring(1);
                String strString = "\"";
                while ((jackCode.charAt(0) != '\"')) {
                    strString += jackCode.charAt(0);
                    jackCode = jackCode.substring(1);
                }
                strString = strString + "\"";
                tokens.add(strString);
                jackCode = jackCode.substring(1);

            } else if (Character.isLetter(jackCode.charAt(0)) || (jackCode.substring(0, 1).equals("_"))) {
                String strIdentifier = jackCode.substring(0, 1);
                jackCode = jackCode.substring(1);
                while ((Character.isLetter(jackCode.charAt(0))) || (jackCode.substring(0, 1).equals("_"))) {
                    strIdentifier += jackCode.substring(0, 1);
                    jackCode = jackCode.substring(1);
                }
                tokens.add(strIdentifier);
            }
        }
    }

    /**
     * hasMoreTokens():
     * Are there more tokens in the input?
     * 
     * @return Boolean hasMore - True if we have more tokens in the input
     */
    public boolean hasMoreTokens() {
        if (counter < tokens.size() - 1) {
            return true;
        }
        return false;
    }

    /**
     * advance():
     * Gets next token from the input and makes it current token.
     * This method should be called only if hasMoreTokens is true.
     * Initially there is no current token.
     */
    public void advance() {
        if (hasMoreTokens()) {
            if (!isFirst) {
                counter++;
            } else if (isFirst) {
                isFirst = false;
            }
            String currentToken = tokens.get(counter);
            if (KEYWORDS.contains(currentToken)) {
                curTokenType = "KEYWORD";
                curKeyWord = currentToken;
            } else if (SYMBOLS.contains(currentToken)) {
                curSymbol = currentToken.charAt(0);
                curTokenType = "SYMBOL";
            } else if (Character.isDigit(currentToken.charAt(0))) {
                curIntVal = Integer.parseInt(currentToken);
                curTokenType = "INT_CONST";
            } else if (currentToken.substring(0, 1).equals("\"")) {
                curTokenType = "STRING_CONST";
                curStringVal = currentToken.substring(1, currentToken.length() - 1);
            } else if ((Character.isLetter(currentToken.charAt(0))) || (currentToken.charAt(0) == '_')) {
                curTokenType = "IDENTIFIER";
                curIdentifier = currentToken;
            }
        }
    }

    /**
     * getTokenType():
     * 
     * @return the type of the current token, as a constant.
     */
    public String getTokenType() {
        return curTokenType;
    }

    /**
     * getKeyWord():
     * 
     * @return the keyword which is the current token, as a constant.
     *         should be called only when getTokenType() is keyword
     */
    public String getKeyWord() {
        return curKeyWord;
    }

    /**
     * getSymbol():
     * 
     * @return the character which is the current token, should be called only if
     *         getTokenType() is symbol
     */
    public char getSymbol() {
        return curSymbol;
    }

    /**
     * getIdentifier():
     * 
     * @return the string which is the current token, should be called only if
     *         getTokenType() is identifier
     */
    public String getIdentifier() {
        return curIdentifier;
    }

    /**
     * getIntVal():
     * 
     * @return the integer value which is the current token, should be called only
     *         if
     *         getTokenType() is INT_CONST
     */
    public int getIntVal() {
        return curIntVal;
    }

    /**
     * getStringVal():
     * 
     * @return the string value which is the current token, without the opening and
     *         closing double quotes
     *         should be called only if getTokenType() is string_const
     */
    public String getStringVal() {
        return curStringVal;
    }

    /**
     * isOperation():
     * Indicates if a symbol is an operation, i.e., =, +, -, &, |, etc.
     * 
     * @return True if the symbol is operation.
     */
    public boolean isOperation() {
        for (int i = 0; i < OPERATIONS.length(); i++) {
            if (OPERATIONS.charAt(i) == curSymbol) {
                return true;
            }
        }
        return false;
    }

    /**
     * decrementCounter():
     * Decrement the counter in the tokens array 1 step backwards
     */
    public void decrementCounter() {
        if (counter > 0) {
            counter--;
        }
    }

    /**
     * hasComments():
     * Check whether the line argument has comments in it.
     * 
     * @param strLine: the current line
     * @return true if line argument has comments in it
     */
    private boolean hasComments(String strLine) {
        if (strLine.contains("//") || strLine.contains("/*") || strLine.startsWith(" *")) {
            return true;
        }
        return false;
    }

    /**
     * removeComments():
     * Remove the comments from the current line and
     * returns the new line without the comments.
     * 
     * @param strLine: the current line
     * @return the same line without comments at all
     */
    private String removeComments(String strLine) {
        String strNoComments = strLine;
        if (hasComments(strLine)) {
            int offSet;
            if (strLine.startsWith(" *")) {
                offSet = strLine.indexOf("*");
            } else if (strLine.contains("/*")) {
                offSet = strLine.indexOf("/*");
            } else {
                offSet = strLine.indexOf("//");
            }
            strNoComments = strLine.substring(0, offSet).trim();
        }
        return strNoComments;
    }

    /**
     * initialKeyWords():
     * Initializing the const KEYWORDS with saved keywords in Jack.
     * 
     * @return the ArrayList which contains the keywords
     */
    private static ArrayList<String> initialKeyWords() {
        ArrayList<String> keyWords = new ArrayList<String>();
        keyWords.add("class");
        keyWords.add("method");
        keyWords.add("function");
        keyWords.add("constructor");
        keyWords.add("int");
        keyWords.add("boolean");
        keyWords.add("char");
        keyWords.add("void");
        keyWords.add("var");
        keyWords.add("static");
        keyWords.add("field");
        keyWords.add("let");
        keyWords.add("do");
        keyWords.add("if");
        keyWords.add("else");
        keyWords.add("while");
        keyWords.add("return");
        keyWords.add("true");
        keyWords.add("false");
        keyWords.add("null");
        keyWords.add("this");
        return keyWords;
    }
}
