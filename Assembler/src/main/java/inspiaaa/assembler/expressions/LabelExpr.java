package inspiaaa.assembler.expressions;

import inspiaaa.assembler.memory.Address;
import inspiaaa.assembler.parser.Location;

public class LabelExpr extends Expr {
    private final Address address;

    public LabelExpr(Address address, Location location) {
        super(location);
        this.address = address;
    }

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public long getNumericValue() {
        return address.getAddress();
    }

    @Override
    public int getRelativeAddress(int baseAddress) {
        return address.getAddress() - baseAddress;
    }

    public Address getAddress() {
        return address;
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
