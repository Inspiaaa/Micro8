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

    // Recursively resolve symbols to values.
    public Expr unwrap() {
        return this;
    }

    public Location getLocation() {
        return location;
    }

    // Returns a copy of this value with new location information. Used when unwrapping symbols.
    public abstract Expr withLocation(Location location);
}
