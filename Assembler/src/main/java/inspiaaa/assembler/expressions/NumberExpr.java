package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public final class NumberExpr extends Expr {
    private final long value;

    public NumberExpr(long value, Location location) {
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
        return "NUMBER(" + value + ")";
    }
}
