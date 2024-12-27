package inspiaaa.assembler.parser;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.InstructionCall;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.directives.LabelDirective;
import inspiaaa.assembler.expressions.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final SymbolTable symtable;
    private final ErrorReporter errorReporter;

    private final List<InstructionCall> instructions;
    private final TokenStream tokens;

    private Parser(List<Token> tokens, SymbolTable symtable, ErrorReporter errorReporter) {
        this.tokens = new TokenStream(tokens);
        this.instructions = new ArrayList<>();

        this.symtable = symtable;
        this.errorReporter = errorReporter;
    }

    public List<InstructionCall> parse() {
        while (!tokens.isAtEnd()) {
            if (tokens.match(TokenType.NEW_LINE)) {
                continue;
            }

            while (parseLabelIfPossible());

            if (tokens.match(TokenType.NEW_LINE)) {
                continue;
            }

            parseInstruction();
        }
        return instructions;
    }

    private boolean parseLabelIfPossible() {
        if (tokens.remaining() < 2)
            return false;

        Token colon = tokens.peek(2);

        if (colon.getType() != TokenType.COLON)
            return false;

        Token symbol = tokens.peek();
        if (symbol.getType() != TokenType.SYMBOL) {
            errorReporter.reportSyntaxError("Expected string identifier for label name.", symbol.getLocation());
        }

        tokens.advance();
        tokens.advance();

        instructions.add(new InstructionCall(
                LabelDirective.VIRTUAL_MNEMONIC,
                List.of(new StringExpr(symbol.getValue(), symbol.getLocation())),
                Location.merge(symbol.getLocation(), colon.getLocation())));

        return true;
    }

    private void parseInstruction() {
        if (tokens.remaining() == 0)
            return;

        Token op = tokens.advance();

        if (op.getType() != TokenType.SYMBOL) {
            errorReporter.reportSyntaxError("Missing opcode.", op.getLocation());
        }

        String mnemonic = op.getValue();

        List<Expr> arguments = parseArguments();

        Location location = arguments.isEmpty()
                ? op.getLocation()
                : Location.merge(op.getLocation(), arguments.get(arguments.size() - 1).getLocation());

        instructions.add(new InstructionCall(mnemonic, arguments, location));
    }

    private List<Expr> parseArguments() {
        var arguments = new ArrayList<Expr>();

        while (!tokens.isAtEnd() && !tokens.checkNext(TokenType.NEW_LINE)) {
            Token token = tokens.advance();
            Expr expression = tokenToExpression(token);

            if (expression == null) {
                errorReporter.reportSyntaxError(
                        "Expected expression, but found: " + token.getType(),
                        token.getLocation());
            }

            RelativeAddressExpr relativeAddress = parseBaseAddress(expression);
            arguments.add(relativeAddress == null ? expression : relativeAddress);

            if (!tokens.match(TokenType.COMMA) && !tokens.isAtEnd() && !tokens.checkNext(TokenType.NEW_LINE)) {
                errorReporter.reportSyntaxError(
                        "Expected comma, but found: " + tokens.peek().getType(),
                        tokens.peek().getLocation());
            }
        }

        return arguments;
    }

    private Expr tokenToExpression(Token token) {
        var location = token.getLocation();

        return switch (token.getType()) {
            case BIN_LITERAL -> new NumberExpr(parseBinaryNumber(token), location);
            case HEX_LITERAL -> new NumberExpr(parseHexNumber(token), location);
            case DEC_LITERAL -> new NumberExpr(parseDecimalNumber(token), location);
            case SYMBOL -> new SymbolExpr(token.getValue(), symtable, location);
            case STRING -> new StringExpr(parseString(token), location);
            case CHAR -> new CharExpr(parseCharacter(token), location);
            default -> null;
        };
    }

    private long parseBinaryNumber(Token token) {
        return Long.parseLong(
                token.getValue().replace("0b", "").replace("_", ""),
                2);
    }

    private long parseHexNumber(Token token) {
        return Long.parseLong(
                token.getValue().replace("0x", "").replace("_", ""),
                16);
    }

    private long parseDecimalNumber(Token token) {
        return Long.parseLong(token.getValue().replace("_", ""));
    }

    private char parseCharacter(Token token) {
        return StringUtil.unescapeString(token.getValue().substring(1, 3)).charAt(0);
    }

    private String parseString(Token token) {
        String value = token.getValue();
        return StringUtil.unescapeString(value.substring(1, value.length()-1));
    }

    // Parse second part of relative addressing mode: E.g. '(sp)' in '4(sp)'.
    private RelativeAddressExpr parseBaseAddress(Expr offsetExpr) {
        if (!tokens.checkNext(TokenType.L_PAREN))
            return null;
        Token lParen = tokens.advance();

        if (tokens.isAtEnd()) {
            errorReporter.reportSyntaxError("Missing closing ')'.", lParen.getLocation().nextColumn());
        }

        Token expressionToken = tokens.advance();
        Expr baseExpr = tokenToExpression(expressionToken);

        if (baseExpr == null) {
            errorReporter.reportSyntaxError("Expected expression, but found: " + expressionToken.getType(), expressionToken.getLocation());
        }

        if (tokens.isAtEnd()) {
            errorReporter.reportSyntaxError("Missing closing ')'.", expressionToken.getLocation().nextColumn());
        }

        Token rParen = tokens.advance();

        if (rParen.getType() != TokenType.R_PAREN) {
            errorReporter.reportSyntaxError("Expected closing ')'.", rParen.getLocation());
        }

        return new RelativeAddressExpr(
                offsetExpr,
                baseExpr,
                Location.merge(offsetExpr.getLocation(), rParen.getLocation()));
    }

    public static List<InstructionCall> parse(List<Token> tokens, SymbolTable symtable, ErrorReporter errorReporter) {
        return new Parser(tokens, symtable, errorReporter).parse();
    }
}
