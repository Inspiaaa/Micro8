package inspiaaa.assembler.expressions;

import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.Location;

public final class StringExpr extends Expr {
    private final String value;

    public StringExpr(String value, SymbolTable symtable, Location location) {
        super(symtable, location);
        this.value = value;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    public String getValue() {
        return value;
    }
}
