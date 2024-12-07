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
        ErrorReporter errorReporter = new ErrorReporter(code, 3);

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