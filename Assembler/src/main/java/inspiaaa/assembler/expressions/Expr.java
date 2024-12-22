package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public abstract class Expr {
    protected final Location location;

    public Expr(Location location) {
        this.location = location;
    }

    public abstract boolean isNumeric();

    public long getNumericValue() {
        throw new UnsupportedOperationException();
    };

    public long getRelativeAddress(int baseAddress) {
        return getNumericValue();
    }

    public Location getLocation() {
        return location;
    }
}
