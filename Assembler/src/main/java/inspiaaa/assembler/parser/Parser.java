package inspiaaa.assembler.parser;

import inspiaaa.assembler.Expression;
import inspiaaa.assembler.NumericExpression;
import inspiaaa.assembler.SymbolicExpression;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final ErrorReporter errorReporter;

    public Parser(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    public String parseLabelIfPossible(List<Token> tokens) {
        int length = tokens.size();

        Token last = tokens.get(length-1);

        if (last.getType() != TokenType.COLON) {
            return null;
        }

        if (length != 2) {
            errorReporter.reportError("Invalid label syntax.", last.getLine());
        }

        Token symbol = tokens.get(0);

        if (symbol.getType() != TokenType.SYMBOL) {
            errorReporter.reportError("Expected string identifier for label name.", last.getLine());
        }

        return symbol.getValue();
    }

    public InstructionCallData parseInstruction(List<Token> tokens) {
        Token op = tokens.get(0);
        int line = op.getLine();

        if (op.getType() != TokenType.SYMBOL) {
            errorReporter.reportSyntaxError("Missing opcode.", line);
        }

        String name = op.getValue();

        var stream = new TokenStream(tokens);
        stream.advance();
        List<Expression> arguments = parseArguments(stream, line);

        return new InstructionCallData(name, arguments, line);
    }

    private List<Expression> parseArguments(TokenStream stream, int line) {
        var arguments = new ArrayList<Expression>();

        while (!stream.isAtEnd()) {
            Expression expression = tokenToExpression(stream.peek(), line);
            if (expression == null) {
                errorReporter.reportSyntaxError("Expected expression, but found: " + stream.peek().getType(), line);
            }

            arguments.add(expression);
            stream.advance();

            Expression relativeBaseExpression = parseBaseAddress(stream, line);
            if (relativeBaseExpression != null) {
                arguments.add(relativeBaseExpression);
            }

            if (stream.match(TokenType.COMMA)) {
                continue;
            }

            if (stream.isAtEnd()) {
                break;
            }

            errorReporter.reportSyntaxError("Unexpected token: " + stream.peek().getType(), line);
        }

        return arguments;
    }

    private Expression tokenToExpression(Token token, int line) {
        return switch (token.getType()) {
            case BIN_LITERAL -> new NumericExpression(parseBinaryNumber(token), line);
            case HEX_LITERAL -> new NumericExpression(parseHexNumber(token), line);
            case DEC_LITERAL -> new NumericExpression(parseDecimalNumber(token), line);
            case SYMBOL -> new SymbolicExpression(token.getValue(), line);
            default -> null;
        };
    }

    private int parseBinaryNumber(Token token) {
        return Integer.parseInt(token.getValue().replace("0b", ""), 2);
    }

    private int parseHexNumber(Token token) {
        return Integer.parseInt(token.getValue().replace("0x", ""), 16);
    }

    private int parseDecimalNumber(Token token) {
        return Integer.parseInt(token.getValue());
    }

    // Parse second part of relative addressing mode: E.g. '(sp)' in '4(sp)'.
    private Expression parseBaseAddress(TokenStream stream, int line) {
        if (!stream.match(TokenType.L_PAREN))
            return null;

        if (stream.isAtEnd())
            errorReporter.reportSyntaxError("Missing closing ')'.", line);

        Expression expression = tokenToExpression(stream.peek(), line);
        if (expression == null)
            errorReporter.reportSyntaxError("Expected expression, but found: " + stream.peek().getType(), line);
        stream.advance();

        if (!stream.match(TokenType.R_PAREN))
            errorReporter.reportSyntaxError("Expected closing ')'.", line);

        return expression;
    }
}
