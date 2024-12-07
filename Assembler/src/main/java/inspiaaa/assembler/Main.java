package inspiaaa.assembler;

import inspiaaa.assembler.parser.ErrorReporter;
import inspiaaa.assembler.parser.Lexer;
import inspiaaa.assembler.parser.Parser;
import inspiaaa.assembler.parser.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String code = """
start:
addi r0, 15
addi sp, 0b101

# Comment

lw x0, 10(x1) # Comment
.org 0x50
""";
        ErrorReporter errorReporter = new ErrorReporter(code, 3, 1);

        SymbolTable symtable = new SymbolTable(errorReporter);

        symtable.declareBuiltinSymbol(new Symbol("x0", SymbolType.REGISTER, 0));
        symtable.declareBuiltinSymbol(new Symbol("x1", SymbolType.REGISTER, 1));
        symtable.declareBuiltinSymbol(new Symbol("x2", SymbolType.REGISTER, 2));
        symtable.declareBuiltinSymbol(new Symbol("x3", SymbolType.REGISTER, 3));
        symtable.declareBuiltinSymbol(new Symbol("x4", SymbolType.REGISTER, 4));
        symtable.declareBuiltinSymbol(new Symbol("x5", SymbolType.REGISTER, 5));
        symtable.declareBuiltinSymbol(new Symbol("x6", SymbolType.REGISTER, 6));
        symtable.declareBuiltinSymbol(new Symbol("x7", SymbolType.REGISTER, 7));

        symtable.declareSynonym("x7", "sp");
        symtable.declareSynonym("x6", "ra");

        List<List<Token>> tokensByLine = Lexer.lex(code, errorReporter);

        for (List<Token> line : tokensByLine) {
            System.out.println("---");
            for (Token token : line) {
                System.out.println(token);
            }
        }

        Parser.parseLabel(tokensByLine.get(0), errorReporter);
    }
}