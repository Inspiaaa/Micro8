package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;
import inspiaaa.assembler.parser.StringUtil;

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
        return "STRING(\"" + StringUtil.escapeString(value) + "\")";
    }
}
