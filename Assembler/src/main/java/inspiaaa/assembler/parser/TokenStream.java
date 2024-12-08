package inspiaaa.assembler.parser;

import java.util.List;

public class TokenStream {
    private final List<Token> tokens;
    private int index;

    public TokenStream(List<Token> tokens) {
        this.tokens = tokens;
    }

    public boolean isAtEnd() {
        return index >= tokens.size();
    }

    public int remaining() {
        return tokens.size() - index;
    }

    public Token peek() {
        return tokens.get(index);
    }

    public void advance() {
        index++;
    }

    public boolean match(TokenType type) {
        if (isAtEnd())
            return false;

        if (checkNext(type)) {
            advance();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkNext(TokenType type) {
        if (isAtEnd())
            return false;

        return peek().getType() == type;
    }
}
