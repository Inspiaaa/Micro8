package inspiaaa.assembler.parser;

public class Token {
    private final TokenType type;
    private final String value;
    private final int line;

    public Token(TokenType type) {
        this(type, "", -1);
    }

    public Token(TokenType type, String value) {
        this(type, value, -1);
    }

    public Token(TokenType type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "Token(type=" + type + ", value='" + value + "', line=" + line + ")";
    }
}
