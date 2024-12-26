package inspiaaa.assembler;

import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.VoidExpr;

public class Symbol {
    private final String name;
    private Expr value;

    public Symbol(String name, Expr value) {
        this.name = name;
        this.value = value;
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
