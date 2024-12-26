package inspiaaa.assembler.expressions;

import inspiaaa.assembler.parser.Location;

public class LabelExpr extends Expr {
    private final int address;

    public LabelExpr(int address, Location location) {
        super(location);
        this.address = address;
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public long getNumericValue() {
        return address;
    }

    @Override
    public int getRelativeAddress(int baseAddress) {
        return address - baseAddress;
    }

    @Override
    public Expr withLocation(Location location) {
        return new LabelExpr(address, location);
    }

    @Override
    public String toString() {
        return "LABEL(" + address + ")";
    }
}
