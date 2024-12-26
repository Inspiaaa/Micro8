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

    public int getRelativeAddress(int baseAddress) {
        return (int)getNumericValue();
    }

    public Expr unwrap() {
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public abstract Expr withLocation(Location location);
}
