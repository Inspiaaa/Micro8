package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public final class RelativeAddressExpr extends Expr {
    private final Expr offset;
    private final Expr base;

    public RelativeAddressExpr(Expr offset, Expr base, Location location) {
        super(location);
        this.offset = offset;
        this.base = base;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    public Expr getOffset() {
        return offset;
    }

    public Expr getBase() {
        return base;
    }

    @Override
    public Expr unwrap() {
        return new RelativeAddressExpr(offset.unwrap(), base.unwrap(), location);
    }

    @Override
    public Expr withLocation(Location location) {
        return new RelativeAddressExpr(offset, base, location);
    }

    @Override
    public String toString() {
        return "RELADDR(offset=" + offset + ", base=" + base + ")";
    }
}
