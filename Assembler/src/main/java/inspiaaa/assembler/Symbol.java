package inspiaaa.assembler;

import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.VoidExpr;

public class Symbol {
    private final String name;
    private final SymbolType type;
    private Expr value;

    public Symbol(String name, SymbolType type, Expr value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Symbol(String name, SymbolType type) {
        this.name = name;
        this.type = type;
        this.value = new VoidExpr(null, null);
    }

    public SymbolType getType() {
        return type;
    }

    public Expr getValue() {
        return value;
    }

    public void setValue(Expr value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }
}
