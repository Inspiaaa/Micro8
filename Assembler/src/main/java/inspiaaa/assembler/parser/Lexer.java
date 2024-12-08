package inspiaaa.assembler.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static class Rule {
        public final Pattern pattern;
        public final TokenType tokenType;

        public Rule(String regex, TokenType tokenType) {
            this.pattern = Pattern.compile(regex);
            this.tokenType = tokenType;
        }
    }

    private static final Rule[] tokenPatterns = {
            new Rule("0b([01]+)", TokenType.BIN_LITERAL),
            new Rule("0x([0-9a-zA-Z]+)", TokenType.HEX_LITERAL),
            new Rule("\\d+", TokenType.DEC_LITERAL),
            new Rule("[.a-zA-Z_][.a-zA-Z0-9]*", TokenType.SYMBOL),
            new Rule(",", TokenType.COMMA),
            new Rule("\\(", TokenType.L_PAREN),
            new Rule("\\)", TokenType.R_PAREN),
            new Rule(":", TokenType.COLON),
    };

    private final ErrorReporter errorReporter;

    private String code;
    private int lineNumber = 1;
    private int index = 0;

    public Lexer(ErrorReporter errorReporter) {
        this.errorReporter = errorReporter;
    }

    private List<Token> scanLine(String line, int lineNumber) {
        this.code = line;
        this.index = 0;
        this.lineNumber = lineNumber;

        ArrayList<Token> tokens = new ArrayList<>();

        while (index < code.length()) {
            if (ignoreWhiteSpace()) continue;
            if (ignoreComment()) continue;

            if (index >= code.length()) {
                continue;
            }

            Token token = scanNextToken();

            if (token == null) {
                char errorChar = code.charAt(index);
                errorReporter.reportError("Unexpected character '" + errorChar + "'.", lineNumber);
            }

            tokens.add(token);
        }

        return tokens;
    }

    private Token scanNextToken() {
        for (Rule rule : tokenPatterns) {
            Matcher matcher = rule.pattern.matcher(code);

            if (matcher.find(index) && matcher.start() == index) {
                index += matcher.group().length();
                String value = matcher.group(matcher.groupCount());
                return new Token(rule.tokenType, value, lineNumber);
            }
        }

        return null;
    }

    private boolean ignoreComment() {
        if (code.charAt(index) != '#')
            return false;

        while (index < code.length() && code.charAt(index) != '\n') {
            index ++;
        }

        return true;
    }

    private boolean ignoreWhiteSpace() {
        int startIndex = index;

        while (index < code.length() && Character.isWhitespace(code.charAt(index))) {
            index ++;
        }

        return startIndex != index;
    }

    public static List<List<Token>> scan(String code, ErrorReporter errorReporter) {
        String[] lines = code.split("\n");
        Lexer lexer = new Lexer(errorReporter);

        var tokensByLine = new ArrayList<List<Token>>();

        for (int i = 0; i < lines.length; i ++) {
            String line = lines[i];
            List<Token> tokens = lexer.scanLine(line, i+1);
            if (!tokens.isEmpty()) {
                tokensByLine.add(tokens);
            }
        }

        return tokensByLine;
    }
}
