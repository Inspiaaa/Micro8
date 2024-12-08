package inspiaaa.assembler;

import inspiaaa.assembler.parser.*;

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
        Assembler assembler = new Assembler(code);
        SymbolTable symtable = assembler.getSymtable();

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

        ErrorReporter errorReporter = new ErrorReporter(code, 3, 1);

        List<List<Token>> tokensByLine = Lexer.scan(code, errorReporter);

        for (List<Token> line : tokensByLine) {
            System.out.println("---");
            for (Token token : line) {
                System.out.println(token);
            }
        }

        System.out.println();

        var parser = new Parser(errorReporter);

        String label = parser.parseLabelIfPossible(tokensByLine.get(0));
        System.out.println("Label: " + label);

        InstructionCallData icall = parser.parseInstruction(tokensByLine.get(1));
        System.out.println(icall.getName());
        System.out.println(icall.getArguments());
    }
}