package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public class DistinctIntegerExpr extends Expr {
    private final String type;
    private final long value;

    public DistinctIntegerExpr(String type, long value, Location location) {
        super(location);
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public long getNumericValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    @Override
    public Expr withLocation(Location location) {
        return new DistinctIntegerExpr(type, value, location);
    }

    @Override
    public String toString() {
        return type + ":" + value;
    }
}
