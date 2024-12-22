package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;
import inspiaaa.assembler.parser.StringUtil;

public final class CharExpr extends Expr {
    private final char value;

    public CharExpr(char value, Location location) {
        super(location);
        this.value = value;
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public long getNumericValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CHAR('" + StringUtil.escapeChar(value) + "')";
    }
}
