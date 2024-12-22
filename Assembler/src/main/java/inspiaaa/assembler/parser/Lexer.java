package inspiaaa.assembler.parser;

import inspiaaa.assembler.ErrorReporter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static class Rule {
        public final Pattern pattern;
        public final TokenType tokenType;
        public final boolean ignore;

        private Rule(String regex, TokenType tokenType, boolean ignore) {
            this.pattern = Pattern.compile(regex);
            this.tokenType = tokenType;
            this.ignore = ignore;
        }

        public static Rule Match(TokenType token, String regex) {
            return new Rule(regex, token, false);
        }

        public static Rule Ignore(String regex) {
            return new Rule(regex, null, true);
        }
    }

    private static final Rule[] tokenPatterns = {
        Rule.Ignore("[ \t\r]+"),
        Rule.Ignore("#.+"),
        Rule.Match(TokenType.NEW_LINE, "\\n"),
        Rule.Match(TokenType.BIN_LITERAL, "[+-]?0b[01]+"),
        Rule.Match(TokenType.HEX_LITERAL, "[+-]?0x[0-9a-fA-F]+"),
        Rule.Match(TokenType.DEC_LITERAL, "[+-]?[0-9]+"),
        Rule.Match(TokenType.CHAR, "'(?:\\\\'|\\\\?[^'\\\\]|\\\\\\\\)'"),
        Rule.Match(TokenType.STRING, "\"(?:[^\"\\\\]|\\\\.)\""),
        Rule.Match(TokenType.SYMBOL, "[.a-zA-Z_][.a-zA-Z0-9]*"),
        Rule.Match(TokenType.COMMA, ","),
        Rule.Match(TokenType.L_PAREN, "\\("),
        Rule.Match(TokenType.R_PAREN, "\\)"),
        Rule.Match(TokenType.COLON, ":"),
    };

    private final ErrorReporter errorReporter;

    private final String file;
    private final String code;

    private int lineNumber = 1;
    private int columnNumber = 1;
    private int index = 0;

    private final List<Token> tokens;

    private Lexer(String file, String code, ErrorReporter errorReporter) {
        this.file = file;
        this.code = code;
        this.errorReporter = errorReporter;

        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (index < code.length()) {
            scanToken();
        }

        return tokens;
    }

    private void scanToken() {
        for (Rule rule : tokenPatterns) {
            Matcher matcher = rule.pattern.matcher(code);
            if (!matcher.find(index) || matcher.start() != index) {
                continue;
            }

            String text = matcher.group();

            if (!rule.ignore) {
                var location = new Location(file, lineNumber, columnNumber, text.length());
                tokens.add(new Token(rule.tokenType, text, location));
            }

            consume(text);
            return;
        }

        errorReporter.reportSyntaxError(
                "Unexpected character '" + code.charAt(index) + "'.",
                new Location(file, lineNumber, columnNumber));
    }

    private void consume(String text) {
        index += text.length();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            columnNumber += 1;

            if (c == '\n') {
                lineNumber += 1;
                columnNumber = 1;
            }
        }
    }

    public static List<Token> tokenize(String file, String code, ErrorReporter errorReporter) {
        return new Lexer(file, code, errorReporter).tokenize();
    }
}
