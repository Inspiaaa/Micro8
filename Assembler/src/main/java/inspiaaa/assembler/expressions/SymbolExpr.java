package inspiaaa.assembler.expressions;

import inspiaaa.assembler.Symbol;
import inspiaaa.assembler.SymbolTable;
import inspiaaa.assembler.SymbolType;
import inspiaaa.assembler.parser.Location;

public final class SymbolExpr extends Expr {
    private final String name;
    private final SymbolTable symtable;

    public SymbolExpr(String name, SymbolTable symtable, Location location) {
        super(location);
        this.name = name;
        this.symtable = symtable;
    }

    @Override
    public boolean isNumeric() {
        return symtable.getSymbolOrThrow(name, location).getValue().isNumeric();
    }

    @Override
    public long getRelativeAddress(int baseAddress) {
        Symbol symbol = symtable.getSymbolOrThrow(name, location);

        long numericValue = symbol.getValue().getNumericValue();

        if (symbol.getType() == SymbolType.LABEL) {
            return numericValue - baseAddress;
        }
        else {
            return numericValue;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "sym(" + name + ")";
    }
}
