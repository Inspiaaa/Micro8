package inspiaaa.assembler.parser;

import java.util.Objects;

public class Token {
    private final TokenType type;
    private final String value;
    private final Location location;

    public Token(TokenType type, String value, Location location) {
        this.type = type;
        this.value = value;
        this.location = location;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Token(type=" + type + ", value='" + value + "', loc=" + location + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token)) return false;
        return type == token.type && Objects.equals(value, token.value) && Objects.equals(location, token.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, location);
    }
}
