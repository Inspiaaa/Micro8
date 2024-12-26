package inspiaaa.assembler.typing;

import inspiaaa.assembler.ErrorReporter;
import inspiaaa.assembler.expressions.Expr;
import inspiaaa.assembler.expressions.SymbolExpr;

public class SymbolTypeParameter implements ParameterTypeChecker {
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
}
