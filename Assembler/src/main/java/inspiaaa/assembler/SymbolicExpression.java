package inspiaaa.assembler;

public class SymbolicExpression extends Expression {
    private final String name;

    public SymbolicExpression(String symbol, int line) {
        super(line);
        this.name = symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getValue(SymbolTable symtable) {
        return symtable.getNumericValue(name, line);
    }

    @Override
    public int getRelativeAddress(SymbolTable symtable, int baseAddress) {
        Symbol symbol = symtable.getSymbol(name, line);

        if (symbol.getType() == SymbolType.LABEL) {
            return symbol.getValue() - baseAddress;
        }
        else {
            return getValue(symtable);
        }
    }

    @Override
    public String toString() {
        return "SymbolicExpression(" + name + ")";
    }
}
