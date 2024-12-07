package inspiaaa.assembler.parser;

import java.util.List;

public class Parser {
    public static String parseLabel(List<Token> tokens, ErrorReporter er) {
        int length = tokens.size();
        Token last = tokens.get(length-1);

        if (last.getType() != TokenType.COLON) {
            return null;
        }

        if (length != 2) {
            er.reportError("Invalid label syntax.", last.getLine());
        }

        Token symbol = tokens.get(0);

        if (symbol.getType() != TokenType.SYMBOL) {
            er.reportError("Expected identifier for label name.", last.getLine());
        }

        return "";
    }
}
