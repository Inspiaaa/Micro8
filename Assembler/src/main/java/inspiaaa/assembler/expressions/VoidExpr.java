package inspiaaa.assembler.expressions;

import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.parser.Location;

public final class VoidExpr extends Expr {
    public VoidExpr(SymbolTable symtable, Location location) {
        super(symtable, location);
    }

    @Override
    public boolean isNumeric() {
        return false;
    }
}
