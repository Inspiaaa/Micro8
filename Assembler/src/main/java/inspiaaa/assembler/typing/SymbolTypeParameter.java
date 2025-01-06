package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.SymbolExpr;

// Only matches literal symbols.
public class SymbolTypeParameter implements ParameterType {
    @Override
    public boolean matches(Expr value) {
        return value instanceof SymbolExpr;
    }

    @Override
    public boolean potentiallyMatches(Expr value) {
        return matches(value);
    }

    @Override
    public void check(Expr value, ErrorReporter errorReporter) {
        if (!matches(value)) {
            errorReporter.reportError(
                    "Expected symbol name but found " + value,
                    value.getLocation());
        }
    }

    @Override
    public String toString() {
        return "symbol";
    }
}
