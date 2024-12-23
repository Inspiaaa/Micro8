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
    public long getNumericValue() {
        if (!isNumeric()) {
            throw new UnsupportedOperationException();
        }

        return symtable.getSymbolOrThrow(name, location).getValue().getNumericValue();
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

    public Symbol getSymbol() {
        return symtable.getSymbolOrThrow(name, location);
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

        String output = symbol.getType().toString();

        if (!(symbol.getValue() instanceof VoidExpr)) {
            output += "(" + symbol.getName();

            if (symbol.getType() == SymbolType.VARIABLE) {
                output += " = " + symbol.getValue();
            }

            output += ")";
        }

        return output;
    }
}
