import java.io.File;
import java.io.IOException;

/*
 * Created by Ziv Shacham on January 2023
 */

/**
 * CompilationEngine:
 * Gets its input from a JackTokenizer and writes its output using the VMWriter/
 * Organized as a series of compileXXX routines,
 * XXX being a syntactic element in the Jack grammar:
 * • Each compileXXX routine should read XXX from input, advance() the input
 * exactly beyond XXX, abd emit to the output VM code effecting the semantics of
 * XXX.
 * • compileXXX is called only if XXX is the current syntactic element.
 * • If XXX is part of an expression and thus has a value, the emitted VM code
 * should
 * compute this value and leave it at the top of the VM's stack.
 */
public class CompilationEngine {
    private VMWriter vmWriter;
    private JackTokenizer jackTokenizer;
    private SymbolTable symbolTable;
    private String className;
    private String subroutineName;
    private int labelsCounter;

    /**
     * Constructor / initializer():
     * Creates a new CompilationEngine with the given input and output.
     * if can't open the file - a message is displayed.
     * The next routine called (by the JackAnalyzer module) must be compileClass.
     * 
     * @param fileInput:  the input file
     * @param fileOutput: the output file
     */
    public CompilationEngine(File fileInput, File fileOutput) {
        className = "";
        subroutineName = "";
        labelsCounter = 0;
        vmWriter = new VMWriter(fileOutput);
        symbolTable = new SymbolTable();
        jackTokenizer = new JackTokenizer(fileInput);
    }

    /**
     * compileClass():
     * Compiles a complete class.
     * 
     * @throws IOException
     */
    public void compileClass() throws IOException {
        jackTokenizer.advance();
        jackTokenizer.advance();
        className = jackTokenizer.identifier();
        jackTokenizer.advance();
        compileClassVarDec();
        compileSubroutine();
        vmWriter.close();
    }

    /**
     * compileClassVarDec():
     * Compiling a static variable declaration or a field declaration.
     * 
     * @throws IOException
     */
    public void compileClassVarDec() {
        String name, type, kind;
        jackTokenizer.advance();
        while (jackTokenizer.keyWord().equals("static") || jackTokenizer.keyWord().equals("field")) {
            if (jackTokenizer.keyWord().equals("static")) {
                kind = "static";
            } else {
                kind = "field";
            }
            jackTokenizer.advance();
            if (jackTokenizer.tokenType().equals("IDENTIFIER")) {
                type = jackTokenizer.identifier();
            } else {
                type = jackTokenizer.keyWord();
            }
            jackTokenizer.advance();
            symbolTable.define(jackTokenizer.identifier(), type, kind);
            jackTokenizer.advance();
            while (jackTokenizer.symbol() == ',') {
                jackTokenizer.advance();
                name = jackTokenizer.identifier();
                symbolTable.define(name, type, kind);
                jackTokenizer.advance();
            }
            jackTokenizer.advance();
        }
        if (jackTokenizer.keyWord().equals("function") || jackTokenizer.keyWord().equals("method")
                || jackTokenizer.keyWord().equals("constructor")) {
            jackTokenizer.decrementCounter();
            return;
        }
    }

    /**
     * compileSubroutine():
     * Compiles a complete method, function or constructor.
     * 
     * @throws IOException
     */
    public void compileSubroutine() throws IOException {
        String keyWord = "";
        String function = "";
        String type = "";
        jackTokenizer.advance();
        if (jackTokenizer.symbol() == '}' && jackTokenizer.tokenType().equals("SYMBOL")) {
            return;
        }
        if ((jackTokenizer.keyWord().equals("function") || jackTokenizer.keyWord().equals("method")
                || jackTokenizer.keyWord().equals("constructor"))) {
            keyWord = jackTokenizer.keyWord();
            symbolTable.reset();
            if (jackTokenizer.keyWord().equals("method")) {
                symbolTable.define("this", className, "argument");
            }
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType().equals("KEYWORD")) {
            if (jackTokenizer.keyWord().equals("void")) {
                type = "void";
                jackTokenizer.advance();
            } else if ((jackTokenizer.keyWord().equals("int") || jackTokenizer.keyWord().equals("boolean")
                    || jackTokenizer.keyWord().equals("char"))) {
                type = jackTokenizer.keyWord();
                jackTokenizer.advance();
            }
        } else {
            type = jackTokenizer.identifier();
            jackTokenizer.advance();
        }

        if (jackTokenizer.tokenType().equals("IDENTIFIER")) {
            subroutineName = jackTokenizer.identifier();
            jackTokenizer.advance();
        }

        if (jackTokenizer.symbol() == '(') {
            compileParameterList();
        }
        jackTokenizer.advance();

        if (jackTokenizer.symbol() == '{') {
            jackTokenizer.advance();
        }

        while (jackTokenizer.keyWord().equals("var") && (jackTokenizer.tokenType().equals("KEYWORD"))) {
            jackTokenizer.decrementCounter();
            compileVarDec();
        }
        if (!className.equals("") && !subroutineName.equals("")) {
            function += className;
            function += ".";
            function += subroutineName;
        }
        vmWriter.writeFunction(function, symbolTable.varCount("var"));

        if (keyWord.equals("method")) {
            vmWriter.writePush("argument", 0);
            vmWriter.writePop("pointer", 0);
        } else if (keyWord.equals("constructor")) {
            vmWriter.writePush("constant", symbolTable.varCount("field"));
            vmWriter.writeCall("Memory.alloc", 1);
            vmWriter.writePop("pointer", 0);
        }
        compileStatements();
        // this is the recursive call of the function
        compileSubroutine();
    }

    /**
     * compileParameterList():
     * Compiles a (possibly empty) parameter list.
     */
    public void compileParameterList() {
        boolean hasParameters = false;
        String type = "";
        String name = "";
        jackTokenizer.advance();
        while (!(jackTokenizer.tokenType().equals("SYMBOL") && jackTokenizer.symbol() == ')')) {
            if (jackTokenizer.tokenType().equals("KEYWORD")) {
                hasParameters = true;
                type = jackTokenizer.keyWord();
            } else if (jackTokenizer.tokenType().equals("IDENTIFIER")) {
                type = jackTokenizer.identifier();
            }
            jackTokenizer.advance();
            if (jackTokenizer.tokenType().equals("IDENTIFIER")) {
                name = jackTokenizer.identifier();
            }
            jackTokenizer.advance();
            if (jackTokenizer.tokenType().equals("SYMBOL") && jackTokenizer.symbol() == ',') {
                symbolTable.define(name, type, "argument");
                jackTokenizer.advance();
            }
        }
        if (hasParameters) {
            symbolTable.define(name, type, "argument");
        }
    }

    /**
     * compileVarDec():
     * Compiles a var declaration.
     */
    public void compileVarDec() {
        jackTokenizer.advance();
        String type = "";
        String name = "";
        if (jackTokenizer.keyWord().equals("var") && (jackTokenizer.tokenType().equals("KEYWORD"))) {
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType().equals("IDENTIFIER")) {
            type = jackTokenizer.identifier();
            jackTokenizer.advance();
        } else if (jackTokenizer.tokenType().equals("KEYWORD")) {
            type = jackTokenizer.keyWord();
            jackTokenizer.advance();
        }
        if (jackTokenizer.tokenType().equals("IDENTIFIER")) {
            name = jackTokenizer.identifier();
            jackTokenizer.advance();
        }
        symbolTable.define(name, type, "var");
        while ((jackTokenizer.tokenType().equals("SYMBOL")) && (jackTokenizer.symbol() == ',')) {
            jackTokenizer.advance();
            name = jackTokenizer.identifier();
            symbolTable.define(name, type, "var");
            jackTokenizer.advance();
        }
        if ((jackTokenizer.tokenType().equals("SYMBOL")) && (jackTokenizer.symbol() == ';')) {
            jackTokenizer.advance();
        }
    }

    /**
     * compileStatements():
     * Compiles a sequence of statements.
     * 
     * @throws IOException
     */
    public void compileStatements() throws IOException {
        if (jackTokenizer.symbol() == '}' && (jackTokenizer.tokenType().equals("SYMBOL"))) {
            return;
        } else if ((jackTokenizer.tokenType().equals("KEYWORD"))) {
            {
                if (jackTokenizer.keyWord().equals("do")) {
                    compileDo();
                } else if (jackTokenizer.keyWord().equals("let")) {
                    compileLet();
                } else if (jackTokenizer.keyWord().equals("if")) {
                    compileIf();
                } else if (jackTokenizer.keyWord().equals("while")) {
                    compileWhile();
                } else if (jackTokenizer.keyWord().equals("return")) {
                    compileReturn();
                }
            }
        }
        jackTokenizer.advance();
        compileStatements();
    }

    /**
     * compileLet():
     * Compiles a let statement.
     * 
     * @throws IOException
     */
    public void compileLet() throws IOException {
        boolean isExpression = false;
        String nameOfVariable = "";
        String kindOfVariable = "";
        int indexOfVariable = 0;
        jackTokenizer.advance();
        nameOfVariable = jackTokenizer.identifier();
        jackTokenizer.advance();
        if ((jackTokenizer.tokenType().equals("SYMBOL")) && (jackTokenizer.symbol() == '[')) {
            isExpression = true;
            kindOfVariable = symbolTable.kindOf(nameOfVariable);
            indexOfVariable = symbolTable.indexOf(nameOfVariable);
            vmWriter.writePush(kindOfVariable, indexOfVariable);
            compileExpression();
            jackTokenizer.advance();
            vmWriter.writeArithmetic("add");
            jackTokenizer.advance();
        }
        compileExpression();
        jackTokenizer.advance();
        if (isExpression) {
            vmWriter.writePop("temp", 0);
            vmWriter.writePop("pointer", 1);
            vmWriter.writePush("temp", 0);
            vmWriter.writePop("that", 0);
        } else {
            kindOfVariable = symbolTable.kindOf(nameOfVariable);
            indexOfVariable = symbolTable.indexOf(nameOfVariable);
            vmWriter.writePop(kindOfVariable, indexOfVariable);
        }
    }

    /**
     * compileIf():
     * Compiles an if statement,
     * possibly with a trailing else clause.
     * 
     * @throws IOException
     */
    public void compileIf() throws IOException {
        String elseLabel = "";
        elseLabel += "LABEL_";
        elseLabel += labelsCounter;
        labelsCounter++;
        String endLabel = "";
        endLabel += "LABEL_";
        endLabel += labelsCounter;
        labelsCounter++;
        jackTokenizer.advance();
        compileExpression();
        jackTokenizer.advance();
        vmWriter.writeArithmetic("not");
        vmWriter.writeIf(elseLabel);
        jackTokenizer.advance();
        compileStatements();
        vmWriter.writeGoto(endLabel);
        vmWriter.writeLabel(elseLabel);
        jackTokenizer.advance();
        if (jackTokenizer.tokenType().equals("KEYWORD") && jackTokenizer.keyWord().equals("else")) {
            jackTokenizer.advance();
            jackTokenizer.advance();
            compileStatements();
        } else {
            jackTokenizer.decrementCounter();
        }
        vmWriter.writeLabel(endLabel);
    }

    /**
     * compileWhile():
     * Compiles a while statement.
     * 
     * @throws IOException
     */
    public void compileWhile() throws IOException {
        String innerLabel = "";
        innerLabel = "LABEL_";
        innerLabel += labelsCounter;
        labelsCounter++;
        String outerLabel = "";
        outerLabel = "LABEL_";
        outerLabel += labelsCounter;
        labelsCounter++;
        vmWriter.writeLabel(outerLabel);
        jackTokenizer.advance();
        compileExpression();
        jackTokenizer.advance();
        vmWriter.writeArithmetic("not");
        vmWriter.writeIf(innerLabel);
        jackTokenizer.advance();
        compileStatements();
        vmWriter.writeGoto(outerLabel);
        vmWriter.writeLabel(innerLabel);
    }

    /**
     * compileDo():
     * Compiles a do statement.
     * 
     * @throws IOException
     */
    public void compileDo() throws IOException {
        compileCall();
        jackTokenizer.advance();
        vmWriter.writePop("temp", 0);
    }

    /**
     * compileReturn():
     * Compiles a return statement.
     * 
     * @throws IOException
     */
    public void compileReturn() throws IOException {
        jackTokenizer.advance();
        if (!((jackTokenizer.tokenType().equals("SYMBOL") && jackTokenizer.symbol() == ';'))) {
            jackTokenizer.decrementCounter();
            compileExpression();
        } else if (jackTokenizer.tokenType().equals("SYMBOL") && jackTokenizer.symbol() == ';') {
            vmWriter.writePush("constant", 0);
        }
        vmWriter.writeReturn();
    }

    /**
     * compileExpression():
     * Compiles an expression.
     * 
     * @throws IOException
     */
    public void compileExpression() throws IOException {
        char currentSymbol;
        compileTerm();
        while (true) {
            jackTokenizer.advance();
            currentSymbol = jackTokenizer.symbol();
            if (jackTokenizer.tokenType().equals("SYMBOL") && jackTokenizer.isOperation()) {
                switch (currentSymbol) {
                    case '+':
                        compileTerm();
                        vmWriter.writeArithmetic("add");
                        break;
                    case '-':
                        compileTerm();
                        vmWriter.writeArithmetic("sub");
                        break;
                    case '=':
                        compileTerm();
                        vmWriter.writeArithmetic("eq");
                        break;
                    case '>':
                        compileTerm();
                        vmWriter.writeArithmetic("gt");
                        break;
                    case '<':
                        compileTerm();
                        vmWriter.writeArithmetic("lt");
                        break;
                    case '&':
                        compileTerm();
                        vmWriter.writeArithmetic("and");
                        break;
                    case '|':
                        compileTerm();
                        vmWriter.writeArithmetic("or");
                        break;
                    case '*':
                        compileTerm();
                        vmWriter.writeCall("Math.multiply", 2);
                        break;
                    case '/':
                        compileTerm();
                        vmWriter.writeCall("Math.divide", 2);
                        break;
                }
            } else {
                jackTokenizer.decrementCounter();
                break;
            }
        }
    }

    /**
     * compileTerm():
     * Compiles an term.
     * If current token is an identifier, the routine must resolve it
     * into a variable, array entry, and subroutine call.
     * A single lookahead token which may be '[' '(' or '.', suffices
     * to distinguish between the possibilities.
     * Any other token is not part of this term and should not be advanced over.
     * 
     * @throws IOException
     */
    public void compileTerm() throws IOException {
        String strIdentifier = "";
        String currentToken = "";
        String kindOfVariable;
        int indexOfVariable;
        jackTokenizer.advance();
        if (jackTokenizer.tokenType().equals("IDENTIFIER")) {
            strIdentifier = jackTokenizer.identifier();
            jackTokenizer.advance();
            if (jackTokenizer.tokenType().equals("SYMBOL") && jackTokenizer.symbol() == '[') {
                kindOfVariable = symbolTable.kindOf(strIdentifier);
                indexOfVariable = symbolTable.indexOf(strIdentifier);
                vmWriter.writePush(kindOfVariable, indexOfVariable);
                compileExpression();
                jackTokenizer.advance();
                vmWriter.writeArithmetic("add");
                vmWriter.writePop("pointer", 1);
                vmWriter.writePush("that", 0);
            } else if (jackTokenizer.tokenType().equals("SYMBOL")
                    && (jackTokenizer.symbol() == '(' || jackTokenizer.symbol() == '.')) {
                jackTokenizer.decrementCounter();
                jackTokenizer.decrementCounter();
                compileCall();
            } else {
                jackTokenizer.decrementCounter();
                kindOfVariable = symbolTable.kindOf(strIdentifier);
                indexOfVariable = symbolTable.indexOf(strIdentifier);
                vmWriter.writePush(kindOfVariable, indexOfVariable);
            }
        } else {
            if (jackTokenizer.tokenType().equals("INT_CONST")) {
                vmWriter.writePush("constant", jackTokenizer.intVal());
            } else if (jackTokenizer.tokenType().equals("STRING_CONST")) {
                currentToken = jackTokenizer.stringVal();
                vmWriter.writePush("constant", currentToken.length());
                vmWriter.writeCall("String.new", 1);
                for (int i = 0; i < currentToken.length(); i++) {
                    vmWriter.writePush("constant", (int) currentToken.charAt(i));
                    vmWriter.writeCall("String.appendChar", 2);
                }
            } else if (jackTokenizer.tokenType().equals("KEYWORD") && jackTokenizer.keyWord().equals("this")) {
                vmWriter.writePush("pointer", 0);
            } else if (jackTokenizer.tokenType().equals("KEYWORD")
                    && (jackTokenizer.keyWord().equals("null") || jackTokenizer.keyWord().equals("false"))) {
                vmWriter.writePush("constant", 0);
            } else if (jackTokenizer.tokenType().equals("KEYWORD") && jackTokenizer.keyWord().equals("true")) {
                vmWriter.writePush("constant", 0);
                vmWriter.writeArithmetic("not");
            } else if (jackTokenizer.tokenType().equals("SYMBOL") && jackTokenizer.symbol() == '(') {
                compileExpression();
                jackTokenizer.advance();
            } else if (jackTokenizer.tokenType().equals("SYMBOL")
                    && (jackTokenizer.symbol() == '-' || jackTokenizer.symbol() == '~')) {
                char symbol = jackTokenizer.symbol();
                compileTerm();
                if (symbol == '-') {
                    vmWriter.writeArithmetic("neg");
                } else if (symbol == '~') {
                    vmWriter.writeArithmetic("not");
                }
            }
        }
    }

    /**
     * compileExpressionList():
     * Compiles a (possibly empty) comma separated list of expressions.
     * Returns the number of expressions in the list.
     * 
     * @throws IOException
     */
    public int compileExpressionList() throws IOException {
        int numOfArgs = 0;
        jackTokenizer.advance();
        if (jackTokenizer.symbol() == ')' && jackTokenizer.tokenType().equals("SYMBOL")) {
            jackTokenizer.decrementCounter();
        } else {
            numOfArgs = 1;
            jackTokenizer.decrementCounter();
            compileExpression();
        }
        while (true) {
            jackTokenizer.advance();
            if (jackTokenizer.tokenType().equals("SYMBOL") && jackTokenizer.symbol() == ',') {
                compileExpression();
                numOfArgs++;
            } else {
                jackTokenizer.decrementCounter();
                break;
            }
        }
        return numOfArgs;
    }

    /**
     * compileCall():
     * Compiles a call statement.
     * 
     * @throws IOException
     */
    private void compileCall() throws IOException {
        String type = "";
        String nameOfObject = "";
        int numOfArgs = 0;
        String strIdentifier = "";
        jackTokenizer.advance();
        strIdentifier = jackTokenizer.identifier();
        jackTokenizer.advance();
        if ((jackTokenizer.tokenType().equals("SYMBOL")) && (jackTokenizer.symbol() == '.')) {
            nameOfObject = strIdentifier;
            jackTokenizer.advance();
            jackTokenizer.advance();
            strIdentifier = jackTokenizer.identifier();
            type = symbolTable.typeOf(nameOfObject);
            if (!type.equals("")) {
                numOfArgs = 1;
                String kindOfVariable = symbolTable.kindOf(nameOfObject);
                int indexOfVariable = symbolTable.indexOf(nameOfObject);
                vmWriter.writePush(kindOfVariable, indexOfVariable);
                strIdentifier = symbolTable.typeOf(nameOfObject) + "." + strIdentifier;
            } else {
                strIdentifier = nameOfObject + "." + strIdentifier;
            }
            numOfArgs += compileExpressionList();
            jackTokenizer.advance();
            vmWriter.writeCall(strIdentifier, numOfArgs);
        } else if ((jackTokenizer.tokenType().equals("SYMBOL")) && (jackTokenizer.symbol() == '(')) {
            vmWriter.writePush("pointer", 0);
            numOfArgs = 1 + compileExpressionList();
            jackTokenizer.advance();
            String callName = className + "." + strIdentifier;
            vmWriter.writeCall(callName, numOfArgs);
        }
    }
}
