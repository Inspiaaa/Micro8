package inspiaaa.assembler;

public abstract class Expression {
    protected final int line;

    public Expression(int line) {
        this.line = line;
    }

    public abstract int getValue(SymbolTable symtable);
}
