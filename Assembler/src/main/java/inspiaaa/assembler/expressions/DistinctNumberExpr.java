package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public class DistinctNumberExpr extends Expr {
    private final String type;
    private final long value;

    public DistinctNumberExpr(String type, long value, Location location) {
        super(location);
        this.type = type;
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

    public String getType() {
        return type;
    }

    @Override
    public Expr withLocation(Location location) {
        return new DistinctNumberExpr(type, value, location);
    }

    @Override
    public String toString() {
        return type + ":" + value;
    }
}
