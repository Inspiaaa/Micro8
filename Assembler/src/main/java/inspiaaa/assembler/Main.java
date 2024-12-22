package inspiaaa.assembler;

import inspiaaa.assembler.parser.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String code = """
start:
label1: label2: addi r0, 15
addi sp, 0b101

# Comment

lw x0, 10(x1) # Comment
.org 0x50
""";

        var arch = new ArchitectureInformation(
                256,
                256,
                8,
                16
        );

        Assembler assembler = new Assembler(code, arch);
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

        ErrorReporter errorReporter = new ErrorReporter(3, 1);
        errorReporter.loadFile("main.S", code);

        List<Token> tokens = Lexer.tokenize("main.S", code, errorReporter);

        for (Token token : tokens) {
            System.out.println(token);
        }
        System.out.println();
    }
}