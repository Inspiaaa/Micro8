package inspiaaa.assembler.expressions;

import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.Location;

public final class CharExpr extends Expr {
    private final char value;

    public CharExpr(char value, SymbolTable symtable, Location location) {
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
