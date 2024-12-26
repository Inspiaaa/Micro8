package inspiaaa.assembler.expressions;

import inspiaaa.assembler.Symbol;
import inspiaaa.assembler.SymbolTable;
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
    public Expr unwrap() {
        return symtable.getSymbolOrThrow(name, location).getValue().unwrap().withLocation(location);
    }

    @Override
    public boolean isNumeric() {
        return unwrap().isNumeric();
    }

    @Override
    public long getNumericValue() {
        return unwrap().getNumericValue();
    }

    @Override
    public int getRelativeAddress(int baseAddress) {
        return unwrap().getRelativeAddress(baseAddress);
    }

    @Override
    public Expr withLocation(Location location) {
        return new SymbolExpr(name, symtable, location);
    }

    public Symbol getSymbol() {
        return symtable.getSymbolOrThrow(name, location);
    }

    public boolean isSymbolDefined() {
        return symtable.isSymbolDefined(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (!symtable.isSymbolDefined(name)) {
            return "SYMBOL(\"" + name + "\")";
        }

        Symbol symbol = symtable.getSymbol(name);
        return "SYMBOL(" + symbol.getName() + " = " + symbol.getValue() + ")";
    }
}
