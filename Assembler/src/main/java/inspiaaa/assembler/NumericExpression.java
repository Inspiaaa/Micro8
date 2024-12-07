package inspiaaa.assembler;

public class NumericExpression extends Expression {
    private final int value;

    public NumericExpression(int value, int line) {
        super(line);
        this.value = value;
    }

    @Override
    public int getValue(SymbolTable symtable) {
        return value;
    }
}
