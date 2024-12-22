package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public final class StringExpr extends Expr {
    private final String value;

    public StringExpr(String value, Location location) {
        super(location);
        this.value = value;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "string(\"" + value + "\")";
    }
}
