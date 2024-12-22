package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public final class VoidExpr extends Expr {
    public VoidExpr(Location location) {
        super(location);
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public String toString() {
        return "void";
    }
}
