package inspiaaa.assembler;

public class SymbolicExpression extends Expression {
    private final String symbol;

    public SymbolicExpression(String symbol, int line) {
        super(line);
        this.symbol = symbol;
    }

    @Override
    public int getValue(SymbolTable symtable) {
        return symtable.getNumericValue(symbol, line);
    }
}
