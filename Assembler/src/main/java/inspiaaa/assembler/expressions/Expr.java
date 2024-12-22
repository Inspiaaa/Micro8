package inspiaaa.assembler.expressions;

import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.Location;

public abstract class Expr {
    protected final SymbolTable symtable;
    protected final Location location;

    public Expr(SymbolTable symtable, Location location) {
        this.symtable = symtable;
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
