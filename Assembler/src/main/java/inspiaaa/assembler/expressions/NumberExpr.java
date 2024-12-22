package inspiaaa.assembler.expressions;

import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.Location;

public final class NumberExpr extends Expr {
    private final long value;

    public NumberExpr(long value, SymbolTable symtable, Location location) {
        super(symtable, location);
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
}
